package com.shoptbdddatn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoptbdddatn.model.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee,Integer> {
    
}
