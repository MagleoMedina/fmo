package com.backendfmo.models;



import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recibo_de_equipos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReciboDeEquipos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "marca")
    private String marca;

    @Column(name = "respaldo")
    private String respaldo;
    

    // Relación hacia arriba (hacia el Encabezado)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encabezado_recibo") // Nombre de la columna FK en tu tabla SQL
    @JsonBackReference
    private EncabezadoRecibo encabezadoRelacion;
}
