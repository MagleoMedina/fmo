package com.backendfmo.services.contador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendfmo.repository.EncabezadoReciboRepository;
import com.backendfmo.repository.EntregasAlDAETRepository;
import com.backendfmo.repository.ReciboDePerifericosRepository;

@Service
public class ContadorServiceImpl {


    @Autowired
    private EncabezadoReciboRepository equipoRepository;

    @Autowired
    private ReciboDePerifericosRepository perifericoRepository;

    @Autowired
    private EntregasAlDAETRepository daetRepository;

    private static final String ESTATUS_LISTO = "Listo";

    public long contarEquiposAtendidos() {
        return equipoRepository.contarPorEstatus(ESTATUS_LISTO);
    }

    public long contarPerifericosAtendidos(){
        return perifericoRepository.contarPorEstatus(ESTATUS_LISTO);
    }

    public long contarDaetAtendidas(){
        return daetRepository.contarPorEstatus(ESTATUS_LISTO);
    } 
    
    public long getTotalPendientes() {
        long equipos = equipoRepository.contarPendientes(ESTATUS_LISTO);
        long perifericos = perifericoRepository.contarPendientes(ESTATUS_LISTO);
        long daet = daetRepository.contarPendientes(ESTATUS_LISTO);

        return equipos + perifericos + daet;
    }
}
