package com.spring.account.repos;

import com.spring.account.entities.logging.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * A {@link org.springframework.data.repository.CrudRepository} for {@link com.spring.account.entities.logging.Event} persistence and querying.
 *
 * @author Alex Giazitzis
 */
@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    /**
     * Queries the database and retrieves all {@link com.spring.account.entities.logging.Event}s saved, in ascending order
     * by their ID.
     *
     * @return {@link java.util.List} of {@link com.spring.account.entities.logging.Event}s.
     */
    List<Event> findAllByOrderByIdAsc();

}
