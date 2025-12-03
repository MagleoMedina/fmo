package com.backendfmo.services.perifericos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backendfmo.dtos.request.reciboperifericos.PerifericoItemDTO;
import com.backendfmo.dtos.request.reciboperifericos.RegistroPerifericosDTO;
import com.backendfmo.models.ComponenteInterno;
import com.backendfmo.models.EncabezadoRecibo;
import com.backendfmo.models.ReciboDePerifericos;
import com.backendfmo.models.Usuario;
import com.backendfmo.repository.ComponenteInternoRepository;
import com.backendfmo.repository.UsuarioRepository;

@Service
public class PerifericosService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComponenteInternoRepository componenteRepository;

    @Transactional
    public Usuario registrarPerifericos(RegistroPerifericosDTO dto) {
        // 1. Crear Usuario (Abuelo)
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuario(dto.getUsuario());
        nuevoUsuario.setClave(dto.getClave());
        nuevoUsuario.setFicha(dto.getFicha());
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setGerencia(dto.getGerencia());

        // 2. Crear Encabezado (Padre)
        EncabezadoRecibo encabezado = new EncabezadoRecibo();
        encabezado.setFmoEquipo(dto.getFmoEquipo());
        encabezado.setSolicitudST(dto.getSolicitudST());
        encabezado.setEstatus(dto.getEstatus());
        encabezado.setFecha(dto.getFecha());
        encabezado.setObservacion(dto.getObservacion());

        // 3. Procesar Periféricos (Hijos)
        if (dto.getItemsPerifericos() != null) {
            for (PerifericoItemDTO itemDto : dto.getItemsPerifericos()) {
                
                // A. BUSCAR componente existente en BD (Validación)
                ComponenteInterno componenteBD = componenteRepository.findById(itemDto.getIdComponente())
                    .orElseThrow(() -> new RuntimeException("Componente no encontrado con ID: " + itemDto.getIdComponente()));

                // B. CREAR la entidad del periférico
                ReciboDePerifericos periferico = new ReciboDePerifericos();
                periferico.setFmoSerial(itemDto.getFmoSerial());
                periferico.setComponenteRef(componenteBD); // Asignamos la referencia encontrada

                // C. VINCULAR al Encabezado
                encabezado.agregarPeriferico(periferico);
            }
        }

        // 4. VINCULAR Encabezado al Usuario
        nuevoUsuario.agregarRecibo(encabezado);

        // 5. GUARDAR (Cascada: Usuario -> Encabezado -> Perifericos)
        return usuarioRepository.save(nuevoUsuario);
    }
}
