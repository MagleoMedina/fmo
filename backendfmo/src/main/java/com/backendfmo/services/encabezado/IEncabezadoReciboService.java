package com.backendfmo.services.encabezado;

import com.backendfmo.dtos.RegistroTotalDTO;
import com.backendfmo.models.Usuario;

public interface IEncabezadoReciboService {

    Usuario guardarUsuarioYRecibos(RegistroTotalDTO dto);
}
