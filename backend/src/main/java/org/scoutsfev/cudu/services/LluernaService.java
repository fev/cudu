package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.TipoMiembroEscuela;
import org.scoutsfev.cudu.domain.dto.MiembroEscuelaDto;
import org.scoutsfev.cudu.storage.CargoRepository;
import org.scoutsfev.cudu.storage.dto.MiembroEscuelaDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LluernaService {

    public static final int MESA_PEDAGOGICA = 37;

    private final CargoRepository cargoRepository;
    private final MiembroEscuelaDtoRepository miembroEscuelaDtoRepository;

    @Autowired
    public LluernaService(CargoRepository cargoRepository, MiembroEscuelaDtoRepository miembroEscuelaDtoRepository) {
        this.cargoRepository = cargoRepository;
        this.miembroEscuelaDtoRepository = miembroEscuelaDtoRepository;
    }

    public List<MiembroEscuelaDto> obtenerMiembros() {
        return miembroEscuelaDtoRepository.findAll();
    }

    @Transactional
    public void añadirMiembro(int asociadoId, TipoMiembroEscuela tipo, boolean esMesaPedagogica) {
        cargoRepository.asignar(tipo.getCargoId(), asociadoId);
        if (esMesaPedagogica)
            cargoRepository.asignar(MESA_PEDAGOGICA, asociadoId);
        else
            cargoRepository.desasignar(MESA_PEDAGOGICA, asociadoId);
    }

    @Transactional
    public void quitarMiembro(int asociadoId) {
        for (TipoMiembroEscuela tipoMiembroEscuela : TipoMiembroEscuela.values()) {
            cargoRepository.desasignar(tipoMiembroEscuela.getCargoId(), asociadoId);
        }
    }
}
