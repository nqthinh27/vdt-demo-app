package com.vdt.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeFaker {
    private String firstName;
    private String lastName;
    private String address;
    private String avatar;

}
