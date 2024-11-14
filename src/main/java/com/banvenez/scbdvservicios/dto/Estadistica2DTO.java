package com.banvenez.scbdvservicios.dto;  

import lombok.Data;  
import java.util.Date;  

@Data  
public class Estadistica2DTO {  
    private String origin;                 // Fecha de origen  
    private Integer collectorId;           // ID del cobrador  
    private String collectorName;           // Nombre del cobrador  
    private Integer totalOperations;        // Total de operaciones  
    private String operationMessage;        // Mensajes de operaciones  
    private Integer totalTransactions;      // Total de transacciones  
} 