package com.shoptbdddatn.service;

import com.shoptbdddatn.model.User;
import com.shoptbdddatn.security.UserPrincipal;

public interface UserService {
    User createUser(User user);
    UserPrincipal findByUsername(String username);
}
