package com.shoptbdddatn.service;

import com.shoptbdddatn.model.Token;

public interface TokenService {
    Token createToken(Token token);

    Token findByToken(String token);
}
