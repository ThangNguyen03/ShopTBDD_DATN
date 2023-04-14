package com.shoptbdddatn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoptbdddatn.model.Office;

public interface IOfficeRepository extends JpaRepository<Office,Integer> {
    
}
