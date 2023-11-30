package mx.gob.imss.cit.pmc.validaciondupsus.service;

import java.util.List;

import mx.gob.imss.cit.mspmccommons.dto.BitacoraErroresDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.resp.DetalleRegistroResponse;

public interface PmcValidarService {

	List<BitacoraErroresDTO> validarDuplicados(DetalleRegistroResponse detalleRegistroResp) throws BusinessException;

	List<BitacoraErroresDTO> validarDuplicadosAlta(DetalleRegistroResponse detalleRegistroResp) throws BusinessException;

	List<String> validarSusceptibles(DetalleRegistroResponse detalleRegistroResp) throws BusinessException;

}
