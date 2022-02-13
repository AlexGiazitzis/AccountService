package com.spring.account.services.bases;

import com.spring.account.dtos.admin.UpdateUserDto;
import com.spring.account.dtos.admin.UserLockDto;
import com.spring.account.dtos.user.ChangePasswordDto;
import com.spring.account.dtos.user.UserDto;
import com.spring.account.entities.user.User;

import java.util.List;
import java.util.Optional;

/**
 * Service for {@link com.spring.account.entities.user.User} manipulation.
 *
 * @author Alex Giazitzis
 */
public interface UserService {

    /**
     * Persists a {@link com.spring.account.dtos.user.UserDto} to a datasource.
     *
     * @param dto with the information the user registers with.
     * @return {@link com.spring.account.dtos.user.UserDto} with the information the user inputted and the account ID given.
     */
    UserDto register(final UserDto dto);

    /**
     * Queries the datasource for a {@link com.spring.account.entities.user.User} with the passed ID.
     *
     * @param id of a user to search for.
     * @return {@link java.util.Optional} of a {@link com.spring.account.entities.user.User} that may or may not be present.
     */
    Optional<User> getUserById(final Long id);

    /**
     * Retrieves a {@link java.util.List} of all {@link com.spring.account.dtos.user.UserDto}s from the datasource.
     *
     * @return {@link java.util.List} of {@link com.spring.account.dtos.user.UserDto}s persisted.
     */
    List<UserDto> getAllUsers();

    /**
     * Checks if a user with the passed email has an administrator role.
     *
     * @param email to query for.
     * @return true if the user has an administrator role, false otherwise.
     */
    boolean isAdmin(final String email);

    /**
     * Checks if the passed email has been registered.
     *
     * @param email to check.
     * @return true if a user exists with the specific email, false otherwise.
     */
    boolean isEmailRegistered(final String email);

    /**
     * Checks if the passed password is among the breached passwords or not.
     *
     * @param password to check.
     * @return true if the password isn't among the breached ones, false otherwise.
     */
    boolean isPasswordSafe(final String password);

    /**
     * Checks if the passed password is the same current password of the user.
     *
     * @param password to check.
     * @param user     whose password is to be used as reference.
     * @return true if the passed password is the same as the current one, false otherwise.
     */
    boolean isSamePassword(final String password, final User user);

    /**
     * Updates a users' password.
     *
     * @param user whose password is to be updated.
     * @param dto  with the new password.
     */
    void updateUserPassword(final User user, final ChangePasswordDto dto);

    /**
     * Updates a users' role.
     *
     * @param dto with the operation, role and user to update
     * @return the new status of the user's account.
     */
    UserDto updateUserRole(final UpdateUserDto dto);

    /**
     * Changes the lock status of a user's account.
     *
     * @param dto with the users' account to lock or unlock
     * @return true if the users' account was locked, false if unlocked.
     */
    boolean changeUserLock(final UserLockDto dto);

    /**
     * Deletes a user from the datasource.
     *
     * @param email of the users' account to delete.
     */
    void deleteUser(final String email);

}
