package com.backendfmo.services.encabezado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backendfmo.dtos.EncabezadoDTO;
import com.backendfmo.dtos.EquipoDTO;
import com.backendfmo.dtos.RegistroTotalDTO;
import com.backendfmo.models.CarpetaDeRed;
import com.backendfmo.models.CarpetaRedRecibo;
import com.backendfmo.models.EncabezadoRecibo;
import com.backendfmo.models.ReciboDeEquipos;
import com.backendfmo.models.Usuario;
import com.backendfmo.repository.UsuarioRepository;

@Service
public class a {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario guardarTodo(RegistroTotalDTO dto) {
        // ... (Creación de Usuario y Encabezado igual que antes) ...
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuario(dto.getUsuario());
        nuevoUsuario.setClave(dto.getClave()); // Recuerda encriptar esto en producción
        nuevoUsuario.setFicha(dto.getFicha());
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setExtension(dto.getExtension());
        nuevoUsuario.setGerencia(dto.getGerencia());
        // ...

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

                if (encDto.getEquipos() != null) {
                    for (EquipoDTO equipoDto : encDto.getEquipos()) {
                        ReciboDeEquipos equipo = new ReciboDeEquipos();
                        equipo.setMarca(equipoDto.getMarca());
                        equipo.setRespaldo(equipoDto.getRespaldo());

                        // --- LÓGICA DE CARPETAS MANUALES ---
                        if (equipoDto.getNombresCarpetas() != null) {
                            for (String nombreCarpeta : equipoDto.getNombresCarpetas()) {
                                
                                // 1. Creamos la Carpeta Nueva (Objeto Hoja)
                                CarpetaDeRed nuevaCarpeta = new CarpetaDeRed();
                                nuevaCarpeta.setNombreCarpeta(nombreCarpeta);

                                // 2. Creamos la Relación (Objeto Intermedio)
                                CarpetaRedRecibo link = new CarpetaRedRecibo();
                                link.setCarpetaRelacion(nuevaCarpeta); // Metemos la carpeta nueva en el link

                                // 3. Usamos el helper del Equipo para vincular el link
                                equipo.agregarRelacionCarpeta(link);
                            }
                        }
                        // ------------------------------------

                        encabezado.agregarEquipo(equipo);
                    }
                }
                nuevoUsuario.agregarRecibo(encabezado);
            }
        }
        // Guardamos al abuelo y Hibernate insertará todo en cascada
        return usuarioRepository.save(nuevoUsuario);
    }
}