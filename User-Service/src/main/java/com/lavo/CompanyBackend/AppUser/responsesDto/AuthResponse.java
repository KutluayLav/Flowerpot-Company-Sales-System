package com.lavo.CompanyBackend.AppUser.responsesDto;

import lombok.Data;

@Data
public class AuthResponse {

    private String message;

    private String email;

    private String accessToken;

    private String token;


}
