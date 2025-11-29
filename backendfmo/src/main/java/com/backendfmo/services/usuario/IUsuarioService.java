package com.backendfmo.services.usuario;

import java.net.URI;

import com.backendfmo.models.Usuario;

public interface IUsuarioService {

    Usuario saveUsuario(Usuario u);
    Usuario findUsuarioById(Integer i);
    URI createUri(String path, Usuario u);
    

}
