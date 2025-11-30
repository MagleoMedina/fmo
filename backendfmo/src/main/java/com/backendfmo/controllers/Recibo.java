package com.backendfmo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backendfmo.dtos.RegistroCompletoDTO;
import com.backendfmo.models.Usuario;
import com.backendfmo.services.encabezado.GestionService;

import jakarta.validation.Valid;

@RestController
public class Recibo {

@Autowired
private GestionService gestionService;
 @PostMapping("/ingreso-equipo")
public ResponseEntity<Usuario> crearIngreso(@Valid @RequestBody RegistroCompletoDTO dto) {
    return ResponseEntity.ok(gestionService.guardarUsuarioYRecibos(dto));
}
}
