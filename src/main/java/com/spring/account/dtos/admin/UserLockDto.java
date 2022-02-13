package com.spring.account.dtos.admin;

import com.spring.account.dtos.admin.annotations.ValidUserLockOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

/**
 * Data Transfer Object for the administrator operation of locking/unlocking a user's account.
 *
 * @author Alex Giazitzis
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public class UserLockDto {

    @NotBlank(message = "{Email.blank}")
    String user;

    @NotBlank(message = "{Operation.blank}")
    @ValidUserLockOperation
    String operation;

}
