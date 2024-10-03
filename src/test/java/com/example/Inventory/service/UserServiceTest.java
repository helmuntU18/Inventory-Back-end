package com.example.Inventory.service;

import com.example.Inventory.model.entity.users;
import com.example.Inventory.repository.usersRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private userServiceImpl userService;

    @Mock
    private usersRepository userRepository;

    private users user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new users();
        user.setId(1L);
        user.setNombre("Andres Quintero");
    }

    @Test
    void testFindById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<users> foundUser = userService.findById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals("Andres Quintero", foundUser.get().getNombre());
    }

    @Test
    void testFindById_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<users> foundUser = userService.findById(1L);
        assertFalse(foundUser.isPresent());
    }

    
}
