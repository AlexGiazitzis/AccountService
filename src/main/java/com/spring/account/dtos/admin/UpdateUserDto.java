package com.spring.account.dtos.admin;

import com.spring.account.dtos.admin.annotations.ValidRole;
import com.spring.account.dtos.admin.annotations.ValidUpdateOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

/**
 * Data Transfer Object for the role update operation an administrator can perform on a user.
 *
 * @author Alex Giazitzis
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public class UpdateUserDto {

    @NotBlank
    String user;

    @NotBlank
    @ValidRole
    String role;

    @NotBlank
    @ValidUpdateOperation
    String operation;

}
