package com.example.Inventory.model.mapper.impl;

import com.example.Inventory.model.dto.RoleDTO;
import com.example.Inventory.model.entity.role;
import com.example.Inventory.model.mapper.GenericMapper;

public class RoleMapper implements GenericMapper<role, RoleDTO>{

    @Override
    public role dtoToPojo(RoleDTO dto) {
        return role.builder()
            .id(dto.getId())
            .Nombre(dto.getNombreCargo())
            .build();
    }

    @Override
    public RoleDTO pojoToDto(role pojo) {
        return RoleDTO.builder()
        .id(pojo.getId())
        .nombreCargo(pojo.getNombre())
        .build();
    }

}
