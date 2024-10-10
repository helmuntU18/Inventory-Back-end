package com.example.Inventory.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.example.Inventory.model.entity.users;
import com.example.Inventory.service.userServiceImpl;

import jakarta.persistence.EntityNotFoundException;

public class UserControllerTest {
    
    @InjectMocks
    private UserController userController;
    
    @Mock
    private userServiceImpl userService;

    private users user;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new users();
        user.setId(1L);
        user.setNombre("Andres Quintero");
    
    }
    

    @Test
    void testDeleteUser() {
        when(userService.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userService).deleteById(1L);


        ResponseEntity<HttpStatus> response = userController.deleteUser(1L);


        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).findById(1L);
        verify(userService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
    
        when(userService.findById(1L)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> userController.deleteUser(1L));
    }

    @Test
    void testGetAllUsers() {
        List<users> userList = List.of(user);
        when(userService.findAll()).thenReturn(userList);

        ResponseEntity<List<users>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).findAll();
    }

    @Test
    void testGetAllUsers_EmptyList() {
        when(userService.findAll()).thenReturn(List.of());

        assertThrows(EntityNotFoundException.class, () -> userController.getAllUsers());
    }
    @Test
    void testGetUserById() {
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        
        ResponseEntity<users> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> userController.getUserById(1L));
    }

    @Test
    void testSaveUser() {
        doNothing().when(userService).save(any(users.class));

        ResponseEntity<HttpStatus> response = userController.saveUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        when(userService.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userService).update(any(users.class));


        ResponseEntity<HttpStatus> response = userController.updateUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).findById(1L);
        verify(userService, times(1)).update(user);
    }

    @Test
    void testUpdateUser_NotFound() {
    
        when(userService.findById(1L)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> userController.updateUser(user));
    }
}
