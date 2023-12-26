package com.lavo.CompanyBackend.AppUser.responsesDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailsUserResponse {

    private String firstname;
    private String lastname;
    private String email;
    private String phoneNo;

}
