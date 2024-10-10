package com.example.Inventory.service;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.example.Inventory.model.entity.role;
import com.example.Inventory.repository.roleRepository;

public class roleServiceImplTest {

    @InjectMocks
    private roleServiceImpl service;

    @Mock
    private roleRepository repository;

    private role role1 ;
    private role role2 ;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        role1 = new role();
        role1.setId(1L);
        role1.setNombre("Administrador");

        role2 = new role();
        role2.setId(2L);
        role2.setNombre("Asesor de ventas");   
    }


    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(role1, role2));

    
        List<role> foundRoles = service.findAll();

    
        assertNotNull("La lista de roles no debería ser nula", foundRoles);
        assertEquals(2, foundRoles.size(), "Debería haber 2 roles en la lista");
        
        
        assertEquals("Administrador", foundRoles.get(0).getNombre(), "El primer rol debería ser 'Administrador'");
        assertEquals("Asesor de ventas", foundRoles.get(1).getNombre(), "El segundo rol debería ser 'Asesor de ventas'");

        
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(role1));


        Optional<role> foundRole = service.findById(1L);


        assertTrue(foundRole.isPresent(), "El rol debería estar presente");
        assertEquals("Administrador", foundRole.get().getNombre(), "El nombre del rol debería ser 'Administrador'");
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        service.save(role1);
        verify(repository, times(1)).save(role1);
    }

    @Test
    void testUpdate() {
        when(repository.findById(1L)).thenReturn(Optional.of(role1));
        service.update(role1);
        verify(repository, times(1)).save(role1);
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(1L);
        service.deleteById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}
