package com.banvenez.scbdvservicios.dto;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ConsultaMorosoDTO {

	private Long id;  
    private LocalDateTime datecreated;  
    private String origin;  
    private String operationMessage;  
    private String operationStatus;  
    private String collectorName;  
    private String dateReceived;  

}
