package com.gruppa.incontact.message.model.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private String name;
    private String password;
    @Email
    private String email;
    private String userpic;
}
