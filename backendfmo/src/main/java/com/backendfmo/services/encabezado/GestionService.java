package com.backendfmo.services.encabezado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backendfmo.dtos.ReciboDTO;
import com.backendfmo.dtos.RegistroCompletoDTO;
import com.backendfmo.models.EncabezadoRecibo;
import com.backendfmo.models.Usuario;
import com.backendfmo.repository.UsuarioRepository;

@Service
public class GestionService {

    @Autowired
    private UsuarioRepository usuarioRepository; 

    @Transactional
    public Usuario guardarUsuarioYRecibos(RegistroCompletoDTO dto) {
        // 1. Llenar datos del Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuario(dto.getUsuario());
        nuevoUsuario.setClave(dto.getClave()); // Recuerda encriptar esto en producción
        nuevoUsuario.setFicha(dto.getFicha());
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setExtension(dto.getExtension());
        nuevoUsuario.setGerencia(dto.getGerencia());

        // 2. Llenar y vincular los Recibos
        if (dto.getRecibos() != null) {
            for (ReciboDTO reciboDto : dto.getRecibos()) {
                EncabezadoRecibo recibo = new EncabezadoRecibo();
                
                recibo.setFmoEquipo(reciboDto.getFmoEquipo());
                recibo.setSolicitudDAET(reciboDto.getSolicitudDAET());
                recibo.setSolicitudST(reciboDto.getSolicitudST());
                recibo.setEntregadoPor(reciboDto.getEntregadoPor());
                recibo.setRecibidoPor(reciboDto.getRecibidoPor());
                recibo.setAsignadoA(reciboDto.getAsignadoA());
                recibo.setEstatus(reciboDto.getEstatus());
                recibo.setFalla(reciboDto.getFalla());
                recibo.setFecha(reciboDto.getFecha());
                recibo.setObservacion(reciboDto.getObservacion());

                // VINCULACIÓN: Esto asigna el ID del usuario al recibo automáticamente al guardar
                nuevoUsuario.agregarRecibo(recibo);
            }
        }

        // 3. Guardar todo en cascada
        return usuarioRepository.save(nuevoUsuario);
    }
}
