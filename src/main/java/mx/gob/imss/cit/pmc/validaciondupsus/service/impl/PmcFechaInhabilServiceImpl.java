package mx.gob.imss.cit.pmc.validaciondupsus.service.impl;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.mspmccommons.dto.FechaInhabilDTO;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.pmc.validaciondupsus.repository.DiasInhabilesRepository;
import mx.gob.imss.cit.pmc.validaciondupsus.service.PmcFechaInhabilService;
import mx.gob.imss.cit.pmc.validaciondupsus.utils.DateUtils;

@Component
public class PmcFechaInhabilServiceImpl implements PmcFechaInhabilService {

	@Autowired
	private DiasInhabilesRepository diasInhabilesRepository;

	@Override
	public Date obtenerFechasInhabiles(int numeroDias) throws BusinessException {
		Date fechaHabil = null;
		LocalDate localDate = LocalDate.now();
		long millisLocalDate = localDate.atStartOfDay().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
		Calendar fechaProceso = Calendar.getInstance();
		fechaProceso.setTimeInMillis(millisLocalDate);
		try {
			List<FechaInhabilDTO> fechasInhabilesDTO = diasInhabilesRepository.findAll();
			fechaHabil = DateUtils.fechaHabil(fechaProceso.getTime(), fechasInhabilesDTO, numeroDias);

		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return fechaHabil;
	}

}
