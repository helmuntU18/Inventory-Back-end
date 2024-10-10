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
import com.example.Inventory.model.entity.Mercancia;
import com.example.Inventory.service.interfaces.MercanciaService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mercancia")
public class MercanciaController {

    private final MercanciaService service;

    @GetMapping("/findAll")
    public ResponseEntity<List<Mercancia>> getAllInventories() {
        List<Mercancia> inventories = service.findAll();
        if (inventories.isEmpty()) {
            throw new EntityNotFoundException("No hay registros de mercancia disponibles.");
        }
        return ResponseEntity.ok(inventories);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Mercancia> getMercanciaById(@PathVariable Long id) {
        Optional<Mercancia> inventoryOptional = service.findById(id);
        if (!inventoryOptional.isPresent()) {
            throw new IdNotFoundException(id.toString()); 
        }
        return ResponseEntity.ok(inventoryOptional.get());
    }
    
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> saveMercancia(@RequestBody Mercancia mercancia) {
        service.save(mercancia);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/editar")
    public ResponseEntity<HttpStatus> updateMercancia(@RequestBody Mercancia mercancia) {
        if (service.findById(mercancia.getId()).isEmpty()) {
            throw new EntityNotFoundException("la mercancia con el ID " + mercancia.getId() + " no existe.");
        }
        service.update(mercancia);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMercancia(@PathVariable Long id, @PathVariable String nombreUsario) {
        if (service.findById(id).isEmpty()) {
            throw new EntityNotFoundException("la mercancia que intentas eliminar, no existe vuelva a intentarlo o comuniquese con soporte");
        }
        service.deleteById(id,nombreUsario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
