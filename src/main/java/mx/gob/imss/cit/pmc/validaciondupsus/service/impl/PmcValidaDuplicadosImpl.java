package mx.gob.imss.cit.pmc.validaciondupsus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.pmc.validaciondupsus.repository.PmcDetalleRegistroRepository;
import mx.gob.imss.cit.pmc.validaciondupsus.service.PmcValidaDuplicados;

@Component
public class PmcValidaDuplicadosImpl implements PmcValidaDuplicados {

	@Autowired
	private PmcDetalleRegistroRepository detalleRegistroRepository;

	@Override
	public boolean existeRegistro(DetalleRegistroDTO registroDTO) throws BusinessException {
		try {
			return detalleRegistroRepository.existeRegistro(registroDTO);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	@Override
	public boolean existeRegistroAlta(DetalleRegistroDTO registroDTO) throws BusinessException {
		try {
			return detalleRegistroRepository.existeRegistroAlta(registroDTO)
					|| detalleRegistroRepository.existeRegistroAltaMovimientos(registroDTO);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
