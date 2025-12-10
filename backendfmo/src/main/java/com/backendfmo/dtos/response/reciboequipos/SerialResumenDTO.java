package com.backendfmo.dtos.response.reciboequipos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SerialResumenDTO {
    private String tipoComponente; // Ej: "Memoria RAM"
    private String marca;
    private String serial;
    private String capacidad;
}