package com.proj.customer.dto;

import java.util.List;

import com.proj.customer.model.Automobile;
import com.proj.customer.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailsResponse {
    private Customer customer;
    private List<Automobile> automobiles;
}
