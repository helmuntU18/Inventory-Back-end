package com.example.Inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Inventory.model.entity.users;
@Repository
public interface usersRepository extends JpaRepository<users, Long>{

}
