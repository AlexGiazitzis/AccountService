package com.spring.account.config.auth.bases;

import org.springframework.security.core.Authentication;

/**
 * Facade for {@link org.springframework.security.core.Authentication} objects. Helps to avoid bloated method parameters
 * on controllers, allows the acquisition of {@link org.springframework.security.core.userdetails.UserDetails} from anywhere
 * in the project. Taken from <a href="https://www.baeldung.com/get-user-in-spring-security#interface">Baeldung: <em>Retrieve User Information in Spring Security</em></a>
 *
 * @author Alex Giazitzis
 */
public interface AuthenticationFacade {

    /**
     * @return {@link org.springframework.security.core.Authentication} from which User information can be retrieved.
     */
    Authentication getAuthentication();

}
