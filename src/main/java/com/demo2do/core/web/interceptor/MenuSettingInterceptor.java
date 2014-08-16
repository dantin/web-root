package com.demo2do.core.web.interceptor;

import com.demo2do.core.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.expression.MapAccessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Determine system menu display
 *
 * @author Downpour
 */
public class MenuSettingInterceptor extends HandlerInterceptorAdapter {

    private static final Log logger = LogFactory.getLog(MenuSettingInterceptor.class);

    private ExpressionParser parser = new SpelExpressionParser();

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (logger.isDebugEnabled()) {
            logger.debug("MenuInterceptor - To determine system menu display .. ");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Select Menu and Submenu when the authentication passed
        if (authentication != null && authentication.isAuthenticated()) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // Find menu annotation on class level
            MenuSetting menuSetting = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), MenuSetting.class);

            // Only handle those controllers with menu annotation
            if (menuSetting != null && !StringUtils.isTempateString(menuSetting.value())) {
                request.setAttribute("activeMenu", menuSetting.value());
            }

            // Find menu annotation on method level
            menuSetting = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), MenuSetting.class);

            // Only handle those methods with menu annotation
            if (menuSetting != null && !StringUtils.isTempateString(menuSetting.value())) {
                request.setAttribute("activeSubmenu", menuSetting.value());
            }
        }

        return super.preHandle(request, response, handler);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // Find menu annotation on class level
            MenuSetting menuSetting = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), MenuSetting.class);

            if (menuSetting != null) {

                String value = menuSetting.value();

                // parse expression when necessary
                if (StringUtils.isTempateString(value)) {

                    // create StandardEvaluationContext with MapAccessor
                    StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext(modelAndView.getModel());
                    standardEvaluationContext.addPropertyAccessor(new MapAccessor());

                    value = (String) parser.parseExpression(value, new RedirectMessageInterceptor.InfoMessageTemplateParserContext())
                            .getValue(standardEvaluationContext);

                    request.setAttribute("activeSubmenu", value);

                }

            }


        }

        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * @author David
     */
    public static class MenuSettingTemplateParserContext implements ParserContext {

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
