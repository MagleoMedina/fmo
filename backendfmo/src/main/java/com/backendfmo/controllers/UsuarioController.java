package com.backendfmo.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backendfmo.models.Usuario;
import com.backendfmo.services.usuario.IUsuarioService;

import jakarta.validation.Valid;

@RestController

public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @PostMapping("/crearUsuario")
    public ResponseEntity<?> saveUsuario(@Valid @RequestBody Usuario usuario) {
       try {
            service.saveUsuario(usuario);
            URI location = service.createUri("/usuario/{id}", usuario);
            return (ResponseEntity<?>) ResponseEntity.created(location).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> findUsuarioById(@Valid @PathVariable Integer id){
        try {
            return ResponseEntity.ok(service.findUsuarioById(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage()); 
        }
    }
}
