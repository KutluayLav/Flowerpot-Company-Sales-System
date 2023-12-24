package com.lavo.CompanyBackend.AppUser.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CreateAccountRequest {
    @NotEmpty(message = "Name can not be empty")
    private String firstname;

    @NotEmpty(message = "Last Name can not be empty")
    private String lastname;

    @NotEmpty(message = "Password can not be empty")
    private String password;

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Please provide a valid email id")
    private String email;

    @NotEmpty(message = "Phone Number can not be empty")
    private String phoneNo;

}
