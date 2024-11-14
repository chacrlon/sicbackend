package com.bolsadeideas.springboot.backend.apirest.models.services;

import com.bolsadeideas.springboot.backend.apirest.models.dao.SicDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory; 

@Slf4j
@Service
public class DeudaService {
	private static final Logger log = LoggerFactory.getLogger(DeudaService.class);  
	@Autowired
	SicDao sicDao;

	
	public ResponseModel consultarDeuda() {
		log.info("Begin consuitarlistaHora en el Service");

		return sicDao.consultarMorosos();
	}
	
	 public ResponseModel consultarMorosos(String idClienteONumeroCuenta) {  
	        log.info("Begin consultarMorosos en el Service");  
	        return sicDao.consultarMorososPorIdClienteONumeroCuenta(idClienteONumeroCuenta);  
	    }
	 
	 public ResponseModel consultarMorosos2(String idClienteONumeroCuenta) {  
	        log.info("Begin consultarMorosos en el Service");  
	        return sicDao.consultarMorososPorIdClienteONumeroCuenta2(idClienteONumeroCuenta);  
	    }  
	
	
	public ResponseModel consultarCreditosMorosos0() {
		log.info("Begin consuitarlistaHora en el Service");

		return sicDao.consultarCreditosMorosos();
	}
	
	public ResponseModel consultarIdCreditosMorosos0(String CreditoONumeroCuenta) {
	    log.info("Begin consultarMorosos3 en el Service");

	    return sicDao.consultarCreditosMorososPorIdClienteONumeroCuenta(CreditoONumeroCuenta);
	}
	
	public ResponseModel consultarIdCreditosMorosos(String CreditoONumeroCuenta) {
	    log.info("Begin consultarMorosos3 en el Service");

	    return sicDao.consultarCreditosMorososPorIdClienteONumeroCuenta(CreditoONumeroCuenta);
	}

}