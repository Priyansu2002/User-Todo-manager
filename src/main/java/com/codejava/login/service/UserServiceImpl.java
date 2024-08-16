package com.codejava.login.service;

import com.codejava.login.entity.User;
import com.codejava.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepository repo;



    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public List<User> searchUsers(String query) {
        return repo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, query);
    }

    @Override
    public User getUserById(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    @Override
    public void updateUser(Long id, User user) {
        User existingUser = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        // Update any other fields as needed
        repo.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        repo.deleteById(id);
    }
}
