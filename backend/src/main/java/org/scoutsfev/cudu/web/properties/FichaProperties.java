package org.scoutsfev.cudu.web.properties;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "ficha")
public class FichaProperties {

    private String carpetaPlantilla;
    private String carpetaFichas;

    public String getCarpetaPlantilla() {
        return this.carpetaPlantilla;
    }

    public void setCarpetaPlantilla(String carpetaPlantilla) {
        this.carpetaPlantilla = carpetaPlantilla;
    }

    public String getCarpetaFichas() {
        return this.carpetaFichas;
    }

    public void setCarpetaFichas(String carpetaFichas) {
        this.carpetaFichas = carpetaFichas;
    }
}
