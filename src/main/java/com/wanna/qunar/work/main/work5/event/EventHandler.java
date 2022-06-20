package com.wanna.qunar.work.main.work5.event;

import java.io.IOException;

public interface EventHandler {

    boolean support(AbstractEvent event);

    void handle(AbstractEvent event) throws IOException;
}
