package com.example.Inventory.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.example.Inventory.model.entity.role;
import com.example.Inventory.service.roleServiceImpl;

import jakarta.persistence.EntityNotFoundException;

public class RoleControllerTest {
    @InjectMocks
    private RoleController roleController;

    @Mock
    private roleServiceImpl service;

    private role role1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role1 = new role();
        role1.setId(1L);
        role1.setNombre("Administrador");
    }

    @Test
    void testDeleteRole() {

        when(service.findById(1L)).thenReturn(Optional.of(role1));
        doNothing().when(service).deleteById(1L);


        ResponseEntity<HttpStatus> response = roleController.deleteRole(1L);


        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).findById(1L);
        verify(service, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteRole_NotFound() {

        when(service.findById(1L)).thenReturn(Optional.empty());


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            roleController.deleteRole(1L);
        });

        assertEquals("El rol que intentas eliminar, no existe vuelva a intentarlo o comuniquese con soporte.", exception.getMessage());
        verify(service, times(1)).findById(1L);
    }

    @Test
    void testGetAllRoles() {
        List<role> roles = Arrays.asList(role1);
        when(service.findAll()).thenReturn(roles);

        ResponseEntity<List<role>> response = roleController.getAllRoles();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roles, response.getBody());
        verify(service, times(1)).findAll();
    }

    @Test
    void testGetAllRoles_Empty() {

        when(service.findAll()).thenReturn(Arrays.asList());


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            roleController.getAllRoles();
        });

        assertEquals("No hay roles disponibles.", exception.getMessage());
        verify(service, times(1)).findAll();
    }

    @Test
    void testGetRoleById() {

        when(service.findById(1L)).thenReturn(Optional.of(role1));


        ResponseEntity<role> response = roleController.getRoleById(1L);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(role1, response.getBody());
        verify(service, times(1)).findById(1L);
    }
    @Test
    void testGetRoleById_NotFound() {

        when(service.findById(1L)).thenReturn(Optional.empty());


        IdNotFoundException exception = assertThrows(IdNotFoundException.class, () -> {
            roleController.getRoleById(1L);
        });


        assertEquals("id numero: 1 No existe actualmente en la base de datos, por favor verifique e intentelo nuevamente", exception.getMessage());
        verify(service, times(1)).findById(1L);
    }
    @Test
    void testSaveRole() {
        doNothing().when(service).save(any(role.class));

        ResponseEntity<HttpStatus> response = roleController.saveRole(role1);


        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(service, times(1)).save(role1);
    }

    @Test
    void testUpdateRole() {
        when(service.findById(1L)).thenReturn(Optional.of(role1));
        doNothing().when(service).update(any(role.class));

    
        ResponseEntity<HttpStatus> response = roleController.updateRole(role1);

    
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service, times(1)).findById(1L);
        verify(service, times(1)).update(role1);
    }
    @Test
    void testUpdateRole_NotFound() {
    
        when(service.findById(1L)).thenReturn(Optional.empty());

    
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            roleController.updateRole(role1);
        });

    
        assertEquals("El rol con el ID 1 no existe.", exception.getMessage());
        verify(service, times(1)).findById(1L);
    }
}
