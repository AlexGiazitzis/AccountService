package com.spring.account.controllers;

import com.spring.account.dtos.admin.UpdateUserDto;
import com.spring.account.dtos.admin.UserLockDto;
import com.spring.account.dtos.admin.constants.UserLockOperation;
import com.spring.account.dtos.user.UserDto;
import com.spring.account.entities.user.constants.UserRole;
import com.spring.account.entities.user.exceptions.InvalidOperationTargetException;
import com.spring.account.entities.user.exceptions.UserNotFoundException;
import com.spring.account.services.bases.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * REST endpoints for the administrator operations.
 *
 * @author Alex Giazitzis
 */
@RestController
@Validated
@RequestMapping({"/api/admin", "/api/admin/"})
@Secured("ROLE_ADMINISTRATOR")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AdminController {

    UserService userService;

    /**
     * @return {@link java.util.List} of {@link com.spring.account.dtos.user.UserDto}s of the registered users.
     */
    @GetMapping({"/user", "/user/"})
    public List<UserDto> getUser() {
        return userService.getAllUsers();
    }

    /**
     * Endpoint which allows updating a users' role.
     *
     * @param dto with the users' email and whether to add or remove the specified {@link com.spring.account.entities.user.constants.UserRole}.
     * @return {@link com.spring.account.dtos.user.UserDto} of the updated user.
     */
    @PutMapping({"/user/role", "/user/role/"})
    public UserDto changeUserRole(@RequestBody(required = false) @Valid UpdateUserDto dto) {

        return userService.updateUserRole(dto);

    }

    /**
     * Endpoint for user deletion by an admin.
     *
     * @param email of the user to delete.
     * @return Success message.
     * @throws com.spring.account.entities.user.exceptions.UserNotFoundException if the specified email isn't of a registered user.
     */
    @DeleteMapping({"/user/{email}", "/user", "/user/"})
    public Map<String, String> deleteUser(@PathVariable(required = false) String email) {

        if (!userService.isEmailRegistered(email)) {
            throw new UserNotFoundException();
        }

        userService.deleteUser(email);
        return Map.of("user", email.toLowerCase(), "status", "Deleted successfully!");

    }

    /**
     * Endpoint for manual user locking.
     *
     * @param dto containing the user's email and which {@link com.spring.account.dtos.admin.constants.UserLockOperation} to perform.
     * @return Success message.
     * @throws com.spring.account.entities.user.exceptions.InvalidOperationTargetException if a
     *                                                                                     {@link com.spring.account.entities.user.constants.UserRole#ADMINISTRATOR} account is specified.
     */
    @PutMapping({"/user/access", "/user/access/"})
    public Map<String, String> changeUserLock(@RequestBody @Valid UserLockDto dto) {

        if (userService.isAdmin(dto.getUser()) && dto.getOperation().equals(UserLockOperation.LOCK.name())) {

            throw new InvalidOperationTargetException("Can't lock the " + UserRole.ADMINISTRATOR.name() + "!");

        }

        if (userService.changeUserLock(dto)) {

            return Map.of("status", "User " + dto.getUser().toLowerCase() + " locked!");

        }

        return Map.of("status", "User " + dto.getUser().toLowerCase() + " unlocked!");

    }

}
