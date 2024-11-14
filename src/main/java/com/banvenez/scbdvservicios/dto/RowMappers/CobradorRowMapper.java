package com.banvenez.scbdvservicios.dto.RowMappers;

import org.springframework.jdbc.core.RowMapper;

import com.banvenez.scbdvservicios.dto.CobradorDTO;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CobradorRowMapper implements RowMapper<CobradorDTO> {
    @Override
    public CobradorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	CobradorDTO cobrador = new CobradorDTO();
        cobrador.setCollector_id(rs.getLong("collector_id"));
        cobrador.setCollector_name(rs.getString("collector_name"));
        cobrador.setStart_time(rs.getTime("start_time"));
        cobrador.setFinal_time(rs.getTime("final_time"));
        cobrador.setStatus(rs.getString("status"));
        cobrador.setPriority(rs.getInt("priority"));
        return cobrador;
    }
    
}