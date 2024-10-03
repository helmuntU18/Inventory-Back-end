package com.example.Inventory.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private Long id;
    private String nombre;
    private Integer edad;
    private Long idRol;
    private String cargo;
    private String fechaIngreso;
}
