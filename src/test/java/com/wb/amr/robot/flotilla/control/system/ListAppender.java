package com.wb.amr.robot.flotilla.control.system;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.util.ArrayList;
import java.util.List;

public class ListAppender extends AbstractAppender {

    private final List<LogEvent> events = new ArrayList<>();

    public ListAppender(String name) {
        super(name, null, PatternLayout.createDefaultLayout(), false, null);
    }

    @Override
    public void append(LogEvent event) {
        events.add(event.toImmutable());
    }

    public List<LogEvent> getEvents() {
        return events;
    }
}
