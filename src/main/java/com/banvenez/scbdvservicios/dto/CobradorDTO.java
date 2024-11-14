package com.banvenez.scbdvservicios.dto;
import java.sql.Time;

import lombok.Data;

@Data
public class CobradorDTO {

    private  Long collector_id;
    private  String collector_name;
    private  Time start_time;
    private  Time final_time;
    private  String status;
    private  int priority;

}
