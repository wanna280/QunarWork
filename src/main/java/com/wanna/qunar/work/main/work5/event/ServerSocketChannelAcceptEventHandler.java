package com.wanna.qunar.work.main.work5.event;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 服务端负责接收一个请求的连接的EventHandler，将该连接的Socket注册到Selector当中
 *
 * @see ServerSocketChannelAcceptEvent
 * @see wanna
 */
public class ServerSocketChannelAcceptEventHandler implements EventHandler {

    @Override
    public boolean support(AbstractEvent event) {
        return event instanceof ServerSocketChannelAcceptEvent && event.getSelectionKey().channel() instanceof ServerSocketChannel;
    }

    @Override
    public void handle(AbstractEvent event) throws IOException {
        final ServerSocketChannel channel = (ServerSocketChannel) event.getSelectionKey().channel();
        final SocketChannel socketChannel = channel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(event.getSelector(), SelectionKey.OP_READ);
        System.out.println("接收到客户端[" + socketChannel.getRemoteAddress() + "]的连接");
    }
}
