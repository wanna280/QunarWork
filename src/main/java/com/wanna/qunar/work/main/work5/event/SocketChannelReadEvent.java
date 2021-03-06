package com.wanna.qunar.work.main.work5.event;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * Read事件
 *
 * @author wanna
 */
public class SocketChannelReadEvent extends AbstractEvent {
    public SocketChannelReadEvent(Object source, Selector selector, SelectionKey selectionKey) {
        super(source, selector, selectionKey);
    }
}
