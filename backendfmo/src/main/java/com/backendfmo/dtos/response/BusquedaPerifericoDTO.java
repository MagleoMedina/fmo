package com.backendfmo.dtos.response;

import lombok.Data;

@Data
public class BusquedaPerifericoDTO {
    // Datos del Periférico (Hijo)
    private String fmoSerial;
    private String tipoComponente; // Ej: "Teclado" (Viene del Catálogo)

    // Datos del Encabezado (Padre)
    private String fmoEquipoLote; // El código del encabezado
    private String solicitudST;
    private String estatus;
    private String fecha;
    private String observacion;

    // Datos del Usuario (Abuelo)
    private String usuarioNombre;
    private String usuarioFicha;
    private String usuarioGerencia;
}
