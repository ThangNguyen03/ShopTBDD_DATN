package com.shoptbdddatn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoptbdddatn.model.Payment;

public interface IPaymentRepository extends JpaRepository<Payment,Integer> {
    
}
