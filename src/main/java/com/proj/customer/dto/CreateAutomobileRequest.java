package com.proj.customer.dto;

import com.proj.customer.model.PreviousInsurance;

import jakarta.persistence.Embedded;
import lombok.Data;

@Data
public class CreateAutomobileRequest {
    private Integer customerId;

    private String make;

    private String made;

    private String year;

    private String licensePlateNumber;
    private String vehicleIdentificationNumber;

    @Embedded
    private PreviousInsurance previousInsurance;
}
