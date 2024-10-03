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
import com.example.Inventory.model.entity.role;
import com.example.Inventory.service.interfaces.RoleService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService service;

    
    @GetMapping("/findAll")
    public ResponseEntity<List<role>> getAllRoles() {
        List<role> roles = service.findAll();
        if (roles.isEmpty()) {
            throw new EntityNotFoundException("No hay roles disponibles.");
        }
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<role> getRoleById(@PathVariable Long id) {
        Optional<role> roleOptional = service.findById(id);
        if (!roleOptional.isPresent()) {
            throw new IdNotFoundException(id.toString());
        }
        return ResponseEntity.ok(roleOptional.get());
    }


    @PostMapping("/create")
    public ResponseEntity<HttpStatus> saveRole(@RequestBody role roles) {
        service.save(roles);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/editar")
    public ResponseEntity<HttpStatus> updateRole(@RequestBody role roles) {
        if (service.findById(roles.getId()).isEmpty()) {
            throw new EntityNotFoundException("El rol con el ID " + roles.getId() + " no existe.");
        }
        service.update(roles);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Long id) {
        if (service.findById(id).isEmpty()) {
            throw new EntityNotFoundException("El rol que intentas eliminar, no existe vuelva a intentarlo o comuniquese con soporte.");
        }
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
