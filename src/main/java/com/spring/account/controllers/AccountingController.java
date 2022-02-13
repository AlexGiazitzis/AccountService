package com.spring.account.controllers;

import com.spring.account.dtos.payment.PaymentDto;
import com.spring.account.services.bases.PaymentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * REST endpoints for the accounting parts of the app.
 *
 * @author Alex Giazitzis
 */
@RestController
@Validated
@RequestMapping({"/api/acct", "/api/acct/"})
@Secured("ROLE_ACCOUNTANT")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AccountingController {

    PaymentService paymentService;

    /**
     * Endpoint for the {@link com.spring.account.entities.user.constants.UserRole#ACCOUNTANT}s to upload new payments
     * for the employees.
     *
     * @param paymentDtoList {@link java.util.List} of {@link com.spring.account.dtos.payment.PaymentDto}s for the service to save.
     *                       Must not be empty and content must be valid.
     * @return Success message.
     * @throws org.springframework.web.server.ResponseStatusException if the parameter List doesn't contain unique objects.
     * @see com.spring.account.dtos.payment.PaymentDto
     */
    @PostMapping({"/payments", "/payments/"})
    public Map<String, String> uploadPayments(@RequestBody(required = false)
                                              @NotEmpty(message = "{List.empty}")
                                                      List<@Valid PaymentDto> paymentDtoList) {

        if (new HashSet<>(paymentDtoList).size() != paymentDtoList.size()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                              "Can't accept payments for the same employee for the same time period.");
        }

        paymentService.createPayments(paymentDtoList);

        return Map.of("status", "Added successfully!");
    }

    /**
     * Endpoint to update an existing {@link com.spring.account.entities.payment.Payment} for users with the
     * {@link com.spring.account.entities.user.constants.UserRole#ACCOUNTANT} role.
     *
     * @param paymentDto a single {@link com.spring.account.dtos.payment.PaymentDto} packet, which must be valid.
     * @return Success message.
     * @see com.spring.account.dtos.payment.PaymentDto
     */
    @PutMapping({"/payments", "/payments/"})
    public Map<String, String> updatePayments(@RequestBody @Valid PaymentDto paymentDto) {

        paymentService.updatePayment(paymentDto);
        return Map.of("status", "Updated successfully!");
    }
}
