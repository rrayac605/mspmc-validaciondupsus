package mx.gob.imss.cit.pmc.validaciondupsus.repository.impl;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.cit.mspmccommons.dto.CambioDTO;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.enums.EstadoRegistroEnum;
import mx.gob.imss.cit.mspmccommons.utils.CustomAggregationOperation;
import mx.gob.imss.cit.mspmccommons.utils.Utils;
import mx.gob.imss.cit.pmc.validaciondupsus.enums.CamposAseguradoEnum;
import mx.gob.imss.cit.pmc.validaciondupsus.enums.CamposIncapacidadEnum;
import mx.gob.imss.cit.pmc.validaciondupsus.enums.CamposPatronEnum;
import mx.gob.imss.cit.pmc.validaciondupsus.repository.PmcDetalleRegistroRepository;

@Repository
public class PmcDetalleRegistroRepositoryImpl implements PmcDetalleRegistroRepository {

	private static final Logger logger = LoggerFactory.getLogger(PmcDetalleRegistroRepositoryImpl.class);

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public boolean existeRegistro(DetalleRegistroDTO registroDTO) {
		Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.setTime(registroDTO.getIncapacidadDTO().getFecInicio());
		calendarInicio.add(Calendar.MONTH, -1);
		Calendar calendarFin = Calendar.getInstance();
		calendarFin.setTime(registroDTO.getIncapacidadDTO().getFecFin());
		calendarFin.add(Calendar.MONTH, -1);
		Query query = new Query(Criteria.where(CamposPatronEnum.RP.getNombreCampo())
				.is(registroDTO.getPatronDTO().getRefRegistroPatronal()).and(CamposAseguradoEnum.NSS.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getNumNss()).and(CamposIncapacidadEnum.TIPO_RIESGO.getNombreCampo())
				.is(Utils.validaEntero(registroDTO.getIncapacidadDTO().getCveTipoRiesgo()))
				.and(CamposIncapacidadEnum.CONSECUENCIA.getNombreCampo())
				.is(Utils.validaEntero(registroDTO.getIncapacidadDTO().getCveConsecuencia()))
				.and(CamposIncapacidadEnum.FECHA_INICIO.getNombreCampo()).is(calendarInicio.getTime())
				.and(CamposIncapacidadEnum.FECHA_FIN.getNombreCampo()).is(calendarFin.getTime())
				.and(CamposIncapacidadEnum.DIAS_SUBSIDIADOS.getNombreCampo())
				.is(registroDTO.getIncapacidadDTO().getNumDiasSubsidiados())
				.and(CamposIncapacidadEnum.PORCENTAJE_INCAPACIDAD.getNombreCampo())
				.is(registroDTO.getIncapacidadDTO().getPorPorcentajeIncapacidad().toString())
				.and("aseguradoDTO.fecBaja").is(null));

		return mongoOperations.exists(query, DetalleRegistroDTO.class);
	}

	@Override
	public boolean existeRegistroAlta(DetalleRegistroDTO registroDTO) {

		Query query = new Query(Criteria.where(CamposPatronEnum.RP_CAMBIO.getNombreCampo())
				.is(registroDTO.getPatronDTO().getRefRegistroPatronal())
				.and(CamposAseguradoEnum.NSS_CAMBIO.getNombreCampo()).is(registroDTO.getAseguradoDTO().getNumNss())
				.and(CamposAseguradoEnum.DELEGACION_NSS_CAMBIO.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getCveDelegacionNss())
				.and(CamposAseguradoEnum.SUBDELEGACION_NSS_CAMBIO.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getCveSubdelNss())
				.and(CamposAseguradoEnum.UMF_ADSCRIPCION_CAMBIO.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getCveUmfAdscripcion())
				.and(CamposAseguradoEnum.DELEGACION_ATENCION_CAMBIO.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getCveDelegacionAtencion())
				.and(CamposAseguradoEnum.SUBDELEGACION_ATENCION_CAMBIO.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getCveSubDelAtencion())
				.and(CamposAseguradoEnum.UMF_EXPEDIDORA_CAMBIO.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getCveUmfExp()).and(CamposAseguradoEnum.CURP_CAMBIO.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getRefCurp()).and(CamposAseguradoEnum.NOMBRE_CAMBIO.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getNomAsegurado())
				.and(CamposAseguradoEnum.PRIMER_APELLIDO_CAMBIO.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getRefPrimerApellido())
				.and(CamposAseguradoEnum.SEGUNDO_APELLIDO_CAMBIO.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getRefSegundoApellido())
				.and(CamposIncapacidadEnum.FECHA_INICIO_CAMBIO.getNombreCampo())
				.is(registroDTO.getIncapacidadDTO().getFecInicio())
				.and(CamposIncapacidadEnum.FECHA_ACCIDENTE_CAMBIO.getNombreCampo())
				.is(registroDTO.getIncapacidadDTO().getFecAccidente())
				.and(CamposIncapacidadEnum.FECHA_ALTA_CAMBIO.getNombreCampo())
				.is(registroDTO.getIncapacidadDTO().getFecAlta())
				.and(CamposIncapacidadEnum.DIAS_SUBSIDIADOS_CAMBIO.getNombreCampo())
				.is(registroDTO.getIncapacidadDTO().getNumDiasSubsidiados())
				.and(CamposIncapacidadEnum.PORCENTAJE_INCAPACIDAD_CAMBIO.getNombreCampo())
				.is(registroDTO.getIncapacidadDTO().getPorPorcentajeIncapacidad().toString())
				.and(CamposIncapacidadEnum.TIPO_RIESGO_CAMBIO.getNombreCampo())
				.is(Utils.validaEntero(registroDTO.getIncapacidadDTO().getCveTipoRiesgo()))
				.and(CamposIncapacidadEnum.LAUDO_CAMBIO.getNombreCampo())
				.is(registroDTO.getIncapacidadDTO().getCveLaudo())
				.and(CamposIncapacidadEnum.CONSECUENCIA_CAMBIO.getNombreCampo())
				.is(Utils.validaEntero(registroDTO.getIncapacidadDTO().getCveConsecuencia())).and("fecBaja").is(null));

		logger.info("Query-->{}", query);

		return mongoOperations.exists(query, CambioDTO.class);
	}

	@Override
	public boolean existeRegistroAltaMovimientos(DetalleRegistroDTO registroDTO) {

		Query query = new Query(Criteria.where(CamposPatronEnum.RP_CAMBIO.getNombreCampo())
				.is(registroDTO.getPatronDTO().getRefRegistroPatronal())

				.and(CamposAseguradoEnum.NSS.getNombreCampo()).is(registroDTO.getAseguradoDTO().getNumNss())
				.and(CamposAseguradoEnum.DELEGACION_NSS.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getCveDelegacionNss())
				.and(CamposAseguradoEnum.SUBDELEGACION_NSS.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getCveSubdelNss())
				.and(CamposAseguradoEnum.UMF_ADSCRIPCION.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getCveUmfAdscripcion())
				.and(CamposAseguradoEnum.DELEGACION_ATENCION.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getCveDelegacionAtencion())
				.and(CamposAseguradoEnum.SUBDELEGACION_ATENCION.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getCveSubDelAtencion())
				.and(CamposAseguradoEnum.UMF_EXPEDIDORA.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getCveUmfExp()).and(CamposAseguradoEnum.CURP.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getRefCurp()).and(CamposAseguradoEnum.NOMBRE.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getNomAsegurado())
				.and(CamposAseguradoEnum.PRIMER_APELLIDO.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getRefPrimerApellido())
				.and(CamposAseguradoEnum.SEGUNDO_APELLIDO.getNombreCampo())
				.is(registroDTO.getAseguradoDTO().getRefSegundoApellido())

				.and(CamposIncapacidadEnum.FECHA_INICIO.getNombreCampo())
				.is(registroDTO.getIncapacidadDTO().getFecInicio())
				.and(CamposIncapacidadEnum.FECHA_ACCIDENTE.getNombreCampo())
				.is(registroDTO.getIncapacidadDTO().getFecAccidente())
				.and(CamposIncapacidadEnum.FECHA_ALTA.getNombreCampo()).is(registroDTO.getIncapacidadDTO().getFecAlta())
				.and(CamposIncapacidadEnum.DIAS_SUBSIDIADOS.getNombreCampo())
				.is(registroDTO.getIncapacidadDTO().getNumDiasSubsidiados())
				.and(CamposIncapacidadEnum.PORCENTAJE_INCAPACIDAD.getNombreCampo())
				.is(registroDTO.getIncapacidadDTO().getPorPorcentajeIncapacidad().toString())
				.and(CamposIncapacidadEnum.TIPO_RIESGO.getNombreCampo())
				.is(Utils.validaEntero(registroDTO.getIncapacidadDTO().getCveTipoRiesgo()))
				.and(CamposIncapacidadEnum.LAUDO.getNombreCampo()).is(registroDTO.getIncapacidadDTO().getCveLaudo())
				.and(CamposIncapacidadEnum.CONSECUENCIA.getNombreCampo())
				.is(Utils.validaEntero(registroDTO.getIncapacidadDTO().getCveConsecuencia()))
				.and("aseguradoDTO.fecBaja").is(null));

		logger.info("Query-->{}", query);

		return mongoOperations.exists(query, DetalleRegistroDTO.class);
	}

	@Override
	public List<DetalleRegistroDTO> existeSusceptibleNss(DetalleRegistroDTO registroDTO) {

		LookupOperation lookup = Aggregation.lookup("MCT_ARCHIVO", "identificadorArchivo", "_id", "archivoDTO");
		String jsonOpperation = "{ $project: {"
				+ " '_id':1,  'identificadorArchivo':1,  'aseguradoDTO.numNss': 1, 'aseguradoDTO.numCicloAnual': 1,'aseguradoDTO.cveEstadoRegistro': 1,"
				+ " 'incapacidadDTO.cveConsecuencia': 1, 'incapacidadDTO.numDiasSubsidiados': 1,"
				+ " 'incapacidadDTO.porPorcentajeIncapacidad': 1, 'aseguradoDTO.cveOrigenArchivo': 1}}";
		CustomAggregationOperation projection = new CustomAggregationOperation(jsonOpperation);
		TypedAggregation<DetalleRegistroDTO> aggregation = Aggregation.newAggregation(DetalleRegistroDTO.class,
				Aggregation.match(Criteria.where(CamposAseguradoEnum.NSS.getNombreCampo())
						.is(registroDTO.getAseguradoDTO().getNumNss())),
				Aggregation.match(Criteria.where(CamposAseguradoEnum.FECHA_BAJA.getNombreCampo()).is(null)), lookup,
				projection);

		AggregationResults<DetalleRegistroDTO> aggregationResults = mongoOperations.aggregate(aggregation,
				DetalleRegistroDTO.class);

		return aggregationResults.getMappedResults();
	}

	@Override
	public List<CambioDTO> existeSusceptibleNssCambios(DetalleRegistroDTO registroDTO) {

		LookupOperation lookup = Aggregation.lookup("MCT_ARCHIVO", "identificadorArchivo", "_id", "archivoDTO");
		String jsonOpperation = "{ $project: {"
				+ " '_id':1,  'identificadorArchivo':1,  'numNss': 1,  'numCicloAnual': 1,'cveEstadoRegistro': 1,"
				+ " 'cveConsecuencia': 1," + "  'numDiasSubsidiados': 1,"
				+ " 'porcentajeIncapacidad': 1, 'cveOrigenArchivo': 1}}";
		CustomAggregationOperation projection = new CustomAggregationOperation(jsonOpperation);
		TypedAggregation<CambioDTO> aggregation = Aggregation.newAggregation(CambioDTO.class,
				Aggregation.match(Criteria.where(CamposAseguradoEnum.NSS_CAMBIO.getNombreCampo())
						.is(registroDTO.getAseguradoDTO().getNumNss())),
				Aggregation
						.match(Criteria.where("cveEstadoRegistro").nin(EstadoRegistroEnum.BAJA.getCveEstadoRegistro(),
								EstadoRegistroEnum.BAJA_OTRAS_DELEGACIONES.getCveEstadoRegistro())),
				Aggregation.match(Criteria.where(CamposAseguradoEnum.FECHA_BAJA_CAMBIO.getNombreCampo()).is(null)),
				lookup, projection);

		AggregationResults<CambioDTO> aggregationResults = mongoOperations.aggregate(aggregation, CambioDTO.class);

		return aggregationResults.getMappedResults();
	}
}