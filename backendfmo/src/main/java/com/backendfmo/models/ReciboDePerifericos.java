package com.backendfmo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "recibo_de_perifericos")
@Data
public class ReciboDePerifericos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fmo_serial")
    private String fmoSerial;

    // Relación con el Encabezado (Padre)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encabezado_recibo")
    private EncabezadoRecibo encabezadoRelacion;

    // Relación con el Catálogo (Data existente en BD)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "componentes_computadora_internos")
    private ComponenteInterno componenteRef;
}