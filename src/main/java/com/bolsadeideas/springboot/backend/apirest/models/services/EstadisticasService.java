package com.bolsadeideas.springboot.backend.apirest.models.services;  

import com.bolsadeideas.springboot.backend.apirest.models.dao.SicDao;  
import com.bolsadeideas.springboot.backend.apirest.models.entity.ResponseModel;  
import lombok.extern.slf4j.Slf4j;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

import java.util.Date;  

@Slf4j  
@Service  
public class EstadisticasService {  
    @Autowired  
    SicDao sicDao;  

    public ResponseModel consultarEstadisticas(Date fecha) {  // Aceptar fecha como parámetro  
        log.info("Begin consultarEstadisticas en el Service");  
        return sicDao.consultarEstadistica1(fecha);  // Pasar la fecha al DAO  
    }  
    
    public ResponseModel consultarEstadisticasPIC(Date fecha) {  
        log.info("Begin consultarEstadisticas en el Service");  
        return sicDao.consultarEstadisticasPIC(fecha);  
    } 
    
    public ResponseModel consultarEstadisticasPCP(Date fecha) {  
        log.info("Begin consultarEstadisticas en el Service");  
        return sicDao.consultarEstadisticasPCP(fecha);  
    } 
    
}