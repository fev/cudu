package org.scoutsfev.cudu.web_security_expression;

import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler
{
	/** Constant - Custom expression - Prefix */
	public static final String CONEXION_WEB_SCOUTSFEV = "conexion_web_scoutsfev" ;

	/**
	 * Public constructor
	 */
	public CustomWebSecurityExpressionHandler()
	{
		this.setExpressionParser(new CustomExpressionParser(this.getExpressionParser())) ;
	}

	@Override
	protected StandardEvaluationContext createEvaluationContextInternal(final Authentication authentication, final FilterInvocation filterInvocation)
	{
		// New instance of Standard evaluation context
		final StandardEvaluationContext standardEvaluationContext = super.createEvaluationContextInternal(authentication, filterInvocation) ;

		// Set the prefix for all the expressions calls
		standardEvaluationContext.setVariable(CONEXION_WEB_SCOUTSFEV, new CustomExpressionMethods(authentication)) ;

		// Return the instance
		return standardEvaluationContext ;
	}
}
