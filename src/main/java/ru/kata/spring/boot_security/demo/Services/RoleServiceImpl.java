package ru.kata.spring.boot_security.demo.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.RoleDAO;
import ru.kata.spring.boot_security.demo.Models.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public Set<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByUsername(String username) {
        return roleDAO.getRoleByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getSetOfRoles(String[] roleNames) {
        return roleDAO.getSetOfRoles(roleNames);
    }

    @Override
    @Transactional(readOnly = true)
    public void add(Role role) {
        roleDAO.add(role);
    }

    @Override
    @Transactional(readOnly = true)
    public void edit(Role role) {
        roleDAO.edit(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getById(Long id) {
        return roleDAO.getById(id);
    }

}
