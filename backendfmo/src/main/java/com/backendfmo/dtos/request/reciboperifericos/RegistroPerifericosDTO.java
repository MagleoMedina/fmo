package com.backendfmo.dtos.request.reciboperifericos;

import java.util.List;

import lombok.Data;

@Data
public class RegistroPerifericosDTO {
    // Datos Usuario (Para crear uno nuevo siguiendo tu lógica)
    private String usuario;
    private String clave;
    private Integer ficha;
    private String nombre;
    private String gerencia;

    // Datos Encabezado
    private String fmoEquipo; // En este caso será el código del lote de periféricos
    private String solicitudST;
    private String estatus;
    private String fecha;
    private String observacion;

    // La lista de periféricos
    private List<PerifericoItemDTO> itemsPerifericos;
}
