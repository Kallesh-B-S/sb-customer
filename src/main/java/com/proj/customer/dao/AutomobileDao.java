package com.proj.customer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.customer.model.Automobile;

public interface AutomobileDao extends JpaRepository<Automobile, Integer> {

    List<Automobile> findByCustomerId(Integer customerId);
    
}
