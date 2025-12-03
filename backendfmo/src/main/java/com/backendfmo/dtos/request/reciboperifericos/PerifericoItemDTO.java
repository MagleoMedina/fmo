package com.backendfmo.dtos.request.reciboperifericos;

import lombok.Data;

@Data
public class PerifericoItemDTO {
    private Long idComponente; // ID de la tabla componentes_computadora_internos (Debe existir)
    private String fmoSerial;  // Serial del periférico
}
