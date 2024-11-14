package com.bolsadeideas.springboot.backend.apirest.models.services;

import com.banvenez.scbdvservicios.dto.CobradorDTO;
import com.bolsadeideas.springboot.backend.apirest.models.dao.SicDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CobradorService {
	@Autowired
	SicDao sicDao;	
	
	public ResponseModel insertarCobrador(CobradorDTO datos) {
		ResponseModel responseModel = new ResponseModel();
		log.info("Begin guardarlote en el Service=>{}", datos.toString());
		if ((datos.getCollector_name() != "" && datos.getCollector_name() != null)
				&& (datos.getStart_time() != null)
				&& (datos.getFinal_time() != null)
				&& (datos.getStatus() != "" && datos.getStatus() != null)) {
			responseModel = sicDao.insertarCobrador(datos);
		} else {
			log.info(" guardarlote Datos no validos o en null");
			responseModel.setCode(9999);
			responseModel.setStatus(204);
			responseModel.setMessage("Datos no validos o en null");
		}
		return responseModel;
	}
	
	public ResponseModel mostrarCobradores() {
	    ResponseModel responseModel = new ResponseModel();
	    responseModel = sicDao.mostrarCobradores();
	    return responseModel;
	}
	
	public ResponseModel actualizarCobrador(CobradorDTO datos) {
        ResponseModel responseModel = new ResponseModel();
        log.info("Actualizando cobrador en el Service => {}", datos.toString());
        if ((datos.getCollector_name() != null && !datos.getCollector_name().isEmpty())
                && (datos.getStart_time() != null)
                && (datos.getFinal_time() != null)
                && (datos.getStatus() != null && !datos.getStatus().isEmpty())) {
            responseModel = sicDao.actualizarCobrador(datos);
        } else {
            log.info("Datos no válidos o nulos para la actualización del cobrador");
            responseModel.setCode(9999);
            responseModel.setStatus(204);
            responseModel.setMessage("Datos no válidos o nulos para la actualización del cobrador");
        }
        return responseModel;
    }
	
	public ResponseModel eliminarCobrador(CobradorDTO datos) {
		ResponseModel responseModel = new ResponseModel();
		log.info("Begin aprobacion en el Service=>{}", datos.toString());
		if ((datos.getCollector_id() != null)) {
			responseModel = sicDao.eliminarCobrador(datos);
		} else {
			log.info(" aprobacion Datos no validos o en null");
			responseModel.setCode(9999);
			responseModel.setStatus(204);
			responseModel.setMessage("Datos no validos o en null");
		}
		return responseModel;
	}
	
	
}