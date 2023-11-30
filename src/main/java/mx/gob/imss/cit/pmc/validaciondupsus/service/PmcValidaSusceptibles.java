package mx.gob.imss.cit.pmc.validaciondupsus.service;

import java.util.List;

import org.springframework.stereotype.Component;

import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.dto.RegistroDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;

@Component
public interface PmcValidaSusceptibles {

	List<RegistroDTO> existeSusceptible(DetalleRegistroDTO registroDTO) throws BusinessException;

	List<String> existeSusceptibleNss(DetalleRegistroDTO registroDTO) throws BusinessException;

}
