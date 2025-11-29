package com.backendfmo.services.usuario;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.backendfmo.models.Usuario;
import com.backendfmo.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public Usuario saveUsuario(Usuario u) {
        
        return usuarioRepository.save(u);
    }

    @Override
    public Usuario findUsuarioById(Integer i) {
        return usuarioRepository.findById(i).orElseThrow();
    }

     @Override
     public URI createUri(String path, Usuario s){

        URI location = ServletUriComponentsBuilder.
            fromCurrentRequest().
            path(path).
            buildAndExpand(s.getId()).
            toUri();

        return location;
    }

}
