package com.spring.account.controllers;

import com.spring.account.dtos.logging.EventDto;
import com.spring.account.services.bases.EventService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST endpoints for the security logging of the service.
 *
 * @author Alex Giazitzis
 */
@RestController
@RequestMapping("/api/security")
@Secured("ROLE_AUDITOR")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class SecurityController {

    EventService eventService;

    /**
     * @return {@link java.util.List} of {@link com.spring.account.dtos.logging.EventDto}s that were logged.
     */
    @GetMapping({"/events", "/events/"})
    public List<EventDto> getEvents() {

        return eventService.getEvents();

    }
}
