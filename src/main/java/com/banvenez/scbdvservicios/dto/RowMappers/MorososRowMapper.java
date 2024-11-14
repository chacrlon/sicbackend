package com.banvenez.scbdvservicios.dto.RowMappers;

import org.springframework.jdbc.core.RowMapper;
import com.banvenez.scbdvservicios.dto.MorosoDTO;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MorososRowMapper implements RowMapper<MorosoDTO> {

	@Override
    public MorosoDTO mapRow(ResultSet rs, int i) throws SQLException {
    	MorosoDTO act = new MorosoDTO();
        act.setAccount_number(rs.getString("account_number"));
        act.setId_customer(rs.getString("id_customer"));
        act.setCollector_name(rs.getString("collector_name"));
        act.setEstado_cliente(rs.getString("estado_cliente"));
        act.setUltima_fecha(rs.getString("ultima_fecha"));
        return act;
    }
}