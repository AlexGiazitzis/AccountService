package com.spring.account.repos;

import com.spring.account.entities.payment.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

/**
 * Α {@link org.springframework.data.repository.CrudRepository} for {@link com.spring.account.entities.payment.Payment} persistence and querying.
 *
 * @author Alex Giazitzis
 */
@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    /**
     * Finds all the {@link com.spring.account.entities.payment.Payment}s of a user, in descending order by their period value.
     *
     * @param id of the user to query for.
     * @return {@link java.util.List} of {@link com.spring.account.entities.payment.Payment}s of the user's ID.
     */
    List<Payment> findAllByUserIdOrderByPeriodDesc(final Long id);

    /**
     * Queries the database and tries to find a {@link com.spring.account.entities.payment.Payment} for the passed user ID and
     * period.
     *
     * @param period to search for a payment.
     * @param userId whose payment is to be queried.
     * @return {@link java.util.Optional} of {@link com.spring.account.entities.payment.Payment} that may or may not contain an object.
     */
    Optional<Payment> findByPeriodAndUserId(final YearMonth period, final Long userId);

}