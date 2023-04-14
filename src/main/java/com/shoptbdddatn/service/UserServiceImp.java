package com.shoptbdddatn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoptbdddatn.model.User;
import com.shoptbdddatn.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        // TODO Auto-generated method stub
        return userRepository.saveAndFlush(user);
        

    } 
}
