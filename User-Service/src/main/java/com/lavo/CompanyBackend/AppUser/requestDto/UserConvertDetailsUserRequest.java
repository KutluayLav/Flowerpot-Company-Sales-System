package com.lavo.CompanyBackend.AppUser.requestDto;

import com.lavo.CompanyBackend.AppUser.model.User;
import com.lavo.CompanyBackend.AppUser.responsesDto.DetailsUserResponse;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConvertDetailsUserRequest {

    public DetailsUserResponse convertToDetailsUserRequest(User user) {

        DetailsUserResponse detailsUserRequest= DetailsUserResponse.builder()
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .email(user.getEmail())
                .phoneNo(user.getPhoneNo())
                .build();

        return detailsUserRequest;
    }

}
