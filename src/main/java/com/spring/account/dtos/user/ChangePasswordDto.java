package com.spring.account.dtos.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.spring.account.dtos.user.annotations.NonBreached;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Alex Giazitzis
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public class ChangePasswordDto {

    @JsonAlias("new_password")
    @NotBlank(message = "{Password.blank}")
    @Size(min = 12, message = "{Password.invalid}")
    @NonBreached
    String password;

}
