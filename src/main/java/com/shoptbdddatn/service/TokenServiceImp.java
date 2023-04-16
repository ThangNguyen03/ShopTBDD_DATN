package com.shoptbdddatn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoptbdddatn.model.Token;
import com.shoptbdddatn.repository.TokenRepository;

@Service
public class TokenServiceImp implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;
    @Override
    public Token createToken(Token token) {
        // TODO Auto-generated method stub
    return tokenRepository.saveAndFlush(token);
    }

    @Override
    public Token findByToken(String token) {
        // TODO Auto-generated method stub
       return tokenRepository.findByToken(token);
    }
    
}
