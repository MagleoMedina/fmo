package com.backendfmo.services.encabezado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backendfmo.dtos.EncabezadoDTO;
import com.backendfmo.dtos.EquipoDTO;
import com.backendfmo.dtos.RegistroTotalDTO;
import com.backendfmo.models.EncabezadoRecibo;
import com.backendfmo.models.ReciboDeEquipos;
import com.backendfmo.models.Usuario;
import com.backendfmo.repository.UsuarioRepository;

@Service
public class MegaRegistroService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario guardarTodoDeUnaVez(RegistroTotalDTO dto) {
        // 1. Crear Abuelo (Usuario)
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuario(dto.getUsuario());
        nuevoUsuario.setClave(dto.getClave()); // Recuerda encriptar esto en producción
        nuevoUsuario.setFicha(dto.getFicha());
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setExtension(dto.getExtension());
        nuevoUsuario.setGerencia(dto.getGerencia());

        // 2. Procesar Padres (Recibos)
        if (dto.getRecibos() != null) {
            for (EncabezadoDTO encDto : dto.getRecibos()) {
                EncabezadoRecibo encabezado = new EncabezadoRecibo();
                encabezado.setFmoEquipo(encDto.getFmoEquipo());
                encabezado.setSolicitudDAET(encDto.getSolicitudDAET());
                encabezado.setSolicitudST(encDto.getSolicitudST());
                encabezado.setEntregadoPor(encDto.getEntregadoPor());
                encabezado.setRecibidoPor(encDto.getRecibidoPor());
                encabezado.setAsignadoA(encDto.getAsignadoA());
                encabezado.setEstatus(encDto.getEstatus());
                encabezado.setFalla(encDto.getFalla());
                encabezado.setFecha(encDto.getFecha());
                encabezado.setObservacion(encDto.getObservacion());

                // 3. Procesar Nietos (Equipos) -> ¡Ojo! Esto está dentro del bucle de recibos
                if (encDto.getEquipos() != null) {
                    for (EquipoDTO equipoDto : encDto.getEquipos()) {
                        ReciboDeEquipos equipo = new ReciboDeEquipos();
                        equipo.setMarca(equipoDto.getMarca());
                        equipo.setRespaldo(equipoDto.getRespaldo());

                        // Vincular Nieto con Padre
                        encabezado.agregarEquipo(equipo);
                    }
                }

                // Vincular Padre con Abuelo
                nuevoUsuario.agregarRecibo(encabezado);
            }
        }

        // 4. Guardar SOLO al Abuelo. Hibernate guarda todo lo demás mágicamente.
        return usuarioRepository.save(nuevoUsuario);
    }
}
