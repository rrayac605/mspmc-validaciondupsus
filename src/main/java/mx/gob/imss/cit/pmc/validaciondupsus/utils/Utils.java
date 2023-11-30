package mx.gob.imss.cit.pmc.validaciondupsus.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.mspmccommons.dto.AseguradoDTO;
import mx.gob.imss.cit.mspmccommons.dto.CambioDTO;
import mx.gob.imss.cit.mspmccommons.dto.DetalleRegistroDTO;
import mx.gob.imss.cit.mspmccommons.dto.IncapacidadDTO;
import mx.gob.imss.cit.mspmccommons.dto.RegistroDTO;

public class Utils {
	
	private static final Logger logger = LoggerFactory.getLogger("Utils");
	
	private Utils(){
		// No se llama
	}

	public static String obtenerNombreArchivo(String id) {
		DateFormat df = new SimpleDateFormat("ddMMyyyy");
		Calendar calendar = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append("_");
		sb.append(df.format(calendar.getTime()));
		sb.append(".txt");
		return sb.toString();
	}

	public static int validaEntero(String cadena) {
		int valor = 0;
		try {
			valor = Integer.valueOf(cadena);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return valor;
	}
	
	public static String validaCadena(String cadena) {
		String cadenaSalida = null;
		try {
			cadenaSalida = cadena != null && !cadena.trim().equals("") ? cadena : null;
		} catch (NumberFormatException e) {
			logger.error("validaCadena Valor: {} {}", cadena, e.getMessage());
		}
		return cadenaSalida;
	}

	public static BigDecimal validaBigDecimal(String cadena) {
		BigDecimal valor = BigDecimal.ZERO;
		try {
			valor = new BigDecimal(cadena);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return valor;
	}

	public static String[] separaNombres(String nombre) {
		 
		return nombre.split("\\$");
	}

	public static String obtenerCurp(RegistroDTO registroDTO) {
		return registroDTO.isErrorCurp()
				&& (registroDTO.getRefCurpBDTU() != null && !registroDTO.getRefCurpBDTU().trim().equals(""))
						? registroDTO.getRefCurpBDTU()
						: registroDTO.getRefCurp();
	}

	/**
	 * • Si los datos del identificador, folio, fecha de recepción y consecuencia
	 * son diferentes, asignarle al estado del registro: “Susceptibles de ajuste”.
	 * 
	 * @param susceptibles
	 * @param susceptible
	 * @param fechas
	 * @return
	 */
	public static boolean susceptibleUno(List<RegistroDTO> susceptibles, RegistroDTO susceptible) {
		boolean esSusceptible = false;
		for (RegistroDTO susceptibleLista : susceptibles) {
			if (!susceptibleLista.getRefFolioOriginal().equals(susceptible.getRefFolioOriginal())
					&& !susceptibleLista.getFecAtencion().equals(susceptible.getFecAtencion())
					&& !susceptibleLista.getCveConsecuencia().equals(susceptible.getCveConsecuencia())) {
				esSusceptible = true;
				break;
			}
		}
		return esSusceptible;
	}

	public static boolean susceptibleDos(List<RegistroDTO> susceptibles, Date fechaHabilInicio,
			Date fechaHabilFin) {
		boolean esSusceptible = false;
		for (RegistroDTO susceptibleLista : susceptibles) {
			if (DateUtils.parserFromStringISOMOngo(susceptibleLista.getFecInicio()).compareTo(fechaHabilInicio) < 0
					&& DateUtils.parserFromStringISOMOngo(susceptibleLista.getFecFin()).compareTo(fechaHabilFin) > 0) {
				esSusceptible = true;
				break;
			}
		}
		return esSusceptible;
	}

	/**
	 * • Cuando además coincidan los campos: Registro Patronal, Tipo de riesgo y
	 * fecha de inicio con diferente consecuencia se le asignará al estado del
	 * registro: “Susceptibles de ajuste”. Cuando se encuentren más de dos registros
	 * con porcentaje de incapacidad se tomará el del número de folio mayor (más
	 * reciente). para efecto de evitar ambigüedades y se le asignará el mismo
	 * estado del registro (susceptible de ajuste).
	 * 
	 * @param susceptibles
	 * @param susceptible
	 * @return
	 */
	public static boolean susceptibleTres(List<RegistroDTO> susceptibles, RegistroDTO susceptible) {
		
		return registroEsSusceptible(susceptibles, susceptible);
		
	}

	/**
	 * 
	 * @param susceptibles
	 * @param susceptible
	 * @return
	 */
	public static boolean susceptibleCuatro(List<RegistroDTO> susceptibles, RegistroDTO susceptible) {
				
		logger.info("***Paso 4 para validar susceptibles***");
		return registroEsSusceptible(susceptibles, susceptible);
		
	}

	/**
	 * • Cuando además coincidan los campos: Registro Patronal, Fecha de inicio,
	 * fecha fin y Tipo de riesgo siendo diferentes las consecuencias, días
	 * subsidiados y/o porcentaje de incapacidad, asignarle al estado del registro:
	 * “Susceptibles de ajuste”.
	 * 
	 * @param susceptibles
	 * @param susceptible
	 * @return
	 */
	public static boolean susceptibleCinco(List<RegistroDTO> susceptibles, RegistroDTO susceptible) {
		boolean esSusceptible = false;
		for (RegistroDTO susceptibleLista : susceptibles) {
			if (susceptibleLista.getRefRegistroPatronal().equals(susceptible.getRefRegistroPatronal())
					&& susceptibleLista.getFecInicio().equals(susceptible.getFecInicio())
					&& susceptibleLista.getFecFin().equals(susceptible.getFecFin())
					&& susceptibleLista.getCveTipoRiesgo().equals(susceptible.getCveTipoRiesgo())
					&& (!susceptibleLista.getCveConsecuencia().equals(susceptible.getCveConsecuencia())
							|| !susceptibleLista.getNumDiasSubsidiados().equals(susceptible.getNumDiasSubsidiados()))
					|| !susceptibleLista.getPorPorcentajeIncapacidad()
							.equals(susceptible.getPorPorcentajeIncapacidad())) {
				esSusceptible = true;
				break;
			}
		}
		return esSusceptible;
	}

	/**
	 * • Cuando además coincidan los campos: Registro Patronal, Tipo de riesgo y
	 * consecuencia, y el archivo de origen sea “RTT”, pero las fechas de inicio y
	 * termino sean diferentes, asignarle al estado del registro: “Susceptibles de
	 * ajuste”. * @param susceptibles
	 * 
	 * @param susceptible
	 * @return
	 */
	public static boolean susceptibleRTT(List<RegistroDTO> susceptibles, RegistroDTO susceptible) {
		boolean esSusceptible = false;
		for (RegistroDTO susceptibleLista : susceptibles) {
			if (susceptibleLista.getRefRegistroPatronal().equals(susceptible.getRefRegistroPatronal())
					&& susceptibleLista.getCveTipoRiesgo().equals(susceptible.getCveTipoRiesgo())
					&& susceptibleLista.getCveConsecuencia().equals(susceptible.getCveConsecuencia())
					&& !susceptibleLista.getFecInicio().equals(susceptible.getFecInicio())
					&& !susceptibleLista.getFecFin().equals(susceptible.getFecFin())) {
				esSusceptible = true;
				break;
			}
		}
		return esSusceptible;
	}

	/**
	 * • Cuando además coincidan los campos: Registro Patronal y Tipo de riesgo, y
	 * el archivo de origen sea “ST3 o ST5”, pero las fechas de inicio y termino
	 * sean diferentes, asignarle al estado del registro: “Susceptibles de ajuste”.
	 * 
	 * @param susceptibles
	 * @param susceptible
	 * @return
	 * 
	 */
	public static boolean susceptibleST3ST5(List<RegistroDTO> susceptibles, RegistroDTO susceptible) {
		boolean esSusceptible = false;
		for (RegistroDTO susceptibleLista : susceptibles) {
			if (susceptibleLista.getRefRegistroPatronal().equals(susceptible.getRefRegistroPatronal())
					&& susceptibleLista.getCveTipoRiesgo().equals(susceptible.getCveTipoRiesgo())
					&& !susceptibleLista.getFecInicio().equals(susceptible.getFecInicio())
					&& !susceptibleLista.getFecFin().equals(susceptible.getFecFin())) {
				esSusceptible = true;
				break;
			}
		}
		return esSusceptible;
	}

	public static boolean susceptibleSeis(List<RegistroDTO> susceptibles, RegistroDTO susceptible) {
		boolean esSusceptible = false;
		for (RegistroDTO susceptibleLista : susceptibles) {
			if (susceptibleLista.getRefRegistroPatronal().equals(susceptible.getRefRegistroPatronal())
					&& susceptibleLista.getCveTipoRiesgo().equals(susceptible.getCveTipoRiesgo())
					&& !susceptibleLista.getCveConsecuencia().equals(susceptible.getCveConsecuencia())
					&& !susceptibleLista.getFecInicio().equals(susceptible.getFecInicio())
					&& !susceptibleLista.getFecFin().equals(susceptible.getFecFin())
					&& (!susceptible.getNumDiasSubsidiados().trim().equals("")
							|| !susceptible.getPorPorcentajeIncapacidad().trim().equals(""))) {
				esSusceptible = true;
				break;
			}
		}
		return esSusceptible;
	}

	public static RegistroDTO detalleRegistroDTOtoRegistroDTO(DetalleRegistroDTO detalleRegistroDTO) {
		RegistroDTO registroDTO = new RegistroDTO();
		registroDTO.setRefFolioOriginal(detalleRegistroDTO.getAseguradoDTO().getRefFolioOriginal());
		registroDTO
				.setFecAtencion(DateUtils.parserDatetoString(detalleRegistroDTO.getIncapacidadDTO().getFecAtencion()));
		registroDTO.setFecInicio(DateUtils.parserDatetoString(detalleRegistroDTO.getIncapacidadDTO().getFecInicio()));
		registroDTO.setFecFin(DateUtils.parserDatetoString(detalleRegistroDTO.getIncapacidadDTO().getFecFin()));
		registroDTO.setCveTipoRiesgo(String.valueOf(detalleRegistroDTO.getIncapacidadDTO().getCveTipoRiesgo()));
		registroDTO.setCveConsecuencia(String.valueOf(detalleRegistroDTO.getIncapacidadDTO().getCveConsecuencia()));
		registroDTO.setRefRegistroPatronal(detalleRegistroDTO.getPatronDTO().getRefRegistroPatronal());
		registroDTO
				.setNumDiasSubsidiados(String.valueOf(detalleRegistroDTO.getIncapacidadDTO().getNumDiasSubsidiados()));
		registroDTO.setPorPorcentajeIncapacidad(
				String.valueOf(detalleRegistroDTO.getIncapacidadDTO().getPorPorcentajeIncapacidad()));
		return registroDTO;
	}
	
	public static List<DetalleRegistroDTO> detalleRegistroDTOtoRegistroDTO(List<CambioDTO> registroSusCambios) {
		List<DetalleRegistroDTO> listaDetalles = new ArrayList<>(); 
		for (CambioDTO cambioDTO: registroSusCambios) {
			DetalleRegistroDTO detalle = new DetalleRegistroDTO();
			detalle.setAseguradoDTO(new AseguradoDTO());
			detalle.setIncapacidadDTO(new IncapacidadDTO());
			detalle.setObjectIdArchivoDetalle(new ObjectId(cambioDTO.getObjectIdCambio()));
			detalle.setCveOrigenArchivo(cambioDTO.getCveOrigenArchivo());
			detalle.getAseguradoDTO().setNumNss(cambioDTO.getNumNss());
			detalle.getAseguradoDTO().setNumCicloAnual(cambioDTO.getNumCicloAnual());
			detalle.getAseguradoDTO().setCveEstadoRegistro(cambioDTO.getCveEstadoRegistro());	
			detalle.getIncapacidadDTO().setCveConsecuencia(String.valueOf(cambioDTO.getCveConsecuencia()));
			detalle.getIncapacidadDTO().setNumDiasSubsidiados(cambioDTO.getNumDiasSubsidiados());
			detalle.getIncapacidadDTO().setPorPorcentajeIncapacidad(cambioDTO.getPorcentajeIncapacidad());
			listaDetalles.add(detalle);
		}
		return listaDetalles;
	}
	
	private static boolean registroEsSusceptible(List<RegistroDTO> susceptibles, RegistroDTO susceptible) {
		
		boolean esSusceptible = false;
		
		for (RegistroDTO susceptibleLista : susceptibles) {
			
			if (susceptibleLista.getRefRegistroPatronal().equals(susceptible.getRefRegistroPatronal())
					&& susceptibleLista.getCveTipoRiesgo().equals(susceptible.getCveTipoRiesgo())
					&& susceptibleLista.getFecInicio().equals(susceptible.getFecInicio())
					&& !susceptibleLista.getCveConsecuencia().equals(susceptible.getCveConsecuencia())) {
			
				esSusceptible = true;
				break;
				
			}
			
		}
		
		return esSusceptible;
	}

}
