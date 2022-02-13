package com.spring.account.config.auth.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.account.entities.logging.constants.EventAction;
import com.spring.account.utils.EventLogger;
import com.spring.account.utils.ExceptionResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Implementation of {@link org.springframework.security.web.access.AccessDeniedHandler} that sends a customized JSON
 * response to the user.
 *
 * @author Alex Giazitzis
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    public static final Logger LOG = Logger.getLogger(AccessDeniedHandlerImpl.class);

    EventLogger logger;

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response,
                       final AccessDeniedException accessDeniedException) throws IOException {

        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();
        String subject = (auth == null
                          ? "Anonymous"
                          : auth.getName());
        LOG.warn("User: " + subject
                 + " attempted to access the protected URL: "
                 + request.getRequestURI());


        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                                                                    HttpStatus.FORBIDDEN.value(),
                                                                    "Forbidden",
                                                                    "Access Denied!",
                                                                    request.getServletPath());

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        response.getWriter().write(mapper.writeValueAsString(exceptionResponse));
        logger.log(EventAction.ACCESS_DENIED, subject, request.getServletPath());

    }

}
