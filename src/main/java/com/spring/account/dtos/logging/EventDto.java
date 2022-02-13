package com.spring.account.dtos.logging;

import com.spring.account.entities.logging.constants.EventAction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representation of {@link com.spring.account.entities.logging.Event}.
 *
 * @author Alex Giazitzis
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public class EventDto {

    Long          id;
    LocalDateTime date;
    EventAction   action;
    String        subject;
    String        object;
    String        path;

}
