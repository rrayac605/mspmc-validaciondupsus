package mx.gob.imss.cit.pmc.validaciondupsus.repository;

import java.util.List;

import mx.gob.imss.cit.mspmccommons.dto.CambioDTO;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroTO;


public interface PmcDetalleRegistroRepository {

	boolean existeRegistro(DetalleRegistroDTO registroDTO);
	
	boolean existeRegistroAlta(DetalleRegistroDTO registroDTO);
	
	boolean existeRegistroAltaMovimientos(DetalleRegistroDTO registroDTO);	

	List<DetalleRegistroDTO> existeSusceptibleNss(DetalleRegistroDTO registroDTO);
	
	List<CambioDTO> existeSusceptibleNssCambios(DetalleRegistroDTO registroDTO);

}
