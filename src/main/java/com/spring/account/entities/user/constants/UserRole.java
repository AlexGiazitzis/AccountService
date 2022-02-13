package com.spring.account.entities.user.constants;

/**
 * The roles a user can take in the service.
 *
 * @author Alex Giazitzis
 */
public enum UserRole {
    ACCOUNTANT("ROLE_ACCOUNTANT"),
    ADMINISTRATOR("ROLE_ADMINISTRATOR"),
    AUDITOR("ROLE_AUDITOR"),
    USER("ROLE_USER");

    private final String role;

    UserRole(final String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}