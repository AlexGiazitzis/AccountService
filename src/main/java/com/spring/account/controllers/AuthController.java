package com.spring.account.controllers;

import com.spring.account.config.auth.bases.AuthenticationFacade;
import com.spring.account.dtos.user.ChangePasswordDto;
import com.spring.account.dtos.user.UserDto;
import com.spring.account.entities.user.User;
import com.spring.account.entities.user.UserDetailsImpl;
import com.spring.account.entities.user.exceptions.InvalidPasswordException;
import com.spring.account.entities.user.exceptions.UserExistsException;
import com.spring.account.services.bases.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

/**
 * REST endpoints for authentication actions.
 *
 * @author Alex Giazitzis
 */
@RestController
@RequestMapping({"/api/auth", "/api/auth/"})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AuthController {

    UserService          userService;
    AuthenticationFacade authenticationFacade;

    /**
     * Endpoint for user registration.
     *
     * @param dto with the user information to register
     * @return the account information post-registration
     * @throws com.spring.account.entities.user.exceptions.UserExistsException if the email specified is already registered.
     */
    @PreAuthorize("permitAll()")
    @PostMapping({"/signup", "/signup/"})
    @ResponseStatus(HttpStatus.OK)
    public UserDto registerUser(@Valid @RequestBody UserDto dto) {

        if (userService.isEmailRegistered(dto.getEmail())) {
            throw new UserExistsException();
        }

        return userService.register(dto);

    }


    /**
     * Endpoint for a user to change their password.
     *
     * @param dto with the new password
     * @return success message.
     * @throws com.spring.account.entities.user.exceptions.InvalidPasswordException if the new password is the same as the old password.
     */
    @Secured({"ROLE_USER", "ROLE_ACCOUNTANT", "ROLE_ADMINISTRATOR", "ROLE_AUDITOR"})
    @PostMapping({"/changepass", "/changepass/"})
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> changePassword(@Valid @RequestBody ChangePasswordDto dto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();
        Optional<User>  user        = userService.getUserById(userDetails.getId());

        if (user.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        }

        if (userService.isSamePassword(dto.getPassword(), user.get())) {
            throw new InvalidPasswordException();
        }

        userService.updateUserPassword(user.get(), dto);

        return Map.of("email", user.get().getEmail().toLowerCase(), "status",
                      "The password has been updated successfully");
    }

}
