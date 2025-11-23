package com.backendfmo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendfmo.models.UserModel;
import com.backendfmo.repository.IUserRepository;

@Service
public class UserServiceImpl {

    @Autowired
    private IUserRepository userService;

    public List<UserModel> getAllUsers(){
        return userService.findAll();
    }

}
