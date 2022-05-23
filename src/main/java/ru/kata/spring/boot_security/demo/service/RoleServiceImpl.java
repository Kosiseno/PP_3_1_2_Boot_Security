package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepositoryJpa;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepositoryJpa roleRepositoryJpa;

    @Autowired
    public RoleServiceImpl(RoleRepositoryJpa roleRepositoryJpa) {
        this.roleRepositoryJpa = roleRepositoryJpa;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepositoryJpa.findAll();
    }

    @Override
    @Transactional
    public Role getRoleById(Long id) {
        return roleRepositoryJpa.findById(id).get();
    }
}
