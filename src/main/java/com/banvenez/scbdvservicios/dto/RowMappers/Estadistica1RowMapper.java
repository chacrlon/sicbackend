package com.banvenez.scbdvservicios.dto.RowMappers;  

import org.springframework.jdbc.core.RowMapper;  
import com.banvenez.scbdvservicios.dto.Estadistica1DTO;  
import java.sql.ResultSet;  
import java.sql.SQLException;  

public class Estadistica1RowMapper implements RowMapper<Estadistica1DTO> {  

    @Override  
    public Estadistica1DTO mapRow(ResultSet rs, int rowNum) throws SQLException {  
        Estadistica1DTO estadistica = new Estadistica1DTO();  
        estadistica.setRecordDate(rs.getDate("RECORD_DATE")); // Para REC_DATE  
        estadistica.setCollectorName(rs.getString("COLLECTOR_NAME")); // Para COLLECTOR_NAME  
        estadistica.setCountCredit(rs.getInt("COUNT_CREDIT")); // Para COUNT_CREDIT  
        estadistica.setCountAccount(rs.getInt("COUNT_ACCOUNT")); // Para COUNT_ACCOUNT  
        estadistica.setCuentasNotificadas(rs.getInt("CUENTAS_NOTIFICADAS")); // Para Cuentas_Notificadas  
        estadistica.setConCobro(rs.getInt("CON_COBRO")); // Para Cuentas Cobradas 
        estadistica.setCuentasNoNotificadas(rs.getInt("CUENTAS_NO_NOTIFICADAS")); // Para Cuentas_no_Notificadas  
        return estadistica;  
    }  
}