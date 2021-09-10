package com.neoris.tcl.security.service;

import java.util.List;
import java.util.Optional;

import com.neoris.tcl.security.models.User;

public interface IUserService {

    Optional<User> findByUsername(String username);

    User saveUser(User user);
    
    List<User> findAll();
    
    void delete(User user);
    
    void deleteAll(List<User> users);

	Optional<User> findById(Integer id);

}