package com.bolsadeideas.springboot.backend.apirest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bolsadeideas.springboot.backend.apirest.models.entity.ResponseModel;
import com.bolsadeideas.springboot.backend.apirest.models.services.DeudaService;

//@CrossOrigin(origins = "http://localhost:4200") 
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DeudaRestController {
	
	@Autowired
	DeudaService sicService;
	
	@GetMapping(value = "/deuda/morosos")
	public ResponseModel consultarDeuda() {
		return sicService.consultarDeuda();
	}
	
	@GetMapping(value = "/deuda/consultar_moroso/{idClienteONumeroCuenta}")  
    public ResponseModel consultarMorosos(@PathVariable String idClienteONumeroCuenta) {  
        return sicService.consultarMorosos(idClienteONumeroCuenta);  
    }
	
	@GetMapping(value = "/deuda/consultar_moroso2/{idClienteONumeroCuenta}")  
    public ResponseModel consultarMorosos2(@PathVariable String idClienteONumeroCuenta) {  
        return sicService.consultarMorosos2(idClienteONumeroCuenta);  
    }
	
	
	@GetMapping(value = "/credito/morosos")
	public ResponseModel consultarCreditosMorosos0() {
		return sicService.consultarCreditosMorosos0();
	}
	
	@GetMapping(value = "/credito/consultar_moroso/{CreditoONumeroCuenta}")
    public ResponseModel consultarIdCreditosMorosos0(@PathVariable String CreditoONumeroCuenta) {
        return sicService.consultarIdCreditosMorosos0(CreditoONumeroCuenta);
    }
	
}
