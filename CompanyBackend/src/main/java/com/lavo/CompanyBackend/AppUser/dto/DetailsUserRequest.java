package com.lavo.CompanyBackend.AppUser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailsUserRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String phoneNo;

}
