package com.wanna.qunar.work.main.work5.event;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.EventObject;

public abstract class AbstractEvent extends EventObject {

    private final Selector selector;

    private final SelectionKey selectionKey;

    public AbstractEvent(Object source, Selector selector, SelectionKey selectionKey) {
        super(source);
        this.selector = selector;
        this.selectionKey = selectionKey;
    }

    public Selector getSelector() {
        return selector;
    }

    public SelectionKey getSelectionKey() {
        return selectionKey;
    }
}
