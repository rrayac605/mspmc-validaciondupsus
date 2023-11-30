package mx.gob.imss.cit.pmc.validaciondupsus.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.mspmccommons.dto.FechaInhabilDTO;

public class DateUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

	private static final String PATTERN_HORA = "HH:mm ";
	private static final String PATTERN_FECHA = "dd 'de' MMMM 'del' yyyy";
	private static final String PATTERN_DDMMYYYY = "ddMMyyyy";
	private static final String PATTERN_REGEX_DDMMYYYY = "(0[1-9]|[12]\\d|3[01])((0[1-9]|1[0-2])[12]\\d{3})";

	/**
	 * Se genera la fecha con formato 10 de febrero del 2020
	 * 
	 * @return string de fecha
	 */
	public static String getFechaActual() {
		String pattern = PATTERN_FECHA;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("es", "MX"));
		
		return simpleDateFormat.format(new Date());
	}

	/**
	 * Se genera la hora con formato a 24 hrs
	 * 
	 * @return
	 */
	public static String getHoraActual() {
		String pattern2 = PATTERN_HORA;
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);

		return simpleDateFormat2.format(new Date());
	}

	public static Date getSysDateMongoISO() {
		return Calendar.getInstance().getTime();
	}

	public static String getFechaActualDDMMYYYY() {
		String pattern = PATTERN_DDMMYYYY;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("es", "MX"));

		return simpleDateFormat.format(new Date());
	}

	public static Date parserFromString(String fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DDMMYYYY);

		Date date = null;
		try {
			date = formatter.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date parserFromStringMongo(String fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DDMMYYYY);

		Date date = null;
		try {
			if (Pattern.matches(PATTERN_REGEX_DDMMYYYY, fecha)) {
				date = formatter.parse(fecha);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String parserDatetoString(Date fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DDMMYYYY);

		String date = "";
		try {
			date = formatter.format(fecha);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date parserFromStringISOMOngo(String fecha) {
		SimpleDateFormat formatter1 = new SimpleDateFormat(PATTERN_DDMMYYYY);
		Date date = null;
		try {
			if (Pattern.matches(PATTERN_REGEX_DDMMYYYY, fecha)) {
				date = formatter1.parse(fecha);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date obtenerFechaActualDate() {
		Date date = null;
		try {

			date = Calendar.getInstance().getTime();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date obtenerFecha30Date() {
		Date date = null;
		try {

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -30);
			date = cal.getTime();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date obtenerFecha62Date() {
		Date date = null;
		try {

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 62);
			date = cal.getTime();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date obtenerFecha3Date() {
		Date date = null;
		try {

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -3);
			date = cal.getTime();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date fechaHabil(Date fecha, List<FechaInhabilDTO> fechas, int dias) {

		Calendar fechaCal = Calendar.getInstance();
		fechaCal.setTime(fecha);
		int diasLocal = Math.abs(dias);				
		try {
			while (diasLocal != 0) {
				int quitarDias = 1;
				if (fechaCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| fechaCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					quitarDias = 0;
				} else {
					for (FechaInhabilDTO fechaInhabil : fechas) {
						if (fechaInhabil.getFecInhabil().equals(fechaCal.getTime())) {
							quitarDias = 0;
							break;
						}
					}
				}
				diasLocal = diasLocal - quitarDias;
				fechaCal.add(Calendar.DATE, dias < 0 ? -1 : 1);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return fechaCal.getTime();
	}
	

	public int diasHabiles(Calendar fechaInicial, Calendar fechaFinal, List<Date> listaFechasNoLaborables) {
		int diffDays = 0;
		boolean diaHabil = false;
		// mientras la fecha inicial sea menor o igual que la fecha final se cuentan los
		// dias
		while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {

			if (!listaFechasNoLaborables.isEmpty()) {
				
				diaHabil = diaInhabil(listaFechasNoLaborables, fechaInicial, diaHabil);
				
			} else {
				if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
						&& fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
					// se aumentan los dias de diferencia entre min y max
					diffDays++;
				}
			}
			if (diaHabil) {
				diffDays++;
			}
			// se suma 1 dia para hacer la validacion del siguiente dia.
			fechaInicial.add(Calendar.DATE, 1);
		}
		return diffDays;
	}
	
	private boolean diaInhabil (List<Date> listaFechasNoLaborables, Calendar fechaInicial, boolean diaHabil) {
		for (Date date : listaFechasNoLaborables) {
			Date fechaNoLaborablecalendar = fechaInicial.getTime();
			// si el dia de la semana de la fecha minima es diferente de sabado o domingo
			if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
					&& fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& !fechaNoLaborablecalendar.equals(date)) {
				// se aumentan los dias de diferencia entre min y max
				diaHabil = true;
			} else {
				diaHabil = false;
				break;
			}
		}
		
		return diaHabil;
	}

}
