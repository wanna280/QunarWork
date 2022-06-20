package com.wanna.qunar.work.main.work5.app;

import com.wanna.qunar.work.main.work5.entity.TransferPackage;
import com.wanna.qunar.work.main.work5.codec.Codec;
import com.wanna.qunar.work.main.work5.codec.DefaultCodec;
import com.wanna.qunar.work.main.work5.event.AbstractEvent;
import com.wanna.qunar.work.main.work5.event.SocketChannelConnectEvent;
import com.wanna.qunar.work.main.work5.event.SocketChannelConnectEventHandler;
import com.wanna.qunar.work.main.work5.event.SocketChannelReadEvent;
import com.wanna.qunar.work.main.work5.loop.ClientEventLoop;
import com.wanna.qunar.work.main.work5.loop.EventLoop;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


/**
 * Client
 *
 * @author wanna
 */
public class Client {

    public static void main(String[] args) throws Exception {
        final EventLoop eventLoop = new ClientEventLoop(Server.SERVER_PORT);
        eventLoop.addEventHandlers(new ClientEventHandler());
        eventLoop.start();
    }

    /**
     * 客户端的EventHandler，负责接收命令行的URL输入参数发送给服务端去进行处理，并且负责去接收服务端的响应数据
     */
    public static class ClientEventHandler extends SocketChannelConnectEventHandler {

        private final Scanner scanner = new Scanner(System.in);

        private final Codec codec = new DefaultCodec();

        @Override
        public boolean support(AbstractEvent event) {
            return (event instanceof SocketChannelConnectEvent || event instanceof SocketChannelReadEvent) && event.getSelectionKey().channel() instanceof SocketChannel;
        }

        @Override
        public void handle(AbstractEvent event) throws IOException {
            super.handle(event);
            final SocketChannel socketChannel = (SocketChannel) event.getSelectionKey().channel();
            if (event instanceof SocketChannelReadEvent) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                socketChannel.read(byteBuffer);
                final TransferPackage transferPackage = codec.decode(byteBuffer);
                System.out.println("接受到服务端的数据包[" + transferPackage.getContentString() + "]");
            }
            System.out.print("请输入要获取数据的URL: ");
            TransferPackage transferPackage = new TransferPackage(scanner.nextLine().getBytes(StandardCharsets.UTF_8));
            socketChannel.write(codec.encode(transferPackage));
        }
    }
}