package com.banvenez.scbdvservicios.dto;

import java.util.Date;
import lombok.Data;

@Data
public class BitacoraDTO {

    private  Long idAuditoria;
    private  String descripcion;
    private  String tipoAccion;
    private  String usuarioAccion;
    private  Date fechaRegistro;

}
