package com.banvenez.scbdvservicios.dto.RowMappers;  

import org.springframework.jdbc.core.RowMapper;  
import com.banvenez.scbdvservicios.dto.ConsultaMorosoDTO;  
import java.sql.ResultSet;  
import java.sql.SQLException;  

public class ConsultaMorososRowMapper implements RowMapper<ConsultaMorosoDTO> {  

	@Override  
    public ConsultaMorosoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {  
        ConsultaMorosoDTO consultaMoroso = new ConsultaMorosoDTO();  
        consultaMoroso.setId(rs.getObject("ID") != null ? rs.getLong("ID") : null);  
        consultaMoroso.setDatecreated(rs.getTimestamp("DATECREATED") != null ? rs.getTimestamp("DATECREATED").toLocalDateTime() : null);  
        consultaMoroso.setOrigin(rs.getString("ORIGIN") != null ? rs.getString("ORIGIN") : "");  
        consultaMoroso.setOperationMessage(rs.getString("OPERATION_MESSAGE") != null ? rs.getString("OPERATION_MESSAGE") : "");  
        consultaMoroso.setOperationStatus(rs.getString("OPERATION_STATUS") != null ? rs.getString("OPERATION_STATUS") : "");  
        consultaMoroso.setCollectorName(rs.getString("COLLECTOR_NAME") != null ? rs.getString("COLLECTOR_NAME") : "");  
        consultaMoroso.setDateReceived(rs.getString("DATE_RECEIVED") != null ? rs.getString("DATE_RECEIVED") : "");  
        return consultaMoroso;  
    }  
    
}