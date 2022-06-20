package com.wanna.qunar.work.main.work5.loop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ClientEventLoop extends EventLoop {
    public ClientEventLoop(int serverPort) {
        try {
            selector = Selector.open();
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            if (((SocketChannel) channel).connect(new InetSocketAddress(serverPort))) {
                channel.register(selector, SelectionKey.OP_READ);
            } else {
                channel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (IOException ex) {
            throw new IllegalStateException("启动EventLoop失败", ex);
        }
    }
}
