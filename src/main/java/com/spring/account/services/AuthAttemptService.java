package com.spring.account.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service that contains information on who failed authentication and how many times. Very basic implementation, missing
 * a lot of things, like timers for when to clear a user from the cache.
 *
 * @author Alex Giazitzis
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AuthAttemptService {

    /**
     * Cache that holds user email as key and amount of failed authentications performed as value
     */
    Map<String, Integer> attemptsCache = new ConcurrentHashMap<>();

    /**
     * Removes the users' email from the cache.
     *
     * @param key users' email.
     */
    public void loginSuccess(final String key) {

        attemptsCache.remove(key);

    }

    /**
     * Adds the users' email to the cache or increments the failed attempts count by one.
     *
     * @param key users' email.
     */
    public void loginFailure(final String key) {

        if (attemptsCache.containsKey(key)) {

            attemptsCache.put(key, attemptsCache.get(key) + 1);

        } else {

            attemptsCache.put(key, 1);

        }

    }

    /**
     * Checks if the users' failed attempts are equal to the limit for which the service counts as brute forcing.
     *
     * @param key users' email
     * @return true if the amount of failed attempts is equal to the limit, false otherwise.
     */
    public boolean isBruteForce(final String key) {

        if (attemptsCache.containsKey(key)) {

            return attemptsCache.get(key) == 5;

        }

        return false;

    }

}
