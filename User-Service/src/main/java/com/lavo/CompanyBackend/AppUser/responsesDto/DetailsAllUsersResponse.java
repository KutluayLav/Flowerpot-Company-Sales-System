package com.lavo.CompanyBackend.AppUser.responsesDto;


import com.lavo.CompanyBackend.AppUser.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailsAllUsersResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private String phoneNo;
    private String email;
    private String createdDate;
    private String lastLoginDate;
    private Set<Role> roles;
}
