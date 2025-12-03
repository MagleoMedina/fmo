package com.backendfmo.services.encabezado;

import com.backendfmo.dtos.request.reciboequipos.RegistroTotalDTO;
import com.backendfmo.models.Usuario;

public interface IEncabezadoReciboService {

    Usuario guardarUsuarioYRecibos(RegistroTotalDTO dto);
}
