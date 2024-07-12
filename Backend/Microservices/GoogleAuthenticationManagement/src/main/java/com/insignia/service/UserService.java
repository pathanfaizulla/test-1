package com.insignia.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.insignia.entity.Provider;
import com.insignia.entity.User;
import com.insignia.repo.UserRepository;

public class UserService {
	@Autowired
    private UserRepository repo;
     
    public void processOAuthPostLogin(String username) {
        User existUser = repo.getUserByUsername(username);
         
        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setEnabled(true);          
             
            repo.save(newUser);        
        }
         
    }
}
