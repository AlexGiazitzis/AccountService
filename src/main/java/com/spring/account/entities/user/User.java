package com.spring.account.entities.user;

import com.spring.account.entities.payment.Payment;
import com.spring.account.entities.user.constants.UserRole;
import com.spring.account.utils.Default;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Object representation of a user's information.
 *
 * @author Alex Giazitzis
 */
@Entity
@Table(indexes = {@Index(name = "user_id_index", columnList = "id"), @Index(name = "user_email_index", columnList = "email")})
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Default)
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String lastname;

    @NaturalId
    @Column(unique = true)
    String email;

    String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.ORDINAL)
    Set<UserRole> roles = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    Set<Payment> paymentSet = new HashSet<>();

    boolean locked;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}
