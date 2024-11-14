package com.banvenez.scbdvservicios.dto.RowMappers;  

import org.springframework.jdbc.core.RowMapper;  
import com.banvenez.scbdvservicios.dto.Estadistica2DTO;
import java.sql.ResultSet;  
import java.sql.SQLException;  

public class Estadistica2RowMapper implements RowMapper<Estadistica2DTO> {  

    @Override  
    public Estadistica2DTO mapRow(ResultSet rs, int rowNum) throws SQLException {  
        Estadistica2DTO estadistica = new Estadistica2DTO();  
        estadistica.setOrigin(rs.getString("ORIGIN"));  
        estadistica.setCollectorId(rs.getInt("COLLECTOR_ID"));  
        estadistica.setCollectorName(rs.getString("COLLECTOR_NAME"));  
        estadistica.setTotalOperations(rs.getInt("TOTAL_OPERATIONS"));  
        estadistica.setOperationMessage(rs.getString("OPERATION_MESSAGE"));  
        estadistica.setTotalTransactions(rs.getInt("TRANSACTIONS_TOTAL"));  
        return estadistica;  
    }  
}  