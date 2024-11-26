package ru.kata.spring.boot_security.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.UserDAO;
import ru.kata.spring.boot_security.demo.Models.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDAO userDAO;

    private final BCryptPasswordEncoder bCryptPasswordEncode;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncode) {
        this.userDAO = userDAO;
        this.bCryptPasswordEncode = bCryptPasswordEncode;
    }


    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
        user.setPassword(bCryptPasswordEncode.encode(user.getPassword()));
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    @Transactional()
    public void updateUser(User user) {

        if (user.getRoles().isEmpty()) {
            var roles = userDAO.getUserById(user.getId()).getRoles();
            user.setRoles(roles);
        }
        if (user.getPassword().isEmpty()) {
            var password = userDAO.getUserByUsername(user.getUsername()).getPassword();
            user.setPassword(password);
        } else {
            user.setPassword(bCryptPasswordEncode.encode(user.getPassword()));
        }
        userDAO.updateUser(user);
    }

    @Override
    @Transactional()
    public void removeUserById(Long id) {
        userDAO.removeUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userDAO.listUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.getUserByUsername(username);
    }
}
