package com.spring.account.entities.payment;

import com.spring.account.dtos.payment.PaymentDto;
import com.spring.account.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * Mapper class for {@link com.spring.account.entities.payment.Payment} and {@link com.spring.account.dtos.payment.PaymentDto}.
 *
 * @author Alex Giazitzis
 */
@Mapper
public interface PaymentMapper {


    /**
     * Maps a {@link com.spring.account.dtos.payment.PaymentDto} to a {@link com.spring.account.entities.payment.Payment} while also adding a
     * {@link com.spring.account.entities.user.User} to it.
     *
     * @param dto  to be converted.
     * @param user whose payment is to be converted.
     * @return a {@link com.spring.account.entities.payment.Payment} to be persisted with a proper relation to its user set.
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "period", expression = "java(YearMonth.parse(dto.getPeriod(), java.time.format.DateTimeFormatter.ofPattern(\"MM-yyyy\")))"),
    })
    Payment getPayment(PaymentDto dto, User user);


    /**
     * Maps a {@link com.spring.account.entities.payment.Payment} to a {@link com.spring.account.dtos.payment.PaymentDto}.
     *
     * @param payment to be mapped.
     * @return its DTO representation.
     */
    @Mappings({
            @Mapping(target = "email", ignore = true),
            @Mapping(target = "name", expression = "java(payment.getUser().getName())"),
            @Mapping(target = "lastname", expression = "java(payment.getUser().getLastname())"),
            @Mapping(target = "period", expression = "java(payment.getPeriod().format(java.time.format.DateTimeFormatter.ofPattern(\"MMMM-yyyy\")))")
    })
    PaymentDto getDto(Payment payment);

    /**
     * Converts a {@link java.util.List} of {@link com.spring.account.entities.payment.Payment}s to a list of {@link com.spring.account.dtos.payment.PaymentDto}s.
     *
     * @param paymentList list of payments to be mapped.
     * @return a list of their DTO representation.
     */
    List<PaymentDto> getDtoList(List<Payment> paymentList);
}
