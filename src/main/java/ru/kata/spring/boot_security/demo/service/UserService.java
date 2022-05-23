package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAllUsers();
    User findUserById(Long id);
    void deleteUser(Long id);
    UserDetails loadUserByUsername(String username);
    void editUser(User user);
    boolean addUser(User user);

}
