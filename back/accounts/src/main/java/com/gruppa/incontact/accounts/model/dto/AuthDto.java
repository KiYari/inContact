package com.gruppa.incontact.accounts.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthDto {
    private String name;
    private String password;
}
