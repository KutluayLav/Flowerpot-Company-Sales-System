package com.lavo.CompanyBackend.AppUser.dto;

import com.lavo.CompanyBackend.AppUser.model.User;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConvertDetailsUserRequest {

    public DetailsUserRequest convertToDetailsUserRequest(User user) {

        DetailsUserRequest detailsUserRequest=DetailsUserRequest.builder()
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .email(user.getEmail())
                .phoneNo(user.getPhoneNo())
                .build();

        return detailsUserRequest;
    }

}
