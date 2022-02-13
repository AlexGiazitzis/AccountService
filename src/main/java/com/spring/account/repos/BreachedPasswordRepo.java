package com.spring.account.repos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A "repository" that accesses "breached" password. Created like that for learning purposes.
 * A proper implementation would be either an actual JPA repository that accesses a database containing actual breached
 * password, or an HTTP client which checks a public resource of breach password.
 *
 * @author Alex Giazitzis
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@NoArgsConstructor
@Getter
public class BreachedPasswordRepo {

    List<String> breachedPassword = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch",
                                            "PasswordForApril",
                                            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
                                            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember",
                                            "PasswordForDecember");

}
