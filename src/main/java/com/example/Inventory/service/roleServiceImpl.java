package com.example.Inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Inventory.model.entity.role;
import com.example.Inventory.repository.roleRepository;
import com.example.Inventory.service.interfaces.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class roleServiceImpl implements RoleService{
    private final roleRepository repository;

    @Override
    public List<role> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<role> findById(Long Id) {
        return repository.findById(Id);
    }

    @Override
    public void update(role Role) {
        repository.save(Role);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(role Role) {
        repository.save(Role);
    }
}
