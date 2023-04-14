package com.shoptbdddatn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoptbdddatn.model.Token;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Token findByToken(String token);
}
