package com.example.Inventory.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Inventory.exception.IdNotFoundException;
import com.example.Inventory.model.entity.users;
import com.example.Inventory.service.interfaces.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    
    @GetMapping("/findAll")
    public ResponseEntity<List<users>> getAllUsers() {
        List<users> users = service.findAll();
        if (users.isEmpty()) {
            throw new EntityNotFoundException("No hay usuarios disponibles. Por favor comunicarse con soporte");
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<users> getUserById(@PathVariable Long id) {
        Optional<users> userOptional = service.findById(id);
        if (!userOptional.isPresent()) {
            throw new IdNotFoundException(id.toString());
        }
        return ResponseEntity.ok(userOptional.get());
    }


    @PostMapping("/create")
    public ResponseEntity<HttpStatus> saveUser(@RequestBody users newUser) {
        service.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    
    @PutMapping("/editar")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody users user) {
        if (service.findById(user.getId()).isEmpty()) {
            throw new EntityNotFoundException("El usuario con el ID " + user.getId() + " no existe.");
        }
        service.update(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        if (service.findById(id).isEmpty()) {
            throw new EntityNotFoundException("El usuario que intentas eliminar, no existe vuelva a intentarlo o comuniquese con soporte");
        }
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
