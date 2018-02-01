package org.scoutsfev.cudu.web_security_expression;


import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.ParserContext;
import org.springframework.util.Assert;


public class CustomExpressionParser implements ExpressionParser
{
	/** Attribute - Delegate */
	private final ExpressionParser delegate ;

	/**
	 * @param delegate with the delegate
	 */
	public CustomExpressionParser(final ExpressionParser delegate)
	{
		Assert.notNull(delegate, "delegate cannot be null") ;

		this.delegate = delegate ;
	}

	/**
	 * @param expressionString with the expression string
	 * @return the expression
	 */
	public Expression parseExpression(final String expressionString) throws ParseException
	{
		return this.delegate.parseExpression(this.wrapExpression(expressionString)) ;
	}

	/**
	 * @param expressionString with the expression string
	 * @param parserContext    with the parser context
	 * @return the expression
	 */
	public Expression parseExpression(final String expressionString, final ParserContext parserContext) throws ParseException
	{
		return this.delegate.parseExpression(this.wrapExpression(expressionString), parserContext) ;
	}

	/**
	 * @param expressionString with the expression string
	 * @return the wrapped expression
	 */
	private String wrapExpression(final String expressionString)
	{
		return "#" + CustomWebSecurityExpressionHandler.CONEXION_WEB_SCOUTSFEV + ".throwOnError(" + expressionString + ")" ;
	}
}
