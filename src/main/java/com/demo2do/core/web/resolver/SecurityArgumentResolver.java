package com.demo2do.core.web.resolver;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Security argument resolver
 *
 * @author David
 */
public class SecurityArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Log logger = LogFactory.getLog(SecurityArgumentResolver.class);

    /* (non-Javadoc)
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
     */
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Secure.class);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
     */
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            Secure secure = parameter.getParameterAnnotation(Secure.class);

            // returns principal when there is no property in Secure annotation
            if (StringUtils.isEmpty(secure.property())) {

                Object principal = authentication.getPrincipal();
                if (principal != null && parameter.getParameterType().isAssignableFrom(principal.getClass())) {
                    return principal;
                }

            } else {

                // extract property using BeanWrapperImpl

                try {
                    BeanWrapperImpl wrapper = new BeanWrapperImpl(authentication.getPrincipal());
                    return wrapper.getPropertyValue(secure.property());
                } catch (BeansException e) {
                    logger.error("Error when extracting property[" + secure.property() + "] from Security Object[" + authentication.getPrincipal() + "]");
                    e.printStackTrace();
                }

            }

        }

        return null;
    }

}
