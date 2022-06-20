package com.wanna.qunar.work.main.work5.loop;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * EventLoop for Server
 *
 * @author wanna
 */
public class ServerEventLoop extends EventLoop {

    public ServerEventLoop(int port) {
        try {
            selector = Selector.open();
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            ((ServerSocketChannel) channel).bind(new InetSocketAddress(port));
            System.out.println("绑定端口[" + port + "]成功");
            channel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException ex) {
            throw new IllegalStateException("启动EventLoop失败", ex);
        }
    }
}
