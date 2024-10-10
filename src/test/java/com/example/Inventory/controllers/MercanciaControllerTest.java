package com.example.Inventory.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.Inventory.exception.IdNotFoundException;
import com.example.Inventory.model.entity.Mercancia;
import com.example.Inventory.model.entity.users;
import com.example.Inventory.service.interfaces.MercanciaService;

import jakarta.persistence.EntityNotFoundException;

public class MercanciaControllerTest {

    @InjectMocks
    private MercanciaController mercanciaController;
    
    @Mock
    private MercanciaService mercanciaService;

    private Mercancia mercancia;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        mercancia = new Mercancia();

        mercancia.setId(1L);
        mercancia.setNombreProducto("Rines Grises");

        users usuario = new users();
        usuario.setNombre("Helmunt");
        mercancia.setUsuario(usuario);

        users usuarioModificacion = new users();
        usuarioModificacion.setNombre("Helmunt");
        mercancia.setUsuario_modificacion(usuarioModificacion);

        mercancia.setCantidad(3);

        mercancia.setFechaEntrada(new Date(20/7/2024));
        mercancia.setFecha_modificacion(new Date(24/7/2024));
    }

    @Test
    void testDeleteMercancia() {
        when(mercanciaService.findById(1L)).thenReturn(Optional.of(mercancia));
        doNothing().when(mercanciaService).deleteById(1L, "Usuario");

        ResponseEntity<HttpStatus> response = mercanciaController.deleteMercancia(1L, "Usuario");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(mercanciaService, times(1)).deleteById(1L, "Usuario");
    }

    @Test
    void testDeleteMercancia_NotFound() {
        when(mercanciaService.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            mercanciaController.deleteMercancia(1L, "Usuario");
        });

        assertEquals("la mercancia que intentas eliminar, no existe vuelva a intentarlo o comuniquese con soporte", thrown.getMessage());
    }

    @Test
    void testGetAllInventories() {
        List<Mercancia> mercanciaList = Arrays.asList(mercancia);
        when(mercanciaService.findAll()).thenReturn(mercanciaList);

        ResponseEntity<List<Mercancia>> response = mercanciaController.getAllInventories();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetMercanciaById() {
        when(mercanciaService.findById(1L)).thenReturn(Optional.of(mercancia));

        ResponseEntity<Mercancia> response = mercanciaController.getMercanciaById(1L);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Rines Grises", response.getBody().getNombreProducto());
    }

    @Test
    void testGetMercanciaById_NotFound() {
        when(mercanciaService.findById(1L)).thenReturn(Optional.empty());

        IdNotFoundException thrown = assertThrows(IdNotFoundException.class, () -> {
            mercanciaController.getMercanciaById(1L);
        });

        assertEquals("id numero: 1 No existe actualmente en la base de datos, por favor verifique e intentelo nuevamente", thrown.getMessage());
    }
    @Test
    void testSaveMercancia() {
        doNothing().when(mercanciaService).save(any(Mercancia.class));

        ResponseEntity<HttpStatus> response = mercanciaController.saveMercancia(mercancia);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(mercanciaService, times(1)).save(any(Mercancia.class));
    }

    @Test
    void testUpdateMercancia() {
        when(mercanciaService.findById(1L)).thenReturn(Optional.of(mercancia));
        doNothing().when(mercanciaService).update(any(Mercancia.class));

        ResponseEntity<HttpStatus> response = mercanciaController.updateMercancia(mercancia);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mercanciaService, times(1)).update(any(Mercancia.class));
    }


    @Test
    void testUpdateMercancia_NotFound() {
        when(mercanciaService.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            mercanciaController.updateMercancia(mercancia);
        });

        assertEquals("la mercancia con el ID 1 no existe.", thrown.getMessage());
    }


}
