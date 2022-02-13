package com.spring.account.entities.logging;

import com.spring.account.dtos.logging.EventDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper class that converts a {@link  com.spring.account.entities.logging.Event} to a
 * {@link com.spring.account.dtos.logging.EventDto}.
 *
 * @author Alex Giazitzis
 */
@Mapper
public interface EventMapper {

    /**
     * Maps the fields of a {@link com.spring.account.entities.logging.Event} to the fields of a
     * {@link com.spring.account.dtos.logging.EventDto}.
     *
     * @param event to be mapped.
     * @return its DTO representation.
     */
    EventDto getDto(Event event);

    /**
     * Converts a {@link java.util.List} of {@link com.spring.account.entities.logging.Event}s to a
     * {@link java.util.List} of {@link com.spring.account.dtos.logging.EventDto}s.
     *
     * @param events list to be mapped.
     * @return a list of DTOs for each event in the list.
     */
    List<EventDto> getList(List<Event> events);

}
