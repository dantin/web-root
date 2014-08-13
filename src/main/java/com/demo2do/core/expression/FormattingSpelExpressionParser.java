package com.demo2do.core.expression;

import org.springframework.core.convert.ConversionService;
import org.springframework.expression.ParseException;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeConverter;

/**
 * SpEL expression parser
 *
 * @author David
 */
public class FormattingSpelExpressionParser extends SpelExpressionParser {

    private ConversionService conversionService;

    /**
     * Create a parser with standard configuration.
     */
    public FormattingSpelExpressionParser() {
        super();
    }

    /**
     * Create a parser with some configured behavior.
     *
     * @param configuration custom configuration options
     */
    public FormattingSpelExpressionParser(SpelParserConfiguration configuration) {
        super(configuration);
    }

    /**
     * @param conversionService the conversionService to set
     */
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /* (non-Javadoc)
     * @see org.springframework.expression.spel.standard.SpelExpressionParser#doParseExpression(java.lang.String, org.springframework.expression.ParserContext)
	 */
    protected SpelExpression doParseExpression(String expressionString, ParserContext context) throws ParseException {
        SpelExpression spelExpression = super.doParseExpression(expressionString, context);
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.setTypeConverter(new StandardTypeConverter(conversionService));
        spelExpression.setEvaluationContext(standardEvaluationContext);
        return spelExpression;
    }

}
