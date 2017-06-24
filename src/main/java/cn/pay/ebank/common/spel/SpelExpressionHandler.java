/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-28 14:51 创建
 *
 */
package cn.pay.ebank.common.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Spring el 表达式解析处理器
 *
 * @author zhyang@ebank.pay.cn
 */
public class SpelExpressionHandler implements ParserContext {
	public static final String DEFAULT_EXPRESSION_PREFIX = "#{";
	public static final String DEFAULT_EXPRESSION_SUFFIX = "}";
	/**
	 * 模板前缀
	 */
	private String expressionPrefix = DEFAULT_EXPRESSION_PREFIX;
	/**
	 * 模板后缀
	 */
	private String expressionSuffix = DEFAULT_EXPRESSION_SUFFIX;

	private EvaluationContext context = new StandardEvaluationContext ();

	@Override
	public String getExpressionSuffix () {
		return expressionSuffix;
	}

	public void setExpressionSuffix (String expressionSuffix) {
		this.expressionSuffix = expressionSuffix;
	}

	@Override
	public String getExpressionPrefix () {
		return expressionPrefix;
	}

	public void setExpressionPrefix (String expressionPrefix) {
		this.expressionPrefix = expressionPrefix;
	}

	public boolean isTemplate () {
		return true;
	}


	/**
	 * 设置上下文变量
	 *
	 * @param name
	 * @param value
	 */
	public void setVariable (String name, Object value) {
		context.setVariable (name, value);
	}

	/**
	 * 获取变量
	 *
	 * @param name
	 *
	 * @return
	 */
	public Object getVariable (String name) {
		return context.lookupVariable (name);
	}


	/**
	 * 解析表达式
	 *
	 * @param template
	 *
	 * @return
	 */
	public String parseExpression (String template) {
		ExpressionParser parser = new SpelExpressionParser ();
		Expression expression = parser.parseExpression (template, this);
		return expression.getValue (context, String.class);
	}

}
