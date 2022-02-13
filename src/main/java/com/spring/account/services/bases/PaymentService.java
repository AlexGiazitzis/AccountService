package com.spring.account.services.bases;

import com.spring.account.dtos.payment.PaymentDto;

import java.time.YearMonth;
import java.util.List;

/**
 * Service for {@link com.spring.account.entities.payment.Payment} manipulation.
 *
 * @author Alex Giazitzis
 */
public interface PaymentService {

    /**
     * Persists a {@link java.util.List} of {@link com.spring.account.entities.payment.Payment}s to a datasource.
     *
     * @param paymentDtoList to be persisted.
     */
    void createPayments(final List<PaymentDto> paymentDtoList);

    /**
     * Retrieves a {@link com.spring.account.dtos.payment.PaymentDto} for a specific user ID
     * and a specific period, from a datasource.
     *
     * @param userId to find a payment for.
     * @param period of the payment.
     * @return {@link com.spring.account.dtos.payment.PaymentDto} for that user and period of time.
     */
    PaymentDto getPayment(final Long userId, final YearMonth period);

    /**
     * Retrieves a {@link java.util.List} of {@link com.spring.account.dtos.payment.PaymentDto}s for a specific user.
     *
     * @param userId to find all payments for
     * @return {@link java.util.List} of {@link com.spring.account.dtos.payment.PaymentDto}s for the specified user ID.
     */
    List<PaymentDto> getAllPayments(final Long userId);

    /**
     * Updates a payment of a user in the datasource.
     *
     * @param paymentDto with the information to be updated for a specific user and a specific period.
     */
    void updatePayment(final PaymentDto paymentDto);

}
