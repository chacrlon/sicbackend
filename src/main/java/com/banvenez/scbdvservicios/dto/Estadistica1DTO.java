package com.banvenez.scbdvservicios.dto;  

import lombok.Data;  
import java.util.Date;  

@Data  
public class Estadistica1DTO {  

    private Date recordDate; // Para REC_DATE  
    private String collectorName; // Para COLLECTOR_NAME  
    private Integer countCredit; // Para COUNT_CREDIT  
    private Integer countAccount; // Para COUNT_ACCOUNT  
    private Integer cuentasNotificadas; // Para Cuentas_Notificadas
    private Integer conCobro; // Para Cuentas Cobradas
    private Integer cuentasNoNotificadas; // Para Cuentas_no_Notificadas  

}