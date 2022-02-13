package com.spring.account.services.impl;

import com.spring.account.dtos.logging.EventDto;
import com.spring.account.entities.logging.Event;
import com.spring.account.entities.logging.EventMapper;
import com.spring.account.entities.logging.constants.EventAction;
import com.spring.account.repos.EventRepository;
import com.spring.account.services.bases.EventService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Alex Giazitzis
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    EventMapper     mapper;
    EventRepository eventRepository;

    @Override
    public List<EventDto> getEvents() {
        return mapper.getList(eventRepository.findAllByOrderByIdAsc());
    }

    @Override
    public void save(final EventAction action, final String subject, final String object, final String path) {
        eventRepository.save(new Event(null, LocalDateTime.now(), action, subject, object, path));
    }

}
