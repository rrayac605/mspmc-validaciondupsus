package mx.gob.imss.cit.pmc.validaciondupsus.repository;

import java.util.Optional;

import mx.gob.imss.cit.mspmccommons.dto.ParametroDTO;


public interface ParametroRepository {

	Optional<ParametroDTO> findOneByCve(String cveIdParametro);

}
