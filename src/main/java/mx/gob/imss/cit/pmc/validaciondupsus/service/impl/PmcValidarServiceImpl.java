package mx.gob.imss.cit.pmc.validaciondupsus.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.mspmccommons.convert.ConvertNegResponse;
import mx.gob.imss.cit.mspmccommons.dto.BitacoraErroresDTO;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.resp.DetalleRegistroResponse;
import mx.gob.imss.cit.pmc.validaciondupsus.service.PmcValidaDuplicados;
import mx.gob.imss.cit.pmc.validaciondupsus.service.PmcValidaSusceptibles;
import mx.gob.imss.cit.pmc.validaciondupsus.service.PmcValidarService;

@Component
public class PmcValidarServiceImpl implements PmcValidarService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String VALIDACIONES = "Iniciando validaciones";
	private static final String DUPLICADO = "El registro se encuentra duplicado";

	@Autowired
	private PmcValidaDuplicados pmcValidaDuplicados;

	@Autowired
	private PmcValidaSusceptibles pmcValidaSusceptibles;

	@Override
	public List<BitacoraErroresDTO> validarDuplicados(DetalleRegistroResponse detalleRegistroResp) throws BusinessException {
		logger.info(VALIDACIONES);
		ConvertNegResponse convert = new ConvertNegResponse();
		DetalleRegistroDTO registroDTO = convert.getDetalleNeg(detalleRegistroResp);
		List<BitacoraErroresDTO> errores = new ArrayList<>();
		BitacoraErroresDTO error = null;
		if (pmcValidaDuplicados.existeRegistro(registroDTO)) {
			logger.info(DUPLICADO);
			error = new BitacoraErroresDTO();
			error.setDesCodigoError(DUPLICADO);
		}
		if (error != null) {
			errores.add(error);
		}
		return errores;
	}

	@Override
	public List<BitacoraErroresDTO> validarDuplicadosAlta(DetalleRegistroResponse detalleRegistroResp)
			throws BusinessException {
		logger.info(VALIDACIONES);
		ConvertNegResponse convert = new ConvertNegResponse();
		DetalleRegistroDTO registroDTO = convert.getDetalleNeg(detalleRegistroResp);
		List<BitacoraErroresDTO> errores = new ArrayList<>();
		BitacoraErroresDTO error = null;
		if (pmcValidaDuplicados.existeRegistroAlta(registroDTO)) {
			logger.info(DUPLICADO);
			error = new BitacoraErroresDTO();
			error.setDesCodigoError(DUPLICADO);
		}
		if (error != null) {
			errores.add(error);
		}
		return errores;
	}

	@Override
	public List<String> validarSusceptibles(DetalleRegistroResponse detalleRegistroResp) throws BusinessException {
		logger.info(VALIDACIONES);
		ConvertNegResponse convert = new ConvertNegResponse();
		DetalleRegistroDTO registroDTO = convert.getDetalleNeg(detalleRegistroResp);
		return pmcValidaSusceptibles.existeSusceptibleNss(registroDTO);
	}

}
