package com.banvenez.scbdvservicios.dto;
import lombok.Data;

@Data
public class CreditoMorosoDTO {

    private  String account_number;
    private  String credit_number;
    private  String id_customer;
    private  String collector_name;
    private  String estado_cliente;
    private  String ultima_fecha;

}
