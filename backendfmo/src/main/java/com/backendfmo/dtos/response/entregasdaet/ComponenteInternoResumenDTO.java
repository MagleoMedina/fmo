package com.backendfmo.dtos.response.entregasdaet;

import lombok.Data;

@Data
public class ComponenteInternoResumenDTO {
    private String nombreComponente; // Ej: "Memoria RAM"
    private Integer cantidad;
}