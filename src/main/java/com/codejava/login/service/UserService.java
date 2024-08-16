package com.codejava.login.service;



import com.codejava.login.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    List<User> searchUsers(String query);

    User getUserById(Long id);
    void updateUser(Long id, User user);
    void deleteUser(Long id);
}
