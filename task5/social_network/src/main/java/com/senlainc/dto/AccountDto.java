package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("middle_name")
    private String middleName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("is_email_confirmed")
    private boolean isEmailConfirmed;
    @JsonProperty("password")
    private String password;
    @JsonProperty("city")
    private String city;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("text_status")
    private String textStatus;
}
