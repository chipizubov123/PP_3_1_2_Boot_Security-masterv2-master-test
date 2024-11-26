package ru.kata.spring.boot_security.demo.Services;

import ru.kata.spring.boot_security.demo.Models.Role;

import java.util.Set;

public interface RoleService {

    Set<Role> getAllRoles();

    Role getRoleByUsername(String username);

    Set<Role> getSetOfRoles(String[] roleNames);

    void add(Role role);

    void edit(Role role);

    Role getById(Long id);
}
