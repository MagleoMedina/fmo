package com.backendfmo.dtos.response.reciboperifericos;

import lombok.Data;

@Data
public class ComponenteItemResponseDTO {
    private Long idComponente; // ID del Periférico (1=Monitor, etc.)
    private String fmoSerial;  // Serial específico
}