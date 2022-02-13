package com.spring.account.entities.logging.constants;

/**
 * Actions performed by the users and the app which are loggable events.
 *
 * @author Alex Giazitzis
 */
public enum EventAction {
    CREATE_USER,
    CHANGE_PASSWORD,
    ACCESS_DENIED,
    LOGIN_FAILED,
    GRANT_ROLE,
    REMOVE_ROLE,
    LOCK_USER,
    UNLOCK_USER,
    DELETE_USER,
    BRUTE_FORCE
}
