package com.spring.account.entities.user;

import com.spring.account.dtos.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * Mapper class for {@link com.spring.account.entities.user.User} and {@link com.spring.account.dtos.user.UserDto}.
 *
 * @author Alex Giazitzis
 */
@Mapper
public abstract class UserMapper {

    /**
     * Maps a {@link com.spring.account.dtos.user.UserDto} to a {@link com.spring.account.entities.user.User}.
     *
     * @param dto to be converted
     * @return user entity.
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "roles", expression = "java(new java.util.LinkedHashSet<>())"),
            @Mapping(target = "email", expression = "java(dto.getEmail().toLowerCase())"),
            @Mapping(target = "paymentSet", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "locked", expression = "java(false)")
    })
    public abstract User getUser(UserDto dto);

    /**
     * Maps a {@link com.spring.account.entities.user.User} to a {@link com.spring.account.dtos.user.UserDto}.
     *
     * @param user to be converted.
     * @return its DTO representation.
     */
    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(com.spring.account.entities.user.constants.UserRole::getRole).sorted().collect(java.util.stream.Collectors.toCollection(java.util.LinkedHashSet::new)))")
    public abstract UserDto getDto(User user);

    /**
     * Creates a {@link com.spring.account.entities.user.UserDetailsImpl} object by mapping the appropriate fields of a
     * {@link com.spring.account.entities.user.User} to it.
     *
     * @param user whose information is to be mapped.
     * @return User Details object for Spring to use.
     */
    @Mapping(target = "authorities",
            expression = "java(user.getRoles().stream().map(com.spring.account.entities.user.constants.UserRole::getRole).map(org.springframework.security.core.authority.SimpleGrantedAuthority::new).collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new)))")
    public abstract UserDetailsImpl getDetails(final User user);

    /**
     * Converts an {@link java.lang.Iterable} of {@link com.spring.account.entities.user.User}s to map to a
     * {@link java.util.List} of {@link com.spring.account.dtos.user.UserDto}s.
     *
     * @param user iterable to be mapped.
     * @return list of the DTO representation of all users.
     */
    public abstract List<UserDto> getDtoList(final Iterable<User> user);

}