package com.example.Inventory.model.mapper.impl;

import com.example.Inventory.model.dto.MercanciaDTO;
import com.example.Inventory.model.entity.Mercancia;
import com.example.Inventory.model.entity.users;
import com.example.Inventory.model.mapper.GenericMapper;

public class MercanciaMapper implements GenericMapper<Mercancia, MercanciaDTO>{

    @Override
    public Mercancia dtoToPojo(MercanciaDTO dto) {

        users users = new users();     
        return Mercancia.builder()
                .id(dto.getId())
                .fechaEntrada(dto.getFechaEntrada())
                .usuario(users)
                .build();
    }

    @Override
    public MercanciaDTO pojoToDto(Mercancia pojo) {
        return MercanciaDTO.builder()
                .id(pojo.getId())
                .fechaEntrada(pojo.getFechaEntrada())
                .idUsuario(pojo.getUsuario().getId())
                .build();
    }

}
