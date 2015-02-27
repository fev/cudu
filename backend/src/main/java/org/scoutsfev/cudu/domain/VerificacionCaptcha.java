package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class VerificacionCaptcha {

    @JsonProperty("success")
    private boolean positiva = false;

    @JsonProperty("error-codes")
    private List<String> codigosDeError = new ArrayList<>();

    public static VerificacionCaptcha Positiva() {
        VerificacionCaptcha verificacion = new VerificacionCaptcha();
        verificacion.positiva = true;
        return verificacion;
    }

    public static VerificacionCaptcha Negativa(String razon) {
        VerificacionCaptcha verificacion = new VerificacionCaptcha();
        verificacion.positiva = false;
        verificacion.codigosDeError.add(razon);
        return verificacion;
    }

    public boolean isPositiva() {
        return positiva;
    }

    public List<String> getCodigosDeError() {
        return codigosDeError;
    }
}
