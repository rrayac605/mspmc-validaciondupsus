package mx.gob.imss.cit.pmc.validaciondupsus.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mx.gob.imss.cit.mspmccommons.dto.BitacoraErroresDTO;
import mx.gob.imss.cit.mspmccommons.dto.ErrorResponse;
import mx.gob.imss.cit.mspmccommons.enums.EnumHttpStatus;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.resp.DetalleRegistroResponse;
import mx.gob.imss.cit.pmc.validaciondupsus.service.PmcValidarService;

@RestController
@Api(value = "Validadion almacenes PMC", tags = { "Validadion duplicados, susceptibles PMC Rest" })
@RequestMapping("/msvalidaciondupsus/v1")
public class MpcValidacionesAlmacenesController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String ARCHIVO= "MpcArchivosController:buscarEstadoArchivo:try [{}]";
	private static final String ESTADO = "MpcArchivosController:buscarEstadoArchivo:returnOk";
	private static final String CATCH = "MpcArchivosController:buscarEstadoArchivo:catch";
	private static final String ERROR = "Error de aplicaci�n";
	private static final String HTTP = "MpcArchivosController:buscarEstadoArchivo:numberHTTPDesired";
	private static final String FINAL = "MpcArchivosController:buscarEstadoArchivo:FinalReturn";

	@Autowired
	private PmcValidarService pmcValidarService;

	@RequestMapping("/health/ready")
	@ResponseStatus(HttpStatus.OK)
	public void ready() {
	}

	@RequestMapping("/health/live")
	@ResponseStatus(HttpStatus.OK)
	public void live() {
	}

	@ApiOperation(value = "Validaciones Duplicados", nickname = "validarDuplicados", notes = "Validaciones Duplicados", response = Object.class, responseContainer = "List", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Respuesta exitosa", response = Object.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Describe un error general del sistema", response = ErrorResponse.class) })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/validar", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object validarDuplicados(@RequestBody DetalleRegistroResponse input,@RequestHeader(value = "Authorization") String token) throws BusinessException {
		
		logger.info("*** Validando duplicados ***");
		Object resultado = null;
		
		try {
			
			logger.info(ARCHIVO, input);
			List<BitacoraErroresDTO> errores = pmcValidarService.validarDuplicados(input);
			resultado = new ResponseEntity<Object>(errores, HttpStatus.OK);
			logger.info(ESTADO);
			
		} catch (BusinessException be) {
			
			logger.info(CATCH);
			ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SERVER_ERROR_INTERNAL, be.getMessage(), ERROR);
			int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());
			resultado = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
			logger.info(HTTP);

		}
		
		logger.info(FINAL);
		
		return resultado;
		/*String bandera = "Normal";
		return resultado(input, bandera);*/
		
	}

	@ApiOperation(value = "Validaciones Duplicados", nickname = "validarDuplicados", notes = "Validaciones Duplicados", response = Object.class, responseContainer = "List", tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Respuesta exitosa", response = Object.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Describe un error general del sistema", response = ErrorResponse.class) })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/validarAlta", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object validarDuplicadosAlta(@RequestBody DetalleRegistroResponse input,@RequestHeader(value = "Authorization") String token) throws BusinessException {
		
		logger.info("*** Validando duplicados en alta ***");
		Object resultado = null;
		
		try {
			
			logger.info(ARCHIVO, input);
			List<BitacoraErroresDTO> errorAlta = pmcValidarService.validarDuplicadosAlta(input);
			resultado = new ResponseEntity<Object>(errorAlta, HttpStatus.OK);
			logger.info(ESTADO);
			
		} catch (BusinessException be) {
			
			logger.info(CATCH);
			ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SERVER_ERROR_INTERNAL, be.getMessage(), ERROR);
			int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());
			resultado = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
			logger.info(HTTP);

		}
		
		logger.info(FINAL);
		
		return resultado;
		
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/validarSusceptibles", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object validarSusceptibles(@RequestBody DetalleRegistroResponse input,@RequestHeader(value = "Authorization") String token) throws BusinessException {
				
		logger.info("*** Validando susceptibles ***");
		Object resultado = null;
		
		try {
			
			logger.info(ARCHIVO, input);
			List<String> errorSusceptible = pmcValidarService.validarSusceptibles(input);
			resultado = new ResponseEntity<Object>(errorSusceptible, HttpStatus.OK);
			logger.info(ESTADO);
			
		} catch (BusinessException be) {
			
			logger.info(CATCH);
			ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SERVER_ERROR_INTERNAL, be.getMessage(), ERROR);
			int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());
			resultado = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
			logger.info(HTTP);

		}
		
		logger.info(FINAL);
		
		return resultado;
		
	}

}
