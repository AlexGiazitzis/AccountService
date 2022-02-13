package com.spring.account.entities.payment;

import com.spring.account.entities.payment.converters.YearMonthConverter;
import com.spring.account.entities.user.User;
import com.spring.account.utils.Default;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Object representation of a users' payment information.
 *
 * @author Alex Giazitzis
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_Id", "period"})},
        indexes = @Index(name = "payment_id_index", columnList = "id"))
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Default)
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Convert(converter = YearMonthConverter.class)
    YearMonth period;

    @ManyToOne
    @JoinColumn(name = "user_Id", referencedColumnName = "id")
    User user;

    Long salary;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(period, payment.period) && user.equals(payment.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
