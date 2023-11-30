package mx.gob.imss.cit.pmc.validaciondupsus.enums;

import lombok.Getter;
import lombok.Setter;

public enum CamposIncapacidadEnum {

	TIPO_RIESGO("incapacidadDTO.cveTipoRiesgo"),
	CONSECUENCIA("incapacidadDTO.cveConsecuencia"),
	FECHA_INICIO("incapacidadDTO.fecInicio"),
	FECHA_ATENCION("incapacidadDTO.fecAtencion"),
	FECHA_ACCIDENTE("incapacidadDTO.fecAccidente"),
	FECHA_INICIO_PENSION("incapacidadDTO.fecIniPension"),
	FECHA_ALTA("incapacidadDTO.fecAltaIncapacidad"),
	FECHA_EXPEDICION("incapacidadDTO.fecExpDictamen"),
	FECHA_FIN("incapacidadDTO.fecFin"),
	DIAS_SUBSIDIADOS("incapacidadDTO.numDiasSubsidiados"),
	PORCENTAJE_INCAPACIDAD("incapacidadDTO.porPorcentajeIncapacidad"),	
	LAUDO("incapacidadDTO.cveLaudo"),
	CODIGO_DIAGNOSTICO("incapacidadDTO.numCodigoDiagnostico"),
	MEDICO_TRATANTE("incapacidadDTO.numMatMedTratante"),
	MEDICO_AUTORIZADOR("incapacidadDTO.numMatMedAutCdst"),
	CAUSA_EXTERNA("incapacidadDTO.numCausaExterna"),
	NATURALEZA("incapacidadDTO.cveNaturaleza"),
	RIESGO_FISICO("incapacidadDTO.numRiesgoFisico"),
	ACTO_INSEGURO("incapacidadDTO.numActoInseguro"),
	
	FECHA_INICIO_CAMBIO("fecInicio"),
	FECHA_ACCIDENTE_CAMBIO("fecAccidente"),
	FECHA_ALTA_CAMBIO("fecAltaIncapacidad"),
	DIAS_SUBSIDIADOS_CAMBIO("numDiasSubsidiados"),
	PORCENTAJE_INCAPACIDAD_CAMBIO("porcentajeIncapacidad"),
	TIPO_RIESGO_CAMBIO("cveTipoRiesgo"),
	LAUDO_CAMBIO("cveLaudo"),
	CONSECUENCIA_CAMBIO("cveConsecuencia"),;

	@Setter
	@Getter
	private String nombreCampo;

	private CamposIncapacidadEnum(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

}