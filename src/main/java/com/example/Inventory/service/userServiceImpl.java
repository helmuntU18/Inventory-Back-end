package com.example.Inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Inventory.model.entity.users;
import com.example.Inventory.repository.usersRepository;
import com.example.Inventory.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class userServiceImpl implements UserService{

    private final usersRepository repository;

    @Override
    public List<users> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<users> findById(Long Id) {
        return repository.findById(Id);
    }

    @Override
    public void update(users user) {
        repository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(users user) {
        repository.save(user);
    }
    

}
