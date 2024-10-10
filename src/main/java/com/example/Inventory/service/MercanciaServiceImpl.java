package com.example.Inventory.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Inventory.model.entity.Mercancia;
import com.example.Inventory.repository.MercanciaRepository;
import com.example.Inventory.service.interfaces.MercanciaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MercanciaServiceImpl implements MercanciaService{
    
    private final MercanciaRepository repository;

    @Override
    public List<Mercancia> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Mercancia> findById(Long Id) {
        return repository.findById(Id);
    }

    @Override
    public void update(Mercancia mercancia) {
        String usuarioRegistrado = mercancia.getUsuario().getNombre();

        if (!usuarioRegistrado.equals(mercancia.getUsuario_modificacion().getNombre())) {
            throw new IllegalArgumentException("No tienes permiso para modificar esta mercancía.");
        }

        validarNombre(mercancia);

        validarFechaIngreso(mercancia.getFechaEntrada());
        repository.save(mercancia);
    }

    @Override
    public void deleteById(Long id, String usuarioModificacion) {
        Optional<Mercancia> MercanciaById = repository.findById(id);

        if (MercanciaById.isPresent()) {
            Mercancia mercancia = MercanciaById.get();
            String usuarioRegistrado = mercancia.getUsuario().getNombre();

            if (!usuarioModificacion.equals(usuarioRegistrado)) {
                throw new IllegalArgumentException("No tienes permiso para eliminar esta mercancía.");
            }

        repository.deleteById(id);
    } else {
        throw new IllegalArgumentException("La mercancía no existe.");
    }
}

    @Override
    public void save(Mercancia mercancia) {
        validarNombre(mercancia);
        validarFechaIngreso(mercancia.getFechaEntrada());
        repository.save(mercancia);
    }

    private void validarNombre(Mercancia mercancia) {
        Optional<Mercancia> mercanciaExistente = repository.findByNombreProducto(mercancia.getNombreProducto());
        if (mercanciaExistente.isPresent() && !mercanciaExistente.get().getId().equals(mercancia.getId())) {
            throw new IllegalArgumentException("Ya existe una mercancía con el mismo nombre.");
        }
    }

    private void validarFechaIngreso(Date fechaIngreso) {
        if (fechaIngreso.after(new Date())) {
            throw new IllegalArgumentException("La fecha de ingreso no puede ser posterior a la fecha actual.");
        }
    }

}
