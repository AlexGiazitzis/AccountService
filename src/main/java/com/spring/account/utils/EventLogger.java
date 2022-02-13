package com.spring.account.utils;

import com.spring.account.entities.logging.constants.EventAction;
import com.spring.account.entities.logging.exceptions.InvalidEventLogException;
import com.spring.account.services.bases.EventService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

/**
 * Logger for service triggered events.
 *
 * @author Alex Giazitzis
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class EventLogger {

    EventService service;

    /**
     * Logs an event triggered.
     *
     * @param action that lead to the event logging.
     * @param args   messages with details about the event.
     */
    public void log(final EventAction action, final String... args) {

        if (args.length == 2) {

            service.save(action, args[0], args[1], args[1]);

        } else if (args.length == 3) {

            service.save(action, args[0], args[1], args[2]);

        } else {

            throw new InvalidEventLogException("Expected 2 or 3 String arguments, got " + args.length + "!");

        }

    }

}
