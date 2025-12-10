package com.backendfmo.dtos.response.entregasdaet;

import java.util.List;

import lombok.Data;

@Data

public class BusquedaDaetDTO {
    // --- Datos Específicos de la Entrega (Lo que buscaste) ---
    private String fmoSerial;
    private String actividad;
    private String estado;
    private String tipoPeriferico; // Nombre del Periférico (Ej: "CPU Torre")
    private String identifique;

    // --- Datos del Encabezado (Padre) ---
    private String fmoEquipoLote; // El código del lote/encabezado
    private String solicitudDAET;
    private String estatusEncabezado;
    private String fecha;
    private String observacion;

    // --- Lista de Componentes Internos (Hijos) ---
    private List<ComponenteInternoResumenDTO> componentesInternos;
}