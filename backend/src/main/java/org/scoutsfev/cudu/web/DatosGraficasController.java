package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.dto.DatosPorGrupoRamaDto;
import org.scoutsfev.cudu.domain.dto.DatosPorGrupoTipoDto;
import org.scoutsfev.cudu.domain.dto.DatosPorPeriodoTipoDto;
import org.scoutsfev.cudu.domain.dto.DatosPorRamaTipoDto;
import org.scoutsfev.cudu.storage.DatosGraficasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.google.common.collect.Lists.transform;

@RestController
public class DatosGraficasController {

    private final DatosGraficasRepository datosGraficasRepository;

    @Autowired
    public DatosGraficasController(DatosGraficasRepository datosGraficasRepository) {
        this.datosGraficasRepository = datosGraficasRepository;
    }

    @RequestMapping(value = "/graficas/login", method = RequestMethod.GET)
    public DatosParaLogin login() {
        List<DatosPorRamaTipoDto> datosPorRamaTipoDto = datosGraficasRepository.findByDatosPorRamaTipoDto();
        List<DatosPorPeriodoTipoDto> datosPorPeriodoTipoDto = datosGraficasRepository.findByDatosPorPeriodoTipoDto();
        return new DatosParaLogin(generarDatosPorRamaTipo(datosPorRamaTipoDto), generarDatosPorPeriodoTipo(datosPorPeriodoTipoDto));
    }

    @RequestMapping(value = "/graficas/grupo/{id}", method = RequestMethod.GET)
    public Map<String, List<Long>> generarDatosParaGrupo(@PathVariable("id") String grupoId) {
        // TODO La cache la deberíamos poner a nivel de servicio, no de repositorio, los datos pueden cachearse
        // dentro de la misma clave en lugar de usar dos separadas (simplificará también el descarte en ctrl asocidado).
        HashMap<String, List<Long>> map = new HashMap<>();
        DatosPorGrupoRamaDto rama = datosGraficasRepository.findByDatosPorGrupoRamaDto(grupoId);
        map.put("rama", Arrays.asList(rama.getColonia(), rama.getManada(), rama.getExploradores(), rama.getExpedicion(), rama.getRuta()));
        DatosPorGrupoTipoDto tipo = datosGraficasRepository.findByDatosPorGrupoTipoDto(grupoId);
        map.put("tipo", Arrays.asList(tipo.getJoven(), tipo.getKraal(), tipo.getComite()));
        return map;
    }

    private Map<String, List<Long>> generarDatosPorRamaTipo(List<DatosPorRamaTipoDto> datosPorRamaTipo) {
        HashMap<String, List<Long>> map = new HashMap<>();
        if(!datosPorRamaTipo.isEmpty()){ 
        	DatosPorRamaTipoDto set1 = datosPorRamaTipo.get(0);
	        map.put(set1.isEsJoven() ? "joven" : "voluntario", Arrays.asList(set1.getColonia(), set1.getManada(), set1.getExploradores(), set1.getExpedicion(), set1.getRuta()));
	        DatosPorRamaTipoDto set2 = datosPorRamaTipo.get(1);
	        map.put(set2.isEsJoven() ? "joven" : "voluntario", Arrays.asList(set2.getColonia(), set2.getManada(), set2.getExploradores(), set2.getExpedicion(), set2.getRuta()));
        }
        return map;
    }

    private GraficaLineas generarDatosPorPeriodoTipo(List<DatosPorPeriodoTipoDto> datosPorPeriodoTipo) {
        GraficaLineas lineas = new GraficaLineas();
        lineas.setSeries(Arrays.asList("Jóvenes", "Voluntarios", "Total"));
        lineas.setEtiquetas(transform(datosPorPeriodoTipo, DatosPorPeriodoTipoDto::getPeriodo));
        List<List<Long>> datos = Arrays.asList(
                transform(datosPorPeriodoTipo, DatosPorPeriodoTipoDto::getJovenes),
                transform(datosPorPeriodoTipo, DatosPorPeriodoTipoDto::getVoluntarios),
                transform(datosPorPeriodoTipo, DatosPorPeriodoTipoDto::getTotal));
        lineas.setDatos(datos);
        return lineas;
    }

    public class GraficaLineas {
        private List<Integer> etiquetas = new ArrayList<>();
        private List<String> series = new ArrayList<>();
        private List<List<Long>> datos = new ArrayList<>();

        public List<Integer> getEtiquetas() {
            return etiquetas;
        }

        public void setEtiquetas(List<Integer> etiquetas) {
            this.etiquetas = etiquetas;
        }

        public List<String> getSeries() {
            return series;
        }

        public void setSeries(List<String> series) {
            this.series = series;
        }

        public List<List<Long>> getDatos() {
            return datos;
        }

        public void setDatos(List<List<Long>> datos) {
            this.datos = datos;
        }
    }

    public class DatosParaLogin {
        private Map<String, List<Long>> datosPorRamaTipo;
        private GraficaLineas datosPorPeriodoTipo;

        public DatosParaLogin(Map<String, List<Long>> datosPorRamaTipo, GraficaLineas datosPorPeriodoTipo) {
            this.datosPorRamaTipo = datosPorRamaTipo;
            this.datosPorPeriodoTipo = datosPorPeriodoTipo;
        }

        public Map<String, List<Long>> getDatosPorRamaTipo() {
            return datosPorRamaTipo;
        }

        public GraficaLineas getDatosPorPeriodoTipo() {
            return datosPorPeriodoTipo;
        }
    }
}
