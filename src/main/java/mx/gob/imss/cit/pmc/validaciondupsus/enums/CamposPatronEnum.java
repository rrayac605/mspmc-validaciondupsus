package mx.gob.imss.cit.pmc.validaciondupsus.enums;

import lombok.Getter;
import lombok.Setter;

public enum CamposPatronEnum {

	RP("patronDTO.refRegistroPatronal"),

	RP_CAMBIO("refRegistroPatronal");

	@Setter
	@Getter
	private String nombreCampo;

	private CamposPatronEnum(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

}
