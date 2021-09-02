package com.senlainc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateAccountDto {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("middle_name")
    private String middleName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("password")
    private String password;
    @JsonProperty("city")
    private String city;
}
