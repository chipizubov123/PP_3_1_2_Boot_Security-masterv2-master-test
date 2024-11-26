package ru.kata.spring.boot_security.demo.DAO;

import ru.kata.spring.boot_security.demo.Models.Role;

import java.util.Set;

public interface RoleDAO {
    Set<Role> getAllRoles();

    Role getRoleByUsername(String username);

    Set<Role> getSetOfRoles(String[] roles);

    void add(Role role);

    void edit(Role role);

    Role getById(Long id);
}
