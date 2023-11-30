package mx.gob.imss.cit.pmc.validaciondupsus.service;

import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;

public interface PmcValidaDuplicados {

	boolean existeRegistro(DetalleRegistroDTO registroDTO) throws BusinessException;
	
	boolean existeRegistroAlta(DetalleRegistroDTO registroDTO) throws BusinessException;

}
