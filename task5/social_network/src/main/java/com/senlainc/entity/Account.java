package com.senlainc.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Account {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private boolean emailConfirmed;
    private String password;
    private String city;
    private String phoneNumber;
    private String textStatus;
}
