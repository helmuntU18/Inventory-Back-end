package com.example.Inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.Inventory.model.entity.Mercancia;
@Repository
public interface MercanciaRepository extends JpaRepository<Mercancia, Long>{

    Optional<Mercancia> findByNombreProducto(String nombreProducto);

}
