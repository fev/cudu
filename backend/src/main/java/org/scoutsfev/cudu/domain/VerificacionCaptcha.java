package org.scoutsfev.cudu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class VerificacionCaptcha {

    @JsonProperty("success")
    private boolean positiva = false;

    @JsonProperty("error-codes")
    private List<String> codigosDeError = new ArrayList<>();

    public boolean isPositiva() {
        return positiva;
    }

    public List<String> getCodigosDeError() {
        return codigosDeError;
    }
}
