package com.wanna.qunar.work.main.work5.app;

import com.wanna.qunar.work.main.work5.codec.DefaultCodec;
import com.wanna.qunar.work.main.work5.entity.TransferPackage;
import com.wanna.qunar.work.main.work5.event.AbstractEvent;
import com.wanna.qunar.work.main.work5.event.EventHandler;
import com.wanna.qunar.work.main.work5.event.ServerSocketChannelAcceptEventHandler;
import com.wanna.qunar.work.main.work5.event.SocketChannelReadEvent;
import com.wanna.qunar.work.main.work5.http.HttpRestTemplate;
import com.wanna.qunar.work.main.work5.loop.EventLoop;
import com.wanna.qunar.work.main.work5.loop.ServerEventLoop;
import com.wanna.qunar.work.main.work5.util.CharacterUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {

    public static final int SERVER_PORT = 9789;

    public static void main(String[] args) {
        final EventLoop eventLoop = new ServerEventLoop(SERVER_PORT);
        eventLoop.addEventHandlers(new ServerEventHandler(), new ServerSocketChannelAcceptEventHandler());
        eventLoop.start();
    }

    /**
     * 服务端的EventHandler，负责接收客户端的读事件的请求，并进行处理，最后将处理结果返回给客户端
     */
    public static class ServerEventHandler implements EventHandler {
        private final DefaultCodec codec = new DefaultCodec();

        @Override
        public boolean support(AbstractEvent event) {
            return event instanceof SocketChannelReadEvent && event.getSelectionKey().channel() instanceof SocketChannel;
        }

        @Override
        public void handle(AbstractEvent event) throws IOException {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            final SocketChannel socketChannel = (SocketChannel) event.getSelectionKey().channel();
            socketChannel.read(byteBuffer);
            final TransferPackage transferPackage = codec.decode(byteBuffer);
            System.out.println("接收到客户端发送的消息URL=[" + transferPackage.getContentString() + "]");

            // 将数据写回给客户端
            handleWriteBack(socketChannel, transferPackage.getContentString());
        }

        private void handleWriteBack(SocketChannel socketChannel, String url) throws IOException {
            final CountResult countResult = count(url);
            final byte[] bytes = countResult.toString().getBytes(StandardCharsets.UTF_8);
            TransferPackage transferPackage = new TransferPackage(bytes);
            socketChannel.write(codec.encode(transferPackage));
        }

        private CountResult count(String url) throws IOException {
            final String result = new HttpRestTemplate().doGet(url);
            int charCount = result.length();
            int chineseCount = 0;
            int letterCount = 0;
            for (int i = 0; i < result.toCharArray().length; i++) {
                if (CharacterUtils.isChinese(result.charAt(i))) {
                    chineseCount++;
                }
                if (CharacterUtils.isLetter(result.charAt(i))) {
                    letterCount++;
                }
            }
            return new CountResult(charCount, chineseCount, letterCount);
        }

        static class CountResult {
            private final int charCount;
            private final int chineseCount;
            private final int letterCount;

            public CountResult(int charCount, int chineseCount, int letterCount) {
                this.charCount = charCount;
                this.chineseCount = chineseCount;
                this.letterCount = letterCount;
            }

            @Override
            public String toString() {
                return "字符总数量为[" + charCount + "], 中文总数量为[" + chineseCount + "], 英文字符数量为[" + letterCount + "]";
            }
        }
    }
}
