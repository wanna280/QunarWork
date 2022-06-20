package com.wanna.qunar.work.main.work5.loop;

import com.google.common.collect.Lists;
import com.wanna.qunar.work.main.work5.event.*;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * EventLoop
 *
 * @author wanna
 */
public abstract class EventLoop {
    protected AbstractSelectableChannel channel;
    protected Selector selector;

    // 是否已经停止的标志位
    private final AtomicBoolean stopped = new AtomicBoolean(false);

    private final List<EventHandler> eventHandlers = Lists.newArrayListWithCapacity(4);

    public EventLoop() {

    }

    public EventLoop(AbstractSelectableChannel channel, Selector selector) {
        this.channel = channel;
        this.selector = selector;
    }

    public void start() {
        while (!stopped.get()) {
            try {
                while (selector.select() > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        final SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (selectionKey.isAcceptable()) {
                            publishEvent(new ServerSocketChannelAcceptEvent(this, selector, selectionKey));
                        }
                        if (selectionKey.isWritable()) {
                            publishEvent(new SocketChannelWriteEvent(this, selector, selectionKey));
                        }
                        if (selectionKey.isConnectable()) {
                            publishEvent(new SocketChannelConnectEvent(this, selector, selectionKey));
                        }
                        if (selectionKey.isReadable()) {
                            publishEvent(new SocketChannelReadEvent(this, selector, selectionKey));
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 发布事件，挨个交给所有的EventHandler去决定能否去处理该事件？
     *
     * @param event event
     * @throws IOException IOException
     */
    public void publishEvent(AbstractEvent event) throws IOException {
        for (EventHandler eventHandler : eventHandlers) {
            if (eventHandler.support(event)) {
                eventHandler.handle(event);
            }
        }
    }

    public void registerChannel(SelectableChannel channel) {

    }

    public void close() {
        if (stopped.compareAndSet(false, true)) {
            try {
                channel.close();
            } catch (IOException ex) {
                handleException(ex);
            }
            try {
                selector.close();
            } catch (IOException ex) {
                handleException(ex);
            }
        }
    }

    public void addEventHandlerFirst(EventHandler handler) {
        this.eventHandlers.add(0, handler);
    }

    public void addEventHandlers(EventHandler... handlers) {
        addEventHandlers(Arrays.asList(handlers));
    }

    public void addEventHandlers(Collection<EventHandler> handlers) {
        this.eventHandlers.addAll(handlers);
    }

    public static void handleException(Throwable ex) {

    }
}
