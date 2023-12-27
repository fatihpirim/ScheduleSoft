package com.example.schedulesoft.event;

import com.example.schedulesoft.enums.Message;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event subclass that contains a Message object/enum
 */
public class DAOEvent extends Event {

    public static final EventType<DAOEvent> DAO_EVENT_TYPE = new EventType<>(Event.ANY, "DAO_EVENT");

    private final Message message;

    public DAOEvent(Message message) {
        super(DAO_EVENT_TYPE);
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
