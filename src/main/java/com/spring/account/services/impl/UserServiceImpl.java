package com.spring.account.services.impl;

import com.spring.account.config.auth.bases.AuthenticationFacade;
import com.spring.account.dtos.admin.UpdateUserDto;
import com.spring.account.dtos.admin.UserLockDto;
import com.spring.account.dtos.admin.constants.UpdateOperation;
import com.spring.account.dtos.admin.constants.UserLockOperation;
import com.spring.account.dtos.user.ChangePasswordDto;
import com.spring.account.dtos.user.UserDto;
import com.spring.account.entities.logging.constants.EventAction;
import com.spring.account.entities.user.User;
import com.spring.account.entities.user.UserDetailsImpl;
import com.spring.account.entities.user.UserMapper;
import com.spring.account.entities.user.constants.UserRole;
import com.spring.account.entities.user.exceptions.InvalidOperationException;
import com.spring.account.entities.user.exceptions.UserNotFoundException;
import com.spring.account.repos.BreachedPasswordRepo;
import com.spring.account.repos.UserRepository;
import com.spring.account.services.bases.UserService;
import com.spring.account.utils.EventLogger;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @author Alex Giazitzis
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    AuthenticationFacade auth;
    UserRepository       userRepository;
    BreachedPasswordRepo breachedPasswordRepo;
    UserMapper           userMapper;
    PasswordEncoder      passwordEncoder;
    EventLogger          logger;
    HttpServletRequest   request;

    @Override
    public UserDto register(final UserDto dto) {

        User user = userMapper.getUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (!userRepository.existsByRolesContaining(UserRole.ADMINISTRATOR)) {
            user.getRoles().add(UserRole.ADMINISTRATOR);
        } else {
            user.getRoles().add(UserRole.USER);
        }

        userRepository.save(user);
        logger.log(EventAction.CREATE_USER, "Anonymous", user.getEmail().toLowerCase(), request.getServletPath());
        return userMapper.getDto(user);

    }

    @Override
    public Optional<User> getUserById(final Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.getDtoList(userRepository.findAllByOrderById());
    }

    @Override
    public boolean isAdmin(final String email) {

        Optional<User> userOptional = userRepository.findUserByEmailIgnoreCase(email);
        if (userOptional.isPresent()) {

            return userOptional.get().getRoles().contains(UserRole.ADMINISTRATOR);

        } else {

            throw new UserNotFoundException();

        }

    }

    @Override
    public boolean isEmailRegistered(final String email) {
        return userRepository.findUserByEmailIgnoreCase(email).isPresent();
    }

    @Override
    public boolean isPasswordSafe(final String password) {
        return !breachedPasswordRepo.getBreachedPassword().contains(password);
    }

    @Override
    public boolean isSamePassword(final String password, final User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public void updateUserPassword(final User user, final ChangePasswordDto dto) {
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        logger.log(EventAction.CHANGE_PASSWORD, user.getEmail().toLowerCase(), user.getEmail().toLowerCase(),
                   request.getServletPath());
        userRepository.save(user);
    }

    @Override
    public UserDto updateUserRole(final UpdateUserDto dto) {

        UserRole        role      = UserRole.valueOf(dto.getRole());
        UpdateOperation operation = UpdateOperation.valueOf(dto.getOperation());
        String          email     = dto.getUser();

        UserDetails userDetails = (UserDetailsImpl) auth.getAuthentication().getPrincipal();
        if (email.equals(userDetails.getUsername()) &&
            role.equals(UserRole.ADMINISTRATOR) && operation.equals(UpdateOperation.REMOVE)) {

            throw new InvalidOperationException("Can't remove ADMINISTRATOR role!");

        }

        Optional<User> userOptional = userRepository.findUserByEmailIgnoreCase(dto.getUser());
        if (userOptional.isPresent()) {

            User user = userOptional.get();

            if (user.getRoles().contains(UserRole.ADMINISTRATOR) &&
                operation.equals(UpdateOperation.REMOVE) && role.equals(UserRole.ADMINISTRATOR)) {
                throw new InvalidOperationException("Can't remove ADMINISTRATOR role!");
            }

            if ((user.getRoles().contains(UserRole.ADMINISTRATOR) || role.equals(UserRole.ADMINISTRATOR)) &&
                operation.equals(UpdateOperation.GRANT)) {
                throw new InvalidOperationException("The user cannot combine administrative and business roles!");
            }

            if (!user.getRoles().contains(role) && operation.equals(UpdateOperation.REMOVE)) {
                throw new InvalidOperationException("The user does not have a role!");
            }

            if (user.getRoles().size() == 1 && operation.equals(UpdateOperation.REMOVE)) {
                throw new InvalidOperationException("The user must have at least one role!");
            }

            switch (operation) {

                case GRANT:

                    user.getRoles().add(role);
                    logger.log(EventAction.GRANT_ROLE,
                               auth.getAuthentication().getName().toLowerCase(),
                               "Grant role " + role.name() + " to " + user.getEmail().toLowerCase(),
                               request.getServletPath());
                    break;

                case REMOVE:
                    user.getRoles().remove(role);
                    logger.log(EventAction.REMOVE_ROLE,
                               auth.getAuthentication().getName().toLowerCase(),
                               "Remove role " + role.name() + " from " + user.getEmail().toLowerCase(),
                               request.getServletPath());
                    break;

            }

            userRepository.save(user);

        } else {

            throw new UserNotFoundException();

        }

        return userOptional.map(userMapper::getDto).orElse(null);
    }

    @Override
    public boolean changeUserLock(final UserLockDto dto) {
        Optional<User>    optionalUser = userRepository.findUserByEmailIgnoreCase(dto.getUser());
        UserLockOperation operation    = UserLockOperation.valueOf(dto.getOperation());

        if (optionalUser.isPresent()) {

            User           user           = optionalUser.get();
            Authentication authentication = auth.getAuthentication();
            String subject = authentication == null
                             ? user.getEmail().toLowerCase()
                             : authentication.getName().toLowerCase();

            // should only trigger by the Authentication failed events listener.
            if (user.getRoles().contains(UserRole.ADMINISTRATOR) && subject.equalsIgnoreCase(user.getEmail())) {

                return false;

            }

            switch (operation) {

                case LOCK:
                    user.setLocked(true);
                    userRepository.save(user);
                    logger.log(EventAction.LOCK_USER, subject, "Lock user " + user.getEmail().toLowerCase(),
                               request.getServletPath());
                    break;

                case UNLOCK:
                    user.setLocked(false);
                    userRepository.save(user);
                    logger.log(EventAction.UNLOCK_USER, subject, "Unlock user " + user.getEmail().toLowerCase(),
                               request.getServletPath());
                    break;

            }

        } else {

            throw new UserNotFoundException();

        }

        return operation.equals(UserLockOperation.LOCK);

    }

    @Override
    public void deleteUser(final String email) {
        UserDetails userDetails = (UserDetailsImpl) auth.getAuthentication().getPrincipal();
        if (email.equals(userDetails.getUsername())) {
            throw new InvalidOperationException("Can't remove ADMINISTRATOR role!");
        }
        Optional<User> optionalUser = userRepository.findUserByEmailIgnoreCase(email);
        optionalUser.ifPresent(user -> {

            logger.log(EventAction.DELETE_USER, auth.getAuthentication().getName().toLowerCase(), user.getEmail(),
                       request.getServletPath());
            userRepository.delete(user);

        });

    }

}
