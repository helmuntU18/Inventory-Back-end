package com.example.Inventory.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.example.Inventory.model.entity.Mercancia;

public interface MercanciaService {
    List<Mercancia> findAll();
    Optional<Mercancia> findById(Long Id);
    void update(Mercancia inventory);
    void deleteById(Long id);
    void save (Mercancia inventory);
}
