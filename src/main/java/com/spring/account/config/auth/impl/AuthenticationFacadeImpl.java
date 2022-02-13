package com.spring.account.config.auth.impl;

import com.spring.account.config.auth.bases.AuthenticationFacade;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link com.spring.account.config.auth.bases.AuthenticationFacade}.
 *
 * @author Alex Giazitzis
 */
@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    /**
     * @return {@link org.springframework.security.core.Authentication} object containing the information of the
     * authentication user that accessed a resource or null if the user is unauthenticated.
     */
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
