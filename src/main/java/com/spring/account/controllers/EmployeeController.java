package com.spring.account.controllers;

import com.spring.account.entities.user.UserDetailsImpl;
import com.spring.account.services.bases.PaymentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * REST endpoints for information retrieval by employees.
 *
 * @author Alex Giazitzis
 */
@RestController
@Validated
@RequestMapping({"/api/empl", "/api/empl/"})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class EmployeeController {

    PaymentService paymentService;

    /**
     * Retrieves payment information for an employee, whether for a specific period or for all of them.
     *
     * @param period - Optional - period for which to query for a {@link com.spring.account.dtos.payment.PaymentDto}
     * @return Either one or a {@link java.util.List} of {@link com.spring.account.dtos.payment.PaymentDto}s based on the parameter passed.
     */
    @GetMapping({"/payment", "/payment/"})
    @Secured({"ROLE_USER", "ROLE_ACCOUNTANT"})
    public ResponseEntity<?> getPayment(@RequestParam(required = false)
                                        @Pattern(regexp = "^(1[0-2]|0?\\d)-\\d{4}$", message = "{Period.invalid}") final String period) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                                                                             .getAuthentication()
                                                                             .getPrincipal();

        if (period == null) {

            return new ResponseEntity<>(paymentService.getAllPayments(userDetails.getId()), HttpStatus.OK);

        }

        return new ResponseEntity<>(paymentService.getPayment(userDetails.getId(), YearMonth.parse(period,
                                                                                                   DateTimeFormatter.ofPattern(
                                                                                                           "MM-yyyy"))),
                                    HttpStatus.OK);
    }


}
