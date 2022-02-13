package com.spring.account.config.auth.listeners;

import com.spring.account.services.AuthAttemptService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link org.springframework.context.ApplicationListener}.
 * Listens for {@link org.springframework.security.authentication.event.AuthenticationSuccessEvent}s.
 * When triggered, removes the user's email from the cache that counts failed authentication attempts.
 *
 * @author Alex Giazitzis
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    AuthAttemptService service;

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {

        service.loginSuccess(event.getAuthentication().getName().toLowerCase());

    }
}
