package org.scoutsfev.cudu.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * Especifica las opciones por defecto que se incorporaran a los editores
 * del formulario (como formatos de fecha, por ejemplo). Se define como
 * bean desde cudu-servlet.xml.
 * @see http://static.springsource.org/spring/docs/3.0.1.RELEASE/reference/html/mvc.html#mvc-ann-webdatabinder
 * @author Luis Belloch
 *
 */
public class BindingInitializer implements WebBindingInitializer {
	
	@Autowired
    private Validator validator;

	public void initBinder(WebDataBinder binder, WebRequest request) {
		// Formato de la fecha
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
		
		// Validación (ver anotaciones en entidades)
		binder.setValidator(validator);
		
		// Especifica que los campos del formulario que se dejen vacios serán tratados
		// como nulos en lugar de ser tratados como strings en blanco.
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
}
