/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.scoutsfev.cudu.domain.validators;

import java.util.Calendar;
import org.scoutsfev.cudu.domain.Asociado;
import org.springframework.validation.Errors;

/**
 *
 * @author cudu
 */
public class EdadValidator {


    public void validate(Asociado  asociado, Errors errors) {

          //if(asociado.getRamas())
        //comprobación de las ramas
        int anyosAsociado = getAnyos(asociado);
        System.out.println(anyosAsociado);

        if(asociado.getTipo()=='J')
        {

        } else if(asociado.getTipo()=='K')
        {
            if (anyosAsociado < 18)
            {
                errors.rejectValue("name", "duplicate", "UN Kraal no puede tener menos de 18 años");
            }
        }
    }

    public static int getAnyos(Asociado asociado)
    {
        Calendar today = Calendar.getInstance();
        Calendar bornDay = Calendar.getInstance();
        bornDay.setTime(asociado.getFechanacimiento());

        int auxToday = today.get(Calendar.YEAR)  *1000  + today.get(Calendar.MONTH)   *100 + today.get(Calendar.DAY_OF_MONTH);
        int auxBorn  = bornDay.get(Calendar.YEAR)*1000  + bornDay.get(Calendar.MONTH) *100 +bornDay.get(Calendar.DAY_OF_MONTH);
        return (auxToday-auxBorn)/1000;
    }
}
