package com.spring.account.config.auth.listeners;

import com.spring.account.dtos.admin.UserLockDto;
import com.spring.account.dtos.admin.constants.UserLockOperation;
import com.spring.account.entities.logging.constants.EventAction;
import com.spring.account.services.AuthAttemptService;
import com.spring.account.services.bases.UserService;
import com.spring.account.utils.EventLogger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Implementation of {@link org.springframework.context.ApplicationListener}.
 * Listens for {@link org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent}s, which get logged.
 * On multiple triggers from the same user, proceed to auto-lock the user's account as a {@link com.spring.account.entities.logging.constants.EventAction#BRUTE_FORCE} event.
 *
 * @author Alex Giazitzis
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AuthenticationFailureEventListener implements
                                                ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    HttpServletRequest request;
    AuthAttemptService service;
    UserService        userService;
    EventLogger        logger;

    @Override
    public void onApplicationEvent(final AuthenticationFailureBadCredentialsEvent event) {

        String subject = event.getAuthentication().getName().toLowerCase();

        service.loginFailure(subject);
        logger.log(EventAction.LOGIN_FAILED, subject, request.getServletPath());

        if (service.isBruteForce(subject)) {

            logger.log(EventAction.BRUTE_FORCE, subject, request.getServletPath());
            userService.changeUserLock(new UserLockDto(subject, UserLockOperation.LOCK.name()));

        }

    }

}
