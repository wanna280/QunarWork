package com.wanna.qunar.work.main.work5.event;

import com.wanna.qunar.work.main.work5.entity.TransferPackage;
import com.wanna.qunar.work.main.work5.codec.Codec;
import com.wanna.qunar.work.main.work5.codec.DefaultCodec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 处理Socket读事件的EventHandler，在客户端发送数据过来时，应该将数据去进行接收，并去进行处理
 *
 * @see  SocketChannelReadEvent
 */
public class SocketChannelReadEventHandler implements EventHandler {
    public static final int DEFAULT_BYTE_BUFFER_SIZE = 1024;
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(DEFAULT_BYTE_BUFFER_SIZE);

    private Codec codec = new DefaultCodec();

    @Override
    public boolean support(AbstractEvent event) {
        return event instanceof SocketChannelReadEvent && event.getSelectionKey().channel() instanceof SocketChannel;
    }

    @Override
    public void handle(AbstractEvent event) throws IOException {
        final SocketChannel socketChannel = (SocketChannel) event.getSelectionKey().channel();
        socketChannel.read(byteBuffer);
        if (byteBuffer.position() != 0) {
            TransferPackage transferPackage = codec.decode(byteBuffer);
            System.out.println("接收到数据[" + transferPackage.getContentString() + "]");
        } else {
            socketChannel.close();
        }
    }

    public Codec getCodec() {
        return codec;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }
}
