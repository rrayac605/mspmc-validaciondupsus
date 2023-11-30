package mx.gob.imss.cit.pmc.validaciondupsus.repository;

import java.util.List;

import mx.gob.imss.cit.mspmccommons.dto.FechaInhabilDTO;

public interface DiasInhabilesRepository {

	List<FechaInhabilDTO> findAll();

}
