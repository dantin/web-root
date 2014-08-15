package com.demo2do.core.web.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor for InfoMessage annotation
 *
 * @author David
 */
public class RedirectMessageInterceptor extends HandlerInterceptorAdapter {

    private ExpressionParser parser = new SpelExpressionParser();

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (handler instanceof HandlerMethod) {

            String infoMessage = null;

            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // find InfoMessage Annotation
            InfoMessage infoMessageAnnotation = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), InfoMessage.class);

            if (infoMessageAnnotation != null) {

                // get infoMessage value from annotation, this may get a template
                infoMessage = infoMessageAnnotation.value();

                // parse infoMessage with model if it is a template
                if (StringUtils.isNotEmpty(infoMessageAnnotation.model())) {

                    String model = infoMessageAnnotation.model();

                    if (modelAndView.getModelMap().containsKey(model)) {
                        infoMessage = (String) parser.parseExpression(infoMessage, new InfoMessageTemplateParserContext())
                                .getValue(new StandardEvaluationContext(modelAndView.getModel().get(model)));
                    }

                }

                RequestContextUtils.getOutputFlashMap(request).put("infoMessage", infoMessage);

            }

        }

        super.postHandle(request, response, handler, modelAndView);
    }

    public static class InfoMessageTemplateParserContext implements ParserContext {

        /* (non-Javadoc)
         * @see org.springframework.expression.ParserContext#isTemplate()
         */
        public boolean isTemplate() {
            return true;
        }

        /* (non-Javadoc)
         * @see org.springframework.expression.ParserContext#getExpressionPrefix()
         */
        public String getExpressionPrefix() {
            return "{";
        }

        /* (non-Javadoc)
         * @see org.springframework.expression.ParserContext#getExpressionSuffix()
         */
        public String getExpressionSuffix() {
            return "}";
        }

    }

}
