package com.spring.account.services.impl;

import com.spring.account.entities.user.User;
import com.spring.account.entities.user.UserMapper;
import com.spring.account.repos.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link org.springframework.security.core.userdetails.UserDetailsService} that accesses the services
 * datasource for Spring to use with {@link org.springframework.security.core.userdetails.UserDetails} for authentication.
 *
 * @author Alex Giazitzis
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;
    UserMapper     userMapper;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmailIgnoreCase(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Email not registered");
        }

        return userMapper.getDetails(user.get());

    }
}
