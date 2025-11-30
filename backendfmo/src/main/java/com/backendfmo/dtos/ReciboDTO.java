package com.backendfmo.dtos;

import lombok.Data;

@Data
public class ReciboDTO {
    private String fmoEquipo;
    private String solicitudDAET;
    private String solicitudST;
    private String entregadoPor;
    private String recibidoPor;
    private String asignadoA;
    private String estatus;
    private String falla;
    private String fecha;
    private String observacion;
}
