package com.example.Inventory.service;

import com.example.Inventory.model.entity.users;
import com.example.Inventory.repository.usersRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceServiceImplTest {

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
    void testFindAll(){
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        assertNotNull(userService.findAll());
    }
    @Test
    void testFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<users> foundUser = userService.findById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals("Andres Quintero", foundUser.get().getNombre());
    }

    @Test
    void testFindById_NotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<users> foundUser = userService.findById(1L);
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testUpdate() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        user.setNombre("Andres Quintero Updated");
        userService.update(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteById() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NotExistException() {
        doThrow(new IllegalArgumentException("El ususario que intenta eliminar no existe.")).when(userRepository).deleteById(999L);

    
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteById(999L);
        });

        assertEquals("El ususario que intenta eliminar no existe.", exception.getMessage());
    }

    @Test
    void testSave() {
        userService.save(user);

        verify(userRepository, times(1)).save(user);
    }
    
}
