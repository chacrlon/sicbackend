package com.bolsadeideas.springboot.backend.apirest.models.dao;

import com.banvenez.scbdvservicios.dto.*;
import com.banvenez.scbdvservicios.dto.RowMappers.*;
import com.bolsadeideas.springboot.backend.apirest.models.entity.ResponseModel;

import lombok.extern.slf4j.Slf4j;

import oracle.jdbc.internal.OracleTypes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.util.zip.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.Key;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

@Slf4j
@Service
@Repository
@SuppressWarnings(value = { "unchecked", "rawtypes", "unused", "resource" })

public class SicDao {
	
	@Value("${maxdata}")
	private  String SecretKeyData;

	private ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	private JdbcTemplate jdbcTemplate;
	
	
	
	public SicDao() {
		super();
		this.jdbcTemplate = (JdbcTemplate) context.getBean("jdbctemplateSic");

	}

	private static ZipOutputStream zos;

	
	public ResponseModel consultarMorosos() {
	    ResponseModel response = new ResponseModel();

	    try {
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
	        jdbcCall.withProcedureName("PKG_SIC_DEBT_ACCOUNTS2.CONSULTA_MOROSOS");
	        jdbcCall.withoutProcedureColumnMetaDataAccess();
	        jdbcCall.setFunction(false);
	        jdbcCall.declareParameters(
	        		new SqlOutParameter("P_OUT_DATA", OracleTypes.CURSOR),
	                new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),
	                new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)
	        );
	        
	        jdbcCall.returningResultSet("P_OUT_DATA", new MorososRowMapper());

	        MapSqlParameterSource inputMap = new MapSqlParameterSource();

	        Map<String, Object> resultMap = jdbcCall.execute(inputMap);
	        ArrayList<MorosoDTO> morososList = (ArrayList<MorosoDTO>) resultMap.get("P_OUT_DATA");
	        String codRetorno = (String) resultMap.get("COD_RET");
	        String descRetorno = (String) resultMap.get("DE_RET");

	        if (codRetorno.equals("1000")) {
	            response.setStatus(200);
	            response.setCode(Integer.parseInt(codRetorno));
	            response.setMessage(descRetorno);
	            response.setData(morososList);
	        } else {
	            response.setStatus(500);
	            response.setCode(9999);
	            response.setMessage("Error en la consulta de morosos");
	        }

	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        response.setStatus(500);
	        response.setCode(9999);
	        response.setMessage("Error al consultar morosos");
	    }

	    return response;
	}
	
	public ResponseModel consultarMorososPorIdClienteONumeroCuenta2(String idClienteONumeroCuenta) {  
        ResponseModel response = new ResponseModel();  

        try {  
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);  
            jdbcCall.withProcedureName("PKG_SIC_DEBT_ACCOUNTS2.FIND_BY_ID_CUSTOMER_OR_ACCOUNT2");  
            jdbcCall.withoutProcedureColumnMetaDataAccess();  
            jdbcCall.setFunction(false);  
            jdbcCall.declareParameters(  
                new SqlOutParameter("P_OUT_DATA", OracleTypes.CURSOR),  
                new SqlParameter("P_IN_CONSULTA", OracleTypes.VARCHAR),  
                new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),  
                new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)  
            );  

            jdbcCall.returningResultSet("P_OUT_DATA", new ConsultaMorososRowMapper());  

            MapSqlParameterSource inputMap = new MapSqlParameterSource();  
            inputMap.addValue("P_IN_CONSULTA", idClienteONumeroCuenta);  

            Map<String, Object> resultMap = jdbcCall.execute(inputMap);  

            List<ConsultaMorosoDTO> morososList = (List<ConsultaMorosoDTO>) resultMap.get("P_OUT_DATA");  
            String codRetorno = (String) resultMap.get("COD_RET");  
            String descRetorno = (String) resultMap.get("DE_RET");  

            if ("1000".equals(codRetorno)) {  
                response.setStatus(200);  
                response.setCode(Integer.parseInt(codRetorno));  
                response.setMessage(descRetorno);  
                response.setData(morososList);  
            } else {  
                response.setStatus(500);  
                response.setCode(9999);  
                response.setMessage("Error en la consulta de créditos morosos por ID de Cliente o Número de Cuenta");  
            }  

        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            response.setStatus(500);  
            response.setCode(9999);  
            response.setMessage("Error al consultar morosos por ID de Cliente o Número de Cuenta");  
        }  

        return response;  
    }  
	
	
	
	public ResponseModel consultarMorososPorIdClienteONumeroCuenta(String idClienteONumeroCuenta) {
	    ResponseModel response = new ResponseModel();

	    try {
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
	        jdbcCall.withProcedureName("PKG_SIC_DEBT_ACCOUNTS2.FIND_BY_ID_CUSTOMER_OR_ACCOUNT");
	        jdbcCall.withoutProcedureColumnMetaDataAccess();
	        jdbcCall.setFunction(false);
	        jdbcCall.declareParameters(
	            new SqlOutParameter("P_OUT_DATA", OracleTypes.CURSOR),
	            new SqlParameter("P_IN_CONSULTA", OracleTypes.VARCHAR),
	            new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),
	            new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)
	        );

	        jdbcCall.returningResultSet("P_OUT_DATA", new MorososRowMapper());

	        MapSqlParameterSource inputMap = new MapSqlParameterSource();
	        inputMap.addValue("P_IN_CONSULTA", idClienteONumeroCuenta);

	        Map<String, Object> resultMap = jdbcCall.execute(inputMap);

	        ArrayList<MorosoDTO> morososList = (ArrayList<MorosoDTO>) resultMap.get("P_OUT_DATA");
	        String codRetorno = (String) resultMap.get("COD_RET");
	        String descRetorno = (String) resultMap.get("DE_RET");

	        if (codRetorno.equals("1000")) {
	            response.setStatus(200);
	            response.setCode(Integer.parseInt(codRetorno));
	            response.setMessage(descRetorno);
	            response.setData(morososList);
	        } else {
	            response.setStatus(500);
	            response.setCode(9999);
	            response.setMessage("Error en la consulta de créditos morosos por ID de Cliente o Número de Cuenta");
	        }

	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        response.setStatus(500);
	        response.setCode(9999);
	        response.setMessage("Error al consultar morosos por ID de Cliente o Número de Cuenta");
	    }

	    return response;
	}
	
	
	
	public ResponseModel consultarCreditosMorosos() {
	    ResponseModel response = new ResponseModel();

	    try {
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
	        jdbcCall.withProcedureName("PKG_SIC_DEBT_ACCOUNTS2.CONSULTA_CONTRATO");
	        jdbcCall.withoutProcedureColumnMetaDataAccess();
	        jdbcCall.setFunction(false);
	        jdbcCall.declareParameters(
	        		new SqlOutParameter("P_OUT_DATA", OracleTypes.CURSOR),
	                new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),
	                new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)
	        );
	        
	        jdbcCall.returningResultSet("P_OUT_DATA", new CreditosMorososRowMapper());

	        MapSqlParameterSource inputMap = new MapSqlParameterSource();

	        Map<String, Object> resultMap = jdbcCall.execute(inputMap);
	        ArrayList<CreditoMorosoDTO> morososList = (ArrayList<CreditoMorosoDTO>) resultMap.get("P_OUT_DATA");
	        String codRetorno = (String) resultMap.get("COD_RET");
	        String descRetorno = (String) resultMap.get("DE_RET");

	        if (codRetorno.equals("1000")) {
	            response.setStatus(200);
	            response.setCode(Integer.parseInt(codRetorno));
	            response.setMessage(descRetorno);
	            response.setData(morososList);
	        } else {
	            response.setStatus(500);
	            response.setCode(9999);
	            response.setMessage("Error en la consulta de creditos");
	        }

	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        response.setStatus(500);
	        response.setCode(9999);
	        response.setMessage("Error al consultar creditos");
	    }

	    return response;
	}  
	
	
	public ResponseModel consultarCreditosMorososPorIdClienteONumeroCuenta(String CreditoONumeroCuenta) {
	    ResponseModel response = new ResponseModel();

	    try {
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
	        jdbcCall.withProcedureName("PKG_SIC_DEBT_ACCOUNTS2.FIND_CREDIT_BY_ID_CUSTOMER_OR_ACCOUNT");
	        jdbcCall.withoutProcedureColumnMetaDataAccess();
	        jdbcCall.setFunction(false);
	        jdbcCall.declareParameters(
	            new SqlOutParameter("P_OUT_DATA", OracleTypes.CURSOR),
	            new SqlParameter("P_IN_CONSULTA", OracleTypes.VARCHAR),
	            new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),
	            new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)
	        );

	        jdbcCall.returningResultSet("P_OUT_DATA", new CreditosMorososRowMapper());

	        MapSqlParameterSource inputMap = new MapSqlParameterSource();
	        inputMap.addValue("P_IN_CONSULTA", CreditoONumeroCuenta);

	        Map<String, Object> resultMap = jdbcCall.execute(inputMap);

	        ArrayList<CreditoMorosoDTO> morososList = (ArrayList<CreditoMorosoDTO>) resultMap.get("P_OUT_DATA");
	        String codRetorno = (String) resultMap.get("COD_RET");
	        String descRetorno = (String) resultMap.get("DE_RET");

	        if (codRetorno.equals("1000")) {
	            response.setStatus(200);
	            response.setCode(Integer.parseInt(codRetorno));
	            response.setMessage(descRetorno);
	            response.setData(morososList);
	        } else {
	            response.setStatus(500);
	            response.setCode(9999);
	            response.setMessage("Error en la consulta de créditos morosos");
	        }

	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        response.setStatus(500);
	        response.setCode(9999);
	        response.setMessage("Error al consultar los créditos morosos");
	    }

	    return response;
	}
	
	public ResponseModel insertarBitacora(BitacoraDTO datos) {  
        ResponseModel response = new ResponseModel();  
        try {  
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)  
                    .withProcedureName("PKG_AUDITORIA.INSERTAR_AUDITORIA")  
                    .withoutProcedureColumnMetaDataAccess()  
                    .declareParameters(  
                            new SqlParameter("P_DESCRIPCION", OracleTypes.VARCHAR),  
                            new SqlParameter("P_TIPO_ACCION", OracleTypes.VARCHAR),  
                            new SqlParameter("P_USUARIO_ACCION", OracleTypes.VARCHAR),  
                            new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),  
                            new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)  
                    );  

            MapSqlParameterSource inputMap = new MapSqlParameterSource();  
            inputMap.addValue("P_DESCRIPCION", datos.getDescripcion());  
            inputMap.addValue("P_TIPO_ACCION", datos.getTipoAccion());  
            inputMap.addValue("P_USUARIO_ACCION", datos.getUsuarioAccion());  

            Map<String, Object> resultMap = jdbcCall.execute(inputMap);  
            String codRetorno = (String) resultMap.get("COD_RET");  
            String descRetorno = (String) resultMap.get("DE_RET");  

            if ("1000".equals(codRetorno)) {  
                response.setCode(1000);  
                response.setStatus(200);  
                response.setMessage(descRetorno);  
            } else {  
                response.setCode(1001);  
                response.setMessage("Error al insertar en la bitácora.");  
                response.setStatus(204);  
            }  

        } catch (Exception e) {  
            response.setCode(9999);  
            response.setMessage("Error al llamar al procedimiento INSERTAR_AUDITORIA");  
            response.setStatus(500);  
            log.error(e.getMessage(), e);  
        }  

        return response;  
    }  
	
	
	public ResponseModel insertarCobrador(CobradorDTO datos) {  
	    ResponseModel response = new ResponseModel();  
	    try {  
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)  
	                .withProcedureName("PKG_COLLECTOR.INSERTAR_COBRADOR")  
	                .withoutProcedureColumnMetaDataAccess()  
	                .declareParameters(    
	                    new SqlParameter("P_NOMBRE", OracleTypes.VARCHAR),  
	                    new SqlParameter("P_HORA_INICIAL", OracleTypes.VARCHAR),  
	                    new SqlParameter("P_HORA_FINAL", OracleTypes.VARCHAR),  
	                    new SqlParameter("P_ESTATUS", OracleTypes.VARCHAR),  
	                    new SqlParameter("P_PRIORITY", OracleTypes.NUMBER),  
	                    new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),  
	                    new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)  
	                );  

	        MapSqlParameterSource inputMap = new MapSqlParameterSource();  
	        inputMap.addValue("P_NOMBRE", datos.getCollector_name());  
	        inputMap.addValue("P_HORA_INICIAL", datos.getStart_time());  
	        inputMap.addValue("P_HORA_FINAL", datos.getFinal_time());  
	        inputMap.addValue("P_ESTATUS", datos.getStatus());  
	        inputMap.addValue("P_PRIORITY", datos.getPriority());  

	        Map<String, Object> resultMap = jdbcCall.execute(inputMap);  
	        String cod_retorno = (String) resultMap.get("COD_RET");  
	        String desc_retorno = (String) resultMap.get("DE_RET");  

	        if (cod_retorno.equals("1000")) {  
	            response.setCode(1000);  
	            response.setStatus(200);  
	            response.setMessage(desc_retorno);  
	        } else {  
	            response.setCode(1001);  
	            response.setMessage("Cobrador registrado correctamente");  
	            response.setStatus(204);  
	        }  

	    } catch (Exception e) {  
	        response.setCode(9999);  
	        response.setMessage("Error al llamar al procedimiento INSERTAR_COBRADOR");  
	        response.setStatus(500);  
	        log.error(e.getMessage(), e);  
	    }  

	    return response;  
	}
	
	
	
	public ResponseModel mostrarCobradores() {
	    ResponseModel response = new ResponseModel();

	    try {
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
	        jdbcCall.withProcedureName("PKG_COLLECTOR.MOSTRAR_TODOS_COBRADORES");
	        jdbcCall.withoutProcedureColumnMetaDataAccess();
	        jdbcCall.setFunction(false);
	        jdbcCall.declareParameters(
	            new SqlOutParameter("P_OUT_DATA", OracleTypes.CURSOR),
	            new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),
	            new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)
	        );

	        jdbcCall.returningResultSet("P_OUT_DATA", new CobradorRowMapper());

	        Map<String, Object> resultMap = jdbcCall.execute();
	        List<CobradorDTO> cobradoresList = (List<CobradorDTO>) resultMap.get("P_OUT_DATA");
	        String codRetorno = (String) resultMap.get("COD_RET");
	        String descRetorno = (String) resultMap.get("DE_RET");

	        response.setStatus(codRetorno.equals("1000") ? 200 : 500);
	        response.setCode(Integer.parseInt(codRetorno));

	        if (codRetorno.equals("1000")) {
	            response.setMessage(descRetorno);
	            response.setData(cobradoresList);
	        } else {
	            response.setMessage("Error en la consulta de cobradores");
	        }

	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        response.setStatus(500);
	        response.setCode(9999);
	        response.setMessage("Error al consultar cobradores");
	    }

	    return response;
	}
	
	
	public ResponseModel actualizarCobrador(CobradorDTO datos) {  
	    ResponseModel response = new ResponseModel();  
	    try {  
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)  
	                .withProcedureName("PKG_COLLECTOR.ACTUALIZAR_COBRADOR")  
	                .withoutProcedureColumnMetaDataAccess()  
	                .declareParameters(  
	                    new SqlParameter("P_ID_COBRADOR", OracleTypes.NUMBER),  
	                    new SqlParameter("P_NOMBRE", OracleTypes.VARCHAR),  
	                    new SqlParameter("P_HORA_INICIAL", OracleTypes.VARCHAR),  
	                    new SqlParameter("P_HORA_FINAL", OracleTypes.VARCHAR),  
	                    new SqlParameter("P_ESTATUS", OracleTypes.VARCHAR),  
	                    new SqlParameter("P_PRIORITY", OracleTypes.NUMBER),  
	                    new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),  
	                    new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)  
	                );  

	        MapSqlParameterSource inputMap = new MapSqlParameterSource();  
	        inputMap.addValue("P_ID_COBRADOR", datos.getCollector_id());  
	        inputMap.addValue("P_NOMBRE", datos.getCollector_name());  
	        inputMap.addValue("P_HORA_INICIAL", datos.getStart_time());  
	        inputMap.addValue("P_HORA_FINAL", datos.getFinal_time());  
	        inputMap.addValue("P_ESTATUS", datos.getStatus());  
	        inputMap.addValue("P_PRIORITY", datos.getPriority());  

	        Map<String, Object> resultMap = jdbcCall.execute(inputMap);  
	        String cod_retorno = (String) resultMap.get("COD_RET");  
	        String desc_retorno = (String) resultMap.get("DE_RET");  

	        if (cod_retorno.equals("1000")) {  
	            response.setCode(1000);  
	            response.setStatus(200);  
	            response.setMessage(desc_retorno);  
	        } else {  
	            response.setCode(1001);  
	            response.setMessage("Error al actualizar el cobrador con ID " + datos.getCollector_id());  
	            response.setStatus(204);  
	        }  

	    } catch (Exception e) {  
	        response.setCode(9999);  
	        response.setMessage("Error al llamar al procedimiento ACTUALIZAR_COBRADOR");  
	        response.setStatus(500);  
	        log.error(e.getMessage(), e);  
	    }  

	    return response;  
	}
	
	
	 public ResponseModel eliminarCobrador(CobradorDTO datos) {
			ResponseModel response = new ResponseModel();
			try {
				SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
				jdbcCall.withProcedureName("PKG_COLLECTOR.ELIMINAR_COBRADOR");
				jdbcCall.withoutProcedureColumnMetaDataAccess();
				jdbcCall.setFunction(false);
				jdbcCall.declareParameters(
					    new SqlParameter("P_ID_COBRADOR", OracleTypes.NUMBER),
					    new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),
					    new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)
					);
				MapSqlParameterSource inputMap = new MapSqlParameterSource();
				inputMap.addValue("P_ID_COBRADOR", datos.getCollector_id());

				Map<String, Object> resultMap = jdbcCall.execute(inputMap);
				String cod_retorno = (String) resultMap.get("COD_RET");
				String desc_retorno = (String) resultMap.get("DE_RET");
				log.info("Resultado => {},{}", cod_retorno, desc_retorno);
				if (cod_retorno.equals("1000")) {
					response.setCode(1000);
					response.setStatus(200);
					response.setCode(Integer.parseInt(cod_retorno));
					response.setMessage(desc_retorno);
					return response;
				} else {
					response.setCode(1001);
					response.setMessage("Error al eliminar el registro");
					response.setStatus(500);
					return response;
				}
			} catch (Exception e) {

				log.error(e.getMessage(), e);
				response.setCode(9999);
				response.setMessage("ERROR al llamar al procedimiento ELIMINAR_COBRADOR");
				response.setStatus(500);
				return response;
			}
		}
	 
	 
	 
	 public ResponseModel consultarEstadistica1(Date fecha) {  
		    ResponseModel response = new ResponseModel();  

		    try {  
		        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);  
		        jdbcCall.withProcedureName("GIOM.PKG_STATISTICS.OBTENER_ESTADISTICAS_POR_FECHA");  
		        jdbcCall.withoutProcedureColumnMetaDataAccess();  
		        jdbcCall.setFunction(false);  
		        
		        // Declarar los parámetros de entrada y salida según el stored procedure  
		        jdbcCall.declareParameters(  
		            new SqlParameter("P_FECHA", OracleTypes.DATE),  // Parámetro de entrada  
		            new SqlOutParameter("P_OUT_DATA", OracleTypes.CURSOR),  
		            new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),  
		            new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)  
		        );  

		        // Indicar que se devolverá un ResultSet  
		        jdbcCall.returningResultSet("P_OUT_DATA", new Estadistica1RowMapper());  

		        // Preparar los parámetros de entrada  
		        MapSqlParameterSource inputMap = new MapSqlParameterSource();  
		        inputMap.addValue("P_FECHA", fecha);  // Agregar la fecha ingresada como parámetro  

		        // Ejecutar el procedimiento  
		        Map<String, Object> resultMap = jdbcCall.execute(inputMap);  
		        List<Estadistica1DTO> estadisticasList = (List<Estadistica1DTO>) resultMap.get("P_OUT_DATA");  
		        String codRetorno = (String) resultMap.get("COD_RET");  
		        String descRetorno = (String) resultMap.get("DE_RET");  

		        // Manejo de la respuesta  
		        if (codRetorno.equals("200")) {  
		            response.setStatus(200);  
		            response.setCode(Integer.parseInt(codRetorno));  
		            response.setMessage(descRetorno);  
		            response.setData(estadisticasList);  
		        } else {  
		            response.setStatus(500);  
		            response.setCode(Integer.parseInt(codRetorno));  // Utilizar el código retornado del procedimiento  
		            response.setMessage(descRetorno);  // Usar la descripción retornada  
		        }  

		    } catch (Exception e) {  
		        log.error(e.getMessage(), e);  
		        response.setStatus(500);  
		        response.setCode(9999);  
		        response.setMessage("Error al consultar estadísticas");  
		    }  

		    return response;  
		}
	 
	 
	 
	 
	 public ResponseModel consultarEstadistica2(Date fecha) {  
		    ResponseModel response = new ResponseModel();  

		    try {  
		        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);  
		        jdbcCall.withProcedureName("GIOM.PKG_STATISTICS.OBTENER_ESTADISTICAS_POR_FECHA");  
		        jdbcCall.withoutProcedureColumnMetaDataAccess();  
		        jdbcCall.setFunction(false);  
		        
		        // Declarar los parámetros de entrada y salida según el stored procedure  
		        jdbcCall.declareParameters(  
		            new SqlParameter("P_FECHA", OracleTypes.DATE),  // Parámetro de entrada  
		            new SqlOutParameter("P_OUT_DATA", OracleTypes.CURSOR),  
		            new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),  
		            new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)  
		        );  

		        // Indicar que se devolverá un ResultSet  
		        jdbcCall.returningResultSet("P_OUT_DATA", new Estadistica1RowMapper());  

		        // Preparar los parámetros de entrada  
		        MapSqlParameterSource inputMap = new MapSqlParameterSource();  
		        inputMap.addValue("P_FECHA", fecha);  // Agregar la fecha ingresada como parámetro  

		        // Ejecutar el procedimiento  
		        Map<String, Object> resultMap = jdbcCall.execute(inputMap);  
		        List<Estadistica1DTO> estadisticasList = (List<Estadistica1DTO>) resultMap.get("P_OUT_DATA");  
		        String codRetorno = (String) resultMap.get("COD_RET");  
		        String descRetorno = (String) resultMap.get("DE_RET");  

		        // Manejo de la respuesta  
		        if (codRetorno.equals("200")) {  
		            response.setStatus(200);  
		            response.setCode(Integer.parseInt(codRetorno));  
		            response.setMessage(descRetorno);  
		            response.setData(estadisticasList);  
		        } else {  
		            response.setStatus(500);  
		            response.setCode(Integer.parseInt(codRetorno));  // Utilizar el código retornado del procedimiento  
		            response.setMessage(descRetorno);  // Usar la descripción retornada  
		        }  

		    } catch (Exception e) {  
		        log.error(e.getMessage(), e);  
		        response.setStatus(500);  
		        response.setCode(9999);  
		        response.setMessage("Error al consultar estadísticas");  
		    }  

		    return response;  
		}
	 
	 
	 public ResponseModel consultarEstadisticasPIC(Date fecha) {  
		    ResponseModel response = new ResponseModel();  

		    try {  
		        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);  
		        jdbcCall.withProcedureName("GIOM.PKG_STATISTICS2.ESTADISTICA2_PIC"); // Corrige el procedimiento  
		        jdbcCall.withoutProcedureColumnMetaDataAccess();  
		        jdbcCall.setFunction(false);  

		        // Declarar los parámetros de entrada y salida  
		        jdbcCall.declareParameters(  
		            new SqlParameter("P_FECHA", OracleTypes.DATE),  
		            new SqlOutParameter("P_OUT_DATA", OracleTypes.CURSOR),  
		            new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),  
		            new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)  
		        );  

		        // Indicar que se devolverá un ResultSet  
		        jdbcCall.returningResultSet("P_OUT_DATA", new Estadistica2RowMapper());  

		        // Preparar los parámetros de entrada  
		        MapSqlParameterSource inputMap = new MapSqlParameterSource();  
		        inputMap.addValue("P_FECHA", fecha);  

		        // Ejecutar el procedimiento  
		        Map<String, Object> resultMap = jdbcCall.execute(inputMap);  
		        List<Estadistica2DTO> estadisticasList = (List<Estadistica2DTO>) resultMap.get("P_OUT_DATA");  
		        String codRetorno = (String) resultMap.get("COD_RET");  
		        String descRetorno = (String) resultMap.get("DE_RET");  

		        // Manejo de la respuesta  
		        if ("200".equals(codRetorno)) {  
		            response.setStatus(200);  
		            response.setCode(Integer.parseInt(codRetorno));  
		            response.setMessage(descRetorno);  
		            response.setData(estadisticasList);  
		        } else {  
		            response.setStatus(500);  
		            response.setCode(Integer.parseInt(codRetorno));  
		            response.setMessage(descRetorno);  
		        }  

		    } catch (Exception e) {  
		        response.setStatus(500);  
		        response.setCode(9999);  
		        response.setMessage("Error al consultar estadísticas: " + e.getMessage());  
		    }  

		    return response;  
		}
	 
	 public ResponseModel consultarEstadisticasPCP(Date fecha) {  
		    ResponseModel response = new ResponseModel();  

		    try {  
		        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);  
		        jdbcCall.withProcedureName("GIOM.PKG_STATISTICS2.ESTADISTICA2_PCP"); // Corrige el procedimiento  
		        jdbcCall.withoutProcedureColumnMetaDataAccess();  
		        jdbcCall.setFunction(false);  

		        // Declarar los parámetros de entrada y salida  
		        jdbcCall.declareParameters(  
		            new SqlParameter("P_FECHA", OracleTypes.DATE),  
		            new SqlOutParameter("P_OUT_DATA", OracleTypes.CURSOR),  
		            new SqlOutParameter("COD_RET", OracleTypes.VARCHAR),  
		            new SqlOutParameter("DE_RET", OracleTypes.VARCHAR)  
		        );  

		        // Indicar que se devolverá un ResultSet  
		        jdbcCall.returningResultSet("P_OUT_DATA", new Estadistica2RowMapper());  

		        // Preparar los parámetros de entrada  
		        MapSqlParameterSource inputMap = new MapSqlParameterSource();  
		        inputMap.addValue("P_FECHA", fecha);  

		        // Ejecutar el procedimiento  
		        Map<String, Object> resultMap = jdbcCall.execute(inputMap);  
		        List<Estadistica2DTO> estadisticasList = (List<Estadistica2DTO>) resultMap.get("P_OUT_DATA");  
		        String codRetorno = (String) resultMap.get("COD_RET");  
		        String descRetorno = (String) resultMap.get("DE_RET");  

		        // Manejo de la respuesta  
		        if ("200".equals(codRetorno)) {  
		            response.setStatus(200);  
		            response.setCode(Integer.parseInt(codRetorno));  
		            response.setMessage(descRetorno);  
		            response.setData(estadisticasList);  
		        } else {  
		            response.setStatus(500);  
		            response.setCode(Integer.parseInt(codRetorno));  
		            response.setMessage(descRetorno);  
		        }  

		    } catch (Exception e) {  
		        response.setStatus(500);  
		        response.setCode(9999);  
		        response.setMessage("Error al consultar estadísticas: " + e.getMessage());  
		    }  

		    return response;  
		}
	 
	
	
}
