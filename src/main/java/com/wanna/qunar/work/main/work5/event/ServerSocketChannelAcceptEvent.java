package com.wanna.qunar.work.main.work5.event;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * Accept事件的处理器
 *
 * @author wanna
 */
public class ServerSocketChannelAcceptEvent extends AbstractEvent {
    public ServerSocketChannelAcceptEvent(Object source, Selector selector, SelectionKey selectionKey) {
        super(source, selector, selectionKey);
    }
}
