package com.backendfmo.services.perifericos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backendfmo.dtos.request.reciboperifericos.ComponenteItemDTO;
import com.backendfmo.dtos.request.reciboperifericos.RegistroPerifericosDTO;
import com.backendfmo.dtos.response.reciboperifericos.ComponenteItemResponseDTO;
import com.backendfmo.dtos.response.reciboperifericos.ReciboPerifericosDTO;
import com.backendfmo.models.perifericos.Periferico;
import com.backendfmo.models.perifericos.ReciboDePerifericos;
import com.backendfmo.models.reciboequipos.ComponenteInterno;
import com.backendfmo.models.reciboequipos.EncabezadoRecibo;
import com.backendfmo.models.reciboequipos.ReciboPeriferico;
import com.backendfmo.models.reciboequipos.Usuario;
import com.backendfmo.repository.ComponenteInternoRepository;
import com.backendfmo.repository.ReciboDePerifericosRepository;
import com.backendfmo.repository.UsuarioRepository;

@Service
public class PerifericosService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComponenteInternoRepository componenteRepository;

    @Autowired
    private ReciboDePerifericosRepository perifericoRepository;

    @Transactional
    public Usuario registrarPerifericos(RegistroPerifericosDTO dto) {
        // 1. Crear Usuario (Abuelo)
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuario(dto.getUsuario());
        nuevoUsuario.setGerencia(dto.getGerencia());
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setFicha(dto.getFicha());

        // 2. Crear Encabezado (Padre)
        EncabezadoRecibo encabezado = new EncabezadoRecibo();
        encabezado.setFmoEquipo(dto.getFmoEquipo());
        encabezado.setSolicitudST(dto.getSolicitudST());


        encabezado.setSolicitudDAET(dto.getSolicitudDAET());
        encabezado.setEntregadoPor(dto.getEntregadoPor());
        encabezado.setRecibidoPor(dto.getRecibidoPor());
        encabezado.setAsignadoA(dto.getAsignadoA());
        
        encabezado.setEstatus(dto.getEstatus());
        encabezado.setFecha(dto.getFecha());
        encabezado.setObservacion(dto.getObservacion());
        encabezado.setFalla(dto.getFalla());

        // 3. Procesar Periféricos (Hijos)
        if (dto.getComponentePerifericos() != null) {
            for (ComponenteItemDTO itemDto : dto.getComponentePerifericos()) {
                
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

@Transactional(readOnly = true)
    public List<ReciboPerifericosDTO> buscarPorSerial(String fmoSerial) {
        
        // 1. Buscar TODOS los registros que coincidan con el serial
        List<ReciboDePerifericos> resultados = perifericoRepository.findByFmoSerial(fmoSerial);

        if (resultados.isEmpty()) {
            throw new RuntimeException("No se encontró ningún componente con el serial: " + fmoSerial);
        }

        // 2. Convertir cada resultado encontrado en un DTO
        return resultados.stream()
            .map(this::convertirADTO) // Llamamos a un método auxiliar para limpiar el código
            .collect(Collectors.toList());
    }

// Método auxiliar corregido
    private ReciboPerifericosDTO convertirADTO(ReciboDePerifericos perifericoHijo) {
        ReciboPerifericosDTO response = new ReciboPerifericosDTO();
        
        // Obtener Padre (Encabezado)
        EncabezadoRecibo encabezado = perifericoHijo.getEncabezadoRelacion();
        if (encabezado == null) return response; 

        // Obtener Abuelo (Usuario)
        Usuario usuario = encabezado.getUsuarioRelacion(); 

        // Mapear Datos del Usuario
        if (usuario != null) {
            // Asumiendo que el DTO tiene estos campos
            // response.setUsuario(usuario.getUsuario()); 
            response.setNombre(usuario.getNombre());
            response.setFicha(usuario.getFicha()); 
            response.setUsuario(usuario.getUsuario());
        }

        // Mapear Datos del Encabezado
        response.setSolicitudST(encabezado.getSolicitudST());
        response.setSolicitudDAET(encabezado.getSolicitudDAET());
        response.setEstatus(encabezado.getEstatus());
        response.setFecha(encabezado.getFecha()); 
        response.setObservacion(encabezado.getObservacion());
        response.setAsignadoA(encabezado.getAsignadoA());
        response.setRecibidoPor(encabezado.getRecibidoPor());
        response.setEntregadoPor(encabezado.getEntregadoPor());
        response.setFalla(encabezado.getFalla());
        
        // CORRECCIÓN 1: Usar getPerifericos() en lugar de getReciboDePerifericos()
        // Esto coincide con: private List<ReciboDePerifericos> perifericos; en EncabezadoRecibo.java
        List<ComponenteItemResponseDTO> listaItems = new ArrayList<>();
        
        if (encabezado.getPerifericos() != null) { 
            for (ReciboDePerifericos item : encabezado.getPerifericos()) {
                ComponenteItemResponseDTO itemDto = new ComponenteItemResponseDTO();
                itemDto.setFmoSerial(item.getFmoSerial());
                
                if (item.getComponenteRef() != null) {
                    itemDto.setIdComponente(item.getComponenteRef().getId());
                }
                listaItems.add(itemDto);
            }
        }
        
        // CORRECCIÓN 2: Ahora funciona porque renombramos el campo en el DTO
        response.setComponentePerifericos(listaItems);

        return response;
    }


}
