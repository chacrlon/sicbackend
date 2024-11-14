package com.banvenez.scbdvservicios.dto.RowMappers;

import org.springframework.jdbc.core.RowMapper;

import com.banvenez.scbdvservicios.dto.BitacoraDTO;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BitacoraRowMapper implements RowMapper<BitacoraDTO> {
    @Override
    public BitacoraDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	BitacoraDTO bitacora = new BitacoraDTO();
    	bitacora.setIdAuditoria(rs.getLong("ID_AUDITORIA"));
    	bitacora.setDescripcion(rs.getString("DESCRIPCION"));
    	bitacora.setTipoAccion(rs.getString("TIPO_ACCION"));
    	bitacora.setUsuarioAccion(rs.getString("USUARIO_ACCION"));
    	bitacora.setFechaRegistro(rs.getTime("FECHA_REGISTRO"));
        return bitacora;
    }
    
}