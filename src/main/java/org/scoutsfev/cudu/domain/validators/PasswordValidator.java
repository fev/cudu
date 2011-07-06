
package org.scoutsfev.cudu.domain.validators;
 
import org.scoutsfev.cudu.domain.Usuario;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
 
 
public class PasswordValidator implements Validator{
 
	@Override
	public boolean supports(Class clazz) {
		//just validate the Customer instances
		return Usuario.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target,  Errors errors) {
 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
			"required.password", "Field name is required.");
 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"required.confirmPassword", "Field name is required.");
 
		Usuario cust = (Usuario)target;
 
		if(!(cust.getPassword().equals(cust.getConfirmarPassword()))){
			errors.rejectValue("password", "notmatch.password");
		}
                
 
	}
 
}
