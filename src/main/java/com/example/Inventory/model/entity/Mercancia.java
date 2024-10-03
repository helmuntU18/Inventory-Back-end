package com.example.Inventory.model.entity;




import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "mercancia")
public class Mercancia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombreProducto")
    private String nombreProducto;
    private int cantidad;
    private Date fechaEntrada;
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private users usuario;
    @ManyToOne
    @JoinColumn(name="usuario_modificacion")
    private users usuario_modificacion;
    private Date fecha_modificacion;

}
