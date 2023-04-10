package com.shoptbdddatn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoptbdddatn.model.ProductLine;

public interface IProductLineRepository extends JpaRepository<ProductLine,Integer> {
    
}
