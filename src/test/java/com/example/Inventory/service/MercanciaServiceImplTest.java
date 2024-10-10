package com.example.Inventory.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Inventory.model.entity.Mercancia;
import com.example.Inventory.model.entity.users;
import com.example.Inventory.repository.MercanciaRepository;

public class MercanciaServiceImplTest {

    @InjectMocks
    private MercanciaServiceImpl service;

    @Mock
    private MercanciaRepository repository;

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
    void testDeleteById() {
        when(repository.findById(1L)).thenReturn(Optional.of(mercancia));
        users usuario1 = new users();
        usuario1.setNombre("Usuario1");
        
        mercancia.setUsuario(usuario1);  

        users usuarioModificacion = new users();
        usuarioModificacion.setNombre("Usuario1");  
        mercancia.setUsuario_modificacion(usuarioModificacion);


        service.deleteById(1L, usuarioModificacion.getNombre());


        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByIdNonExistExcepcion() {

        Long nonExistentId = 999L; 

        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());
    
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.deleteById(nonExistentId, "usuario1");
        });
    
        assertEquals("La mercancía no existe.", exception.getMessage());

    }

    @Test
    void testDeleteByIdNoPermissionExcepcion() { 
        Mercancia mercanciaConPermiso = new Mercancia();
        mercanciaConPermiso.setId(1L);
        users usuarioConPermiso = new users();
        usuarioConPermiso.setNombre("Usuario1");
        mercanciaConPermiso.setUsuario(usuarioConPermiso);     
        when(repository.findById(1L)).thenReturn(Optional.of(mercanciaConPermiso));  
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.deleteById(1L, "Usuario2");  
        });
        assertEquals("No tienes permiso para eliminar esta mercancía.", exception.getMessage());

    }
    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(mercancia));
        assertNotNull(service.findAll());
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(mercancia));
        Optional<Mercancia> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(mercancia, result.get());
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(997L)).thenReturn(Optional.empty());

        Optional<Mercancia> result = service.findById(997L);
        assertFalse(result.isPresent());
    }

    @Test
    void testSave() {
        
        when(repository.findByNombreProducto(mercancia.getNombreProducto())).thenReturn(Optional.empty());

        when(repository.save(any(Mercancia.class))).thenReturn(mercancia);
        when(repository.save(any(Mercancia.class))).thenReturn(mercancia);

        verify(repository, never()).save(any(Mercancia.class));

    }

    @Test
    void testSaveNameException() {

        Mercancia mercanciaExistente = new Mercancia();
        mercanciaExistente.setId(2L); 
        mercanciaExistente.setNombreProducto("Rines Grises");
        when(repository.findByNombreProducto("Rines Grises")).thenReturn(Optional.of(mercanciaExistente));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.save(mercancia);  
        });
        assertEquals("Ya existe una mercancía con el mismo nombre.", exception.getMessage());

    }

    @Test
    void testUpdate() {
        users usuarioConPermiso = new users();
        usuarioConPermiso.setNombre("Usuario1");
        mercancia.setUsuario(usuarioConPermiso);
        mercancia.setUsuario_modificacion(usuarioConPermiso);
        when(repository.findById(1L)).thenReturn(Optional.of(mercancia));
        mercancia.setNombreProducto("Prueba Update"); 
        service.update(mercancia);  
        verify(repository, times(1)).save(mercancia); 
        assertEquals("Prueba Update", mercancia.getNombreProducto());
    }

    @Test
    void testUpdateNoPermission() {
        users usuarioConPermiso = new users();
        usuarioConPermiso.setNombre("Usuario1");
        users usuarioSinPermiso = new users();
        usuarioSinPermiso.setNombre("Usuario2");
        mercancia.setUsuario(usuarioConPermiso);
        mercancia.setUsuario_modificacion(usuarioSinPermiso); 

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.update(mercancia);
        });

        
        assertEquals("No tienes permiso para modificar esta mercancía.", exception.getMessage());
    }

    @Test
    void testUpdateNombreException() {

        users usuarioConPermiso = new users();
        usuarioConPermiso.setNombre("Usuario1");
        mercancia.setUsuario(usuarioConPermiso);
        mercancia.setUsuario_modificacion(usuarioConPermiso);
        mercancia.setId(1L);
        mercancia.setNombreProducto("Producto 2"); 
        Mercancia mercanciaExistente = new Mercancia();
        mercanciaExistente.setId(2L);
        mercanciaExistente.setNombreProducto("Producto 2"); 
        when(repository.findByNombreProducto("Producto 2")).thenReturn(Optional.of(mercanciaExistente));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.update(mercancia);
        });
        assertEquals("Ya existe una mercancía con el mismo nombre.", exception.getMessage());
    }
}
