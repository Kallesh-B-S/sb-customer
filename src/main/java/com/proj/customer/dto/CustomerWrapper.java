package com.proj.customer.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CustomerWrapper {
    private Integer id;
    private String fullName;
    private LocalDate dob;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String email;
    private String phone;
}
