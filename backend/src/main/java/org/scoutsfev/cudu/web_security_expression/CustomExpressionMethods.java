package org.scoutsfev.cudu.web_security_expression;

import java.util.Collection;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import org.scoutsfev.cudu.domain.Usuario;


public class CustomExpressionMethods
{
	/** Attribute - Authentication */
	private final Authentication authentication ;

	/**
	 * Public constructor
	 *
	 * @param authentication with the authentication
	 */
	public CustomExpressionMethods(final Authentication authentication)
	{
		this.authentication = authentication ;
	}

	/**
	 * @param decision with the final decision
	 * @return true if the user has permissions. Otherwise, it will throw an exception
	 */
	public boolean throwOnError(final boolean decision)
	{
		if (!decision)
		{
			final Throwable throwable = new RuntimeException("Insufficient scope for this resource") ;
			throw new AccessDeniedException(throwable.getMessage(), throwable) ;
		}

		return decision ;
	}

	/**
	 * @param configCustomField with the configuration custom field
	 * @return true if one of the scopes belongs to the expected ones
	 */
	public boolean notHasUsuarioAmbitoEdicion(final String configCustomField)
	{
      return String.valueOf(((Usuario)this.authentication.getPrincipal()).getAmbitoEdicion()) != configCustomField;
	}
}
