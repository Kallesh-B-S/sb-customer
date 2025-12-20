package com.proj.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUpdateResponse {
    private Integer id;
    private String message;
}
