package com.banvenez.scbdvservicios.dto.RowMappers;  

import org.springframework.jdbc.core.RowMapper;  
import com.banvenez.scbdvservicios.dto.CreditoMorosoDTO;  
import java.sql.ResultSet;  
import java.sql.SQLException;  

public class CreditosMorososRowMapper implements RowMapper<CreditoMorosoDTO> {  

    @Override  
    public CreditoMorosoDTO mapRow(ResultSet rs, int i) throws SQLException {  
        CreditoMorosoDTO act = new CreditoMorosoDTO();  
        act.setAccount_number(rs.getString("account_number")); 
        act.setCredit_number(rs.getString("credit_number"));   
        act.setId_customer(rs.getString("id_customer"));      
        act.setCollector_name(rs.getString("collector_name"));  
        act.setEstado_cliente(rs.getString("estado_cliente"));  
        act.setUltima_fecha(rs.getString("ultima_fecha"));     
        return act;  
    }  
}