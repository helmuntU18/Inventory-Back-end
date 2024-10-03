package com.example.Inventory.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MercanciaDTO {
    private Long id;
    private Long idProducto; 
    private Date fechaEntrada;
    private Date fechaSalida;
    private Long idUsuario;
}
