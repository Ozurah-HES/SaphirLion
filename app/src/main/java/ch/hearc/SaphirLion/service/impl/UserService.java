package ch.hearc.SaphirLion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.SaphirLion.model.User;
import ch.hearc.SaphirLion.repository.UserRepository;
import ch.hearc.SaphirLion.service.UserService_I;

@Service
public class UserService implements UserService_I {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }
    
    
}
