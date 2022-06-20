package com.wanna.qunar.work.main.work5.event;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * 接收连接完成的事件，在注册完成之后，负责将该SocketChannel注册到Selector当中
 *
 * @see  SocketChannelConnectEvent
 */
public class SocketChannelConnectEventHandler implements EventHandler {

    @Override
    public boolean support(AbstractEvent event) {
        return event instanceof SocketChannelConnectEvent && event.getSelectionKey().channel() instanceof SocketChannel;
    }

    @Override
    public void handle(AbstractEvent event) throws IOException {
        final SelectionKey selectionKey = event.getSelectionKey();
        final SocketChannel channel = (SocketChannel) selectionKey.channel();
        if (channel.finishConnect()) {
            channel.register(event.getSelector(), SelectionKey.OP_READ);
        }
    }
}
