package com.backendfmo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backendfmo.dtos.request.reciboperifericos.RegistroPerifericosDTO;
import com.backendfmo.dtos.response.BusquedaPerifericoDTO;
import com.backendfmo.models.Usuario;
import com.backendfmo.services.perifericos.PerifericosService;

@RestController
public class PerifericosController {

    @Autowired
    private PerifericosService perifericosService;

    @PostMapping("/crear")
    public ResponseEntity<Usuario> crearRegistroPerifericos(@RequestBody RegistroPerifericosDTO dto) {
        Usuario usuarioGuardado = perifericosService.registrarPerifericos(dto);
        return ResponseEntity.ok(usuarioGuardado);
    }

    @GetMapping("/buscare/{fmoSerial}") // Ejemplo URL: /api/perifericos/buscar?serial=KEY-HP-0099
    public ResponseEntity<BusquedaPerifericoDTO> buscarPeriferico(@PathVariable String fmoSerial) {
        BusquedaPerifericoDTO resultado = perifericosService.buscarPorSerial(fmoSerial);
        return ResponseEntity.ok(resultado);
    }
}