package com.demo2do.core.web.resolver;

import com.demo2do.core.persistence.GenericDaoSupport;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import javax.servlet.ServletRequest;

/**
 * Resolver for Preloading Argument
 *
 * @author David
 */
public class PreloadingArgumentResolver extends ModelAttributeMethodProcessor {

    private static final String DEFAULT_PRIMARY_KEY = "id";

    private GenericDaoSupport genericDaoSupport;

    /**
     * @param genericDaoSupport the genericDaoSupport to set
     */
    public void setGenericDaoSupport(GenericDaoSupport genericDaoSupport) {
        this.genericDaoSupport = genericDaoSupport;
    }

    /**
     * Extends constructor
     *
     * @param annotationNotRequired the annotationNotRequired to initialized
     */
    public PreloadingArgumentResolver(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.web.method.annotation.ModelAttributeMethodProcessor#createAttribute(java.lang.String, org.springframework.core.MethodParameter, org.springframework.web.bind.support.WebDataBinderFactory, org.springframework.web.context.request.NativeWebRequest)
     */
    protected Object createAttribute(String attributeName, MethodParameter parameter, WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {
        Preloading preloading = parameter.getParameterAnnotation(Preloading.class);
        Class<?> persistentClass = parameter.getParameterType();
        String primaryKey = StringUtils.isEmpty(preloading.value()) ? request.getParameter(DEFAULT_PRIMARY_KEY) : request.getParameter(preloading.value());

        try {
            return genericDaoSupport.load(persistentClass, new Long(primaryKey));
        } catch (Exception e) {
            return super.createAttribute(attributeName, parameter, binderFactory, request);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.web.method.annotation.ModelAttributeMethodProcessor#supportsParameter(org.springframework.core.MethodParameter)
     */
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Preloading.class);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Downcast {@link WebDataBinder} to {@link ServletRequestDataBinder} before
     * binding.
     *
     * @see ServletRequestDataBinderFactory
     */
    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
        servletBinder.bind(servletRequest);
    }
}
