package com.bolsadeideas.springboot.backend.apirest.controllers;  

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.*;  
import com.bolsadeideas.springboot.backend.apirest.models.entity.ResponseModel;  
import com.bolsadeideas.springboot.backend.apirest.models.services.EstadisticasService;  
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Date;

@CrossOrigin(origins = "*")
@RestController  
@RequestMapping("/api")  
public class EstadisticasRestController {  

    @Autowired  
    private EstadisticasService estadisticasService;  
    
    @GetMapping(value = "/estadistica") // Endpoint ahora sin fecha en el URL  
    public ResponseEntity<ResponseModel> consultarEstadisticaFecha(@RequestParam String fecha) {  
        try {  
            // Suponiendo que se recibe en el formato "dd-MM-yyyy"  
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");  
            Date parsedDate = sdf.parse(fecha); // Convertir la fecha  
            ResponseModel response = estadisticasService.consultarEstadisticas(parsedDate);  
            return ResponseEntity.ok(response); // Devuelve respuesta  
        } catch (ParseException e) {  
            // Manejar el error de conversión de fecha  
            ResponseModel errorResponse = ResponseModel.builder()  
                .code(400)  
                .message("Formato de fecha inválido. Debe ser dd-MM-yyyy.")  
                .data(null)  
                .status(400)  
                .build();  
            return ResponseEntity.badRequest().body(errorResponse);  
        }  
    } 
    
    
    @GetMapping(value = "/estadistica2pic")  
    public ResponseEntity<ResponseModel> consultarEstadisticaFechaPIC(@RequestParam String fecha) {  
        try {  
            // Suponiendo que se recibe en el formato "dd-MM-yyyy"  
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");  
            Date parsedDate = sdf.parse(fecha); // Convertir la fecha  
            ResponseModel response = estadisticasService.consultarEstadisticasPIC(parsedDate);  
            return ResponseEntity.ok(response);  
        } catch (ParseException e) {  
            ResponseModel errorResponse = ResponseModel.builder()  
                .code(400)  
                .message("Formato de fecha inválido. Debe ser dd-MM-yyyy.")  
                .data(null)  
                .status(400)  
                .build();  
            return ResponseEntity.badRequest().body(errorResponse);  
        }  
    }
    
    @GetMapping(value = "/estadistica2pcp")  
    public ResponseEntity<ResponseModel> consultarEstadisticaFechaPCP(@RequestParam String fecha) {  
        try {  
            // Suponiendo que se recibe en el formato "dd-MM-yyyy"  
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");  
            Date parsedDate = sdf.parse(fecha); // Convertir la fecha  
            ResponseModel response = estadisticasService.consultarEstadisticasPCP(parsedDate);  
            return ResponseEntity.ok(response);  
        } catch (ParseException e) {  
            ResponseModel errorResponse = ResponseModel.builder()  
                .code(400)  
                .message("Formato de fecha inválido. Debe ser dd-MM-yyyy.")  
                .data(null)  
                .status(400)  
                .build();  
            return ResponseEntity.badRequest().body(errorResponse);  
        }  
    }
    
}