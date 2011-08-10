/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.scoutsfev.cudu.domain;

import java.util.Date;
import java.util.List;

/**
 *
 * @author gaxp
 */
public class Inscripcion {


    int id;
    Asociado asociado;
    List<Monografico> monograficos;
    Date fechaInscripcion;
    boolean aceptado;
    boolean pagoRealizado;


}
