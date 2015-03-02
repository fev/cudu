package org.scoutsfev.cudu.web;

import org.scoutsfev.cudu.domain.Asociado;
import org.scoutsfev.cudu.domain.Cargo;
import org.scoutsfev.cudu.domain.Usuario;
import org.scoutsfev.cudu.services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Esta clase da soporte a la edición de cargos en la pantalla de asociado
 * y también a la edición de cargos por parte de los técnicos federativos.
 * @implNote Los cargos relativos a un asociado mapean sobre una vista y un dto,
 * por lo que no es necesario utilizar este controlador para extraer dichos datos,
 * el controlador de asociados ya los devuelve con una sola consulta en BBDD.
 * @see org.scoutsfev.cudu.web.AsociadoController
 */
@RestController
public class CargoController {

    private final CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @RequestMapping(value = "/asociado/{id}/cargo/{cargoId}", method = RequestMethod.PUT)
    //@PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public ResponseEntity<Cargo> asignar(@PathVariable("cargoId") Cargo cargo, @PathVariable("id") Asociado asociado, @AuthenticationPrincipal Usuario usuario) {
        cargoService.asignar(cargo, asociado);
        return new ResponseEntity<>(cargo, HttpStatus.OK);
    }

    @RequestMapping(value = "/asociado/{id}/cargo", method = RequestMethod.POST)
    //@PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public ResponseEntity<Cargo> asignarNuevo(@RequestBody @NotNull @Size(max = 50) String nombreCargo, @PathVariable("id") Asociado asociado, @AuthenticationPrincipal Usuario usuario) {
        Cargo cargo = cargoService.asignarNuevo(nombreCargo, asociado.getId());
        return new ResponseEntity<>(cargo, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/asociado/{id}/cargo/{cargoId}", method = RequestMethod.DELETE)
    //@PreAuthorize("@auth.puedeEditarAsociado(#id, #usuario)")
    public void eliminar(@PathVariable("cargoId") Cargo cargo, @PathVariable("id") Asociado asociado, @AuthenticationPrincipal Usuario usuario) {
        cargoService.eliminar(cargo, asociado);
    }
}
