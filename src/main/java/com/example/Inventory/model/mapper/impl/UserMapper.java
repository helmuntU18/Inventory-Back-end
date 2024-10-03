package com.example.Inventory.model.mapper.impl;

import com.example.Inventory.model.dto.UsersDTO;
import com.example.Inventory.model.entity.role;
import com.example.Inventory.model.entity.users;
import com.example.Inventory.model.mapper.GenericMapper;

public class UserMapper implements GenericMapper<users, UsersDTO>{

    @Override
    public users dtoToPojo(UsersDTO dto) {
            return users.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .edad(dto.getEdad())
                .id_rol(new role(dto.getIdRol(), dto.getCargo()))  
                .build();
    }

    @Override
    public UsersDTO pojoToDto(users pojo) {
        return UsersDTO.builder()
            .id(pojo.getId())
            .nombre(pojo.getNombre())
            .edad(pojo.getEdad())
            .idRol(pojo.getId_rol().getId())  
            .build();
    }



}
