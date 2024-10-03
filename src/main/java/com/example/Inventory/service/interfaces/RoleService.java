package com.example.Inventory.service.interfaces;
import java.util.List;
import java.util.Optional;

import com.example.Inventory.model.entity.role;


public interface RoleService {
    List<role> findAll();
    Optional<role> findById(Long Id);
    void update(role Role);
    void deleteById(Long id);
    void save (role Role);
}
