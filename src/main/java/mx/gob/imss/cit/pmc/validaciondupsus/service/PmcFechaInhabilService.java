package mx.gob.imss.cit.pmc.validaciondupsus.service;

import java.util.Date;

import mx.gob.imss.cit.mspmccommons.exception.BusinessException;

public interface PmcFechaInhabilService {

	Date obtenerFechasInhabiles(int numeroDias) throws BusinessException;

}
