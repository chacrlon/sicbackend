package com.bolsadeideas.springboot.backend.apirest.controllers;  

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.CrossOrigin;  
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;
import com.banvenez.scbdvservicios.dto.BitacoraDTO;
import com.bolsadeideas.springboot.backend.apirest.models.entity.ResponseModel;
import com.bolsadeideas.springboot.backend.apirest.models.services.BitacoraService;

@CrossOrigin(origins = "*")
@RestController  
@RequestMapping("/api")  
public class BitacoraController {  

    @Autowired  
    private BitacoraService bitacoraService;  

    @PostMapping("/insertarbitacora")  
    public ResponseEntity<ResponseModel> insertarBitacora(@RequestBody BitacoraDTO bitacoraDTO) {  
        ResponseModel response = bitacoraService.insertarBitacora(bitacoraDTO);  
        return ResponseEntity.status(response.getStatus()).body(response);  
    }  
}