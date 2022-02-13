package com.spring.account.entities.logging;

import com.spring.account.entities.logging.constants.EventAction;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * An action performed by the user or the service.
 *
 * @author Alex Giazitzis
 */
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    LocalDateTime date;

    @Enumerated(EnumType.ORDINAL)
    EventAction action;

    String subject;
    String object;
    String path;

}
