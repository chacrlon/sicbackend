package com.bolsadeideas.springboot.backend.apirest.models.services;

import com.banvenez.scbdvservicios.dto.BitacoraDTO;
import com.bolsadeideas.springboot.backend.apirest.models.dao.SicDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BitacoraService {
	   @Autowired  
	    SicDao bitacoraDao;  

	     public ResponseModel insertarBitacora(BitacoraDTO datos) {  
	        ResponseModel responseModel = new ResponseModel();  
	        log.info("Begin insertando en la bitácora=>{}", datos.toString());  

	        if (datos.getDescripcion() != null && !datos.getDescripcion().isEmpty() &&  
	            datos.getTipoAccion() != null && !datos.getTipoAccion().isEmpty() &&  
	            datos.getUsuarioAccion() != null && !datos.getUsuarioAccion().isEmpty()) {  
	            
	            responseModel = bitacoraDao.insertarBitacora(datos);  
	        } else {  
	            log.info("Datos no válidos o en null");  
	            responseModel.setCode(9999);  
	            responseModel.setStatus(204);  
	            responseModel.setMessage("Datos no válidos o en null");  
	        }  
	        return responseModel;  
	    }  
	}  