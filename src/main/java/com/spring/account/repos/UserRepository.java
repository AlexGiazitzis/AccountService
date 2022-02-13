package com.spring.account.repos;

import com.spring.account.entities.user.User;
import com.spring.account.entities.user.constants.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * A {@link org.springframework.data.repository.CrudRepository} for {@link com.spring.account.entities.user.User} persistence and querying.
 *
 * @author Alex Giazitzis
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Finds all users persisted in the database, in ascending order by their ID.
     *
     * @return {@link java.util.List} of {@link com.spring.account.entities.user.User}s.
     */
    List<User> findAllByOrderById();

    /**
     * Queries the database for a user with the passed email, case-insensitive.
     *
     * @param email of a user to search for.
     * @return {@link java.util.Optional} of {@link com.spring.account.entities.user.User} that may or may not contain an object.
     */
    Optional<User> findUserByEmailIgnoreCase(final String email);

    /**
     * Queries the database, finding if there is at least one user with the passed role.
     *
     * @param role to search for.
     * @return true if at least one user has that role, false otherwise.
     */
    boolean existsByRolesContaining(final UserRole role);
}