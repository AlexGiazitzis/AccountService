package com.spring.account.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.account.dtos.user.annotations.NonBreached;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Data Transfer Object representation of {@link com.spring.account.entities.user.User}. When used as an incoming object
 * the id and roles fields are skipped, whilst as an outgoing object, the password field is skipped.
 *
 * @author Alex Giazitzis
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
@ToString
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @NotBlank
    String name;

    @NotBlank
    String lastname;

    @NotBlank
    @Email(regexp = ".+@acme\\.com")
    String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "{Password.blank}")
    @Size(min = 12, message = "{Password.invalid}")
    @NonBreached
    String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Set<String> roles;

}

