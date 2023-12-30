package com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryNameResponse {

    private List<Long> id;

    private List<String> names;

    private String message;



}
