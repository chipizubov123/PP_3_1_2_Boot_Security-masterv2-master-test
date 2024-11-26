package ru.kata.spring.boot_security.demo.Services;


import ru.kata.spring.boot_security.demo.Models.User;

import java.util.List;

public interface UserService {

    User getUserByUsername(String username);

    void addUser(User user);

    User getUserById(Long id);

    void updateUser(User user);

    void removeUserById(Long id);

    List<User> listUsers();

}
