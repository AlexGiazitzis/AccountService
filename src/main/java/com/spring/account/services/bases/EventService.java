package com.spring.account.services.bases;

import com.spring.account.dtos.logging.EventDto;
import com.spring.account.entities.logging.constants.EventAction;

import java.util.List;

/**
 * Service for {@link  com.spring.account.entities.logging.Event} manipulation.
 *
 * @author Alex Giazitzis
 */
public interface EventService {

    /**
     * Gets all the events logged to a datasource.
     *
     * @return {@link java.util.List} of {@link com.spring.account.entities.logging.Event}s.
     */
    List<EventDto> getEvents();

    /**
     * Persists an {@link com.spring.account.entities.logging.Event} to a datasource.
     *
     * @param action  that led to the event trigger.
     * @param subject who triggered the event.
     * @param object  that was going to be accessed or manipulated, endpoint or user.
     * @param path    where the event was triggered.
     */
    void save(final EventAction action, final String subject, final String object, final String path);

}
