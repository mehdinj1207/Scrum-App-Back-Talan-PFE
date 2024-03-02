package com.example.ScrumWise.rest.dto;

import com.example.ScrumWise.model.entity.Country;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {
    private Long idUser;
    private String cin;
    private String firstname;
    private String lastname;
    private String position;
    private String role;
    private boolean isActive;
    private String email;
    private String tel;
    private Boolean status;
    private DepartmentDto department;
    private byte[] data;
    private CountryDto country;

}
