package com.bolsadeideas.springboot.backend.apirest.controllers;  

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.CrossOrigin;  
import org.springframework.web.bind.annotation.DeleteMapping;  
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.PutMapping;  
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;  
import com.banvenez.scbdvservicios.dto.CobradorDTO;  
import com.bolsadeideas.springboot.backend.apirest.models.entity.ResponseModel;  
import com.bolsadeideas.springboot.backend.apirest.models.services.CobradorService;

@CrossOrigin(origins = "*")  // Permitir solicitudes desde Angular
@RestController  
@RequestMapping("/api")  
public class CobradorRestController {  

    @Autowired  
    private CobradorService cobradorService;  // Renombrado por claridad  

    @PostMapping("/processUser")  
    public ResponseEntity<String> processUser(@RequestBody Map<String, String> requestBody) {  
        String codUsuario = requestBody.get("codUsuario");  
        System.out.println("EL USUARIO ES : " + codUsuario);  
        return ResponseEntity.ok("Usuario procesado: " + codUsuario);  
    } 

    @PostMapping(value = "/insertarcobrador")  
    public ResponseModel insertarCobrador(@RequestBody CobradorDTO datos) {  
    	System.out.println("Datos recibidos en el servidor: " + datos); // Agregar esta línea 
        return cobradorService.insertarCobrador(datos);  
    }  

    @GetMapping(value = "/mostrarcobradores")  
    public ResponseModel mostrarCobradores() {  
        return cobradorService.mostrarCobradores();  
    }  

    @PutMapping(value = "/actualizarcobrador/{collector_id}")  
    public ResponseModel actualizarCobrador(@RequestBody CobradorDTO datos, @PathVariable Long collector_id) {	        
        datos.setCollector_id(collector_id);  
        return cobradorService.actualizarCobrador(datos);  
    }  

    @DeleteMapping(value = "/eliminarcobrador/{collector_id}")  
    public ResponseModel eliminarCobrador(@RequestBody CobradorDTO datos, @PathVariable Long collector_id) {  
        datos.setCollector_id(collector_id);  
        return cobradorService.eliminarCobrador(datos);  
    }  
}