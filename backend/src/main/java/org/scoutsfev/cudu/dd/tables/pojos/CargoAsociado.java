/**
 * This class is generated by jOOQ
 */
package org.scoutsfev.cudu.dd.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CargoAsociado implements Serializable {

	private static final long serialVersionUID = -1788739809;

	private final Integer cargoId;
	private final Integer asociadoId;

	public CargoAsociado(CargoAsociado value) {
		this.cargoId = value.cargoId;
		this.asociadoId = value.asociadoId;
	}

	public CargoAsociado(
		Integer cargoId,
		Integer asociadoId
	) {
		this.cargoId = cargoId;
		this.asociadoId = asociadoId;
	}

	public Integer getCargoId() {
		return this.cargoId;
	}

	public Integer getAsociadoId() {
		return this.asociadoId;
	}
}
