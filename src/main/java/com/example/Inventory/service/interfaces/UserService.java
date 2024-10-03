package com.example.Inventory.service.interfaces;
import java.util.List;
import java.util.Optional;

import com.example.Inventory.model.entity.users;


public interface UserService {
List<users> findAll();
    Optional<users> findById(Long Id);
    void update(users user);
    void deleteById(Long id);
    void save (users user);
}
