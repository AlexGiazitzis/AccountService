package com.spring.account.dtos.payment;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spring.account.dtos.payment.serializers.PaymentDtoSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

/**
 * Data Transfer Object representation of {@link com.spring.account.entities.payment.Payment}.
 * When used as an incoming object, the name and lastname parameters are skipped, whilst as an outgoing object
 * the email field is skipped.
 *
 * @author Alex Giazitzis
 */
@JsonSerialize(using = PaymentDtoSerializer.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public class PaymentDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonAlias("employee")
    @NotBlank(message = "{Email.blank}")
    @Email(regexp = "^.+@acme\\.com$", message = "{Email.invalid}")
    String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String lastname;

    @NotBlank
    @Pattern(regexp = "^(1[0-2]|0?\\d)-\\d{4}$", message = "{Period.invalid}")
    String period;

    @Min(value = 0, message = "{Salary.min}")
    Long salary;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDto that = (PaymentDto) o;
        return Objects.equals(email, that.email) && Objects.equals(period, that.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
