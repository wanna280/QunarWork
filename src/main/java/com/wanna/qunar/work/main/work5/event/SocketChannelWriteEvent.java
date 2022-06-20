package com.wanna.qunar.work.main.work5.event;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * 可写事件
 *
 * @author wanna
 */
public class SocketChannelWriteEvent extends AbstractEvent {
    public SocketChannelWriteEvent(Object source, Selector selector, SelectionKey selectionKey) {
        super(source, selector, selectionKey);
    }
}
