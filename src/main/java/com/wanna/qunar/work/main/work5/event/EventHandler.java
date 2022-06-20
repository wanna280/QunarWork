package com.wanna.qunar.work.main.work5.event;

import java.io.IOException;

/**
 * JavaNio的事件处理器
 *
 * @author wanna
 */
public interface EventHandler {

    boolean support(AbstractEvent event);

    void handle(AbstractEvent event) throws IOException;
}
