package org.scoutsfev.cudu.services;

import org.scoutsfev.cudu.domain.AmbitoCargo;
import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Cargo;
import org.scoutsfev.cudu.storage.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CargoService {
    private final CargoRepository cargoRepository;

    @Autowired
    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @Transactional
    public void asignar(Cargo cargo, Asociado asociado) {
        if (cargo.getAmbito() == AmbitoCargo.Federativo)
            throw new AccessDeniedException("No es posible asignar cargos federativos directamente.");
        if (cargo.isUnico())
            cargoRepository.desasignarCargoUnicoEnGrupo(cargo.getId(), asociado.getGrupoId());
        cargoRepository.asignar(cargo.getId(), asociado.getId());
    }

    @Transactional
    public Cargo asignarNuevo(String nombreCargo, Integer asociadoId) {
        Cargo cargo = new Cargo();
        cargo.setId(null);
        cargo.setEtiqueta(nombreCargo);
        cargo.setAmbito(AmbitoCargo.Personal);
        cargo.setPuntos(0);
        cargo.setUnico(false);
        Cargo guardado = cargoRepository.save(cargo);

        cargoRepository.asignar(guardado.getId(), asociadoId);
        return guardado;
    }

    @Transactional
    public void eliminar(Cargo cargo, Asociado asociado) {
        if (cargo.getAmbito() == AmbitoCargo.Federativo)
            throw new AccessDeniedException("No es posible eliminar cargos federativos directamente.");
        cargoRepository.desasignar(cargo.getId(), asociado.getId());
        if (cargo.getAmbito() == AmbitoCargo.Personal)
            cargoRepository.delete(cargo.getId());
    }
}
