package com.backendfmo.dtos.request.reciboequipos;

import java.util.List;

import lombok.Data;

@Data
public class RegistroTotalDTO {

    // Datos del Usuario
    private String usuario;
    private String clave;
    private Integer ficha;
    private String nombre;
    private String extension;
    private String gerencia;
    
    // Lista de Recibos a insertar
    private List<EncabezadoDTO> recibos;
}


