package com.demo2do.core.cache.web.filter;

import com.demo2do.core.cache.CacheAccessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Filter to handler cache accessor in all JSP page
 *
 * @author David
 */
public class CacheAccessorRequestFilter implements Filter {

    private static final String URL_SUFFIX = ".jsp";

    private CacheAccessor cacheAccessor;

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        this.cacheAccessor = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getBean(CacheAccessor.class);
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (httpServletRequest.getRequestURI().endsWith(URL_SUFFIX)) {
            httpServletRequest = new CacheAccessorRequestWrapper(httpServletRequest, cacheAccessor);
        }

        chain.doFilter(httpServletRequest, response);
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
	 */
    public void destroy() {
    }

    /**
     * Wrapper of HttpServletRequest which combined Cache Accessor
     *
     * @author David
     */
    private static class CacheAccessorRequestWrapper extends HttpServletRequestWrapper {

        private static final Log logger = LogFactory.getLog(CacheAccessorRequestWrapper.class);

        private CacheAccessor cacheAccessor;

        /**
         * Constructor
         * @param request the HTTP servlet request
         * @param cacheAccessor the cache accessor
         */
        public CacheAccessorRequestWrapper(HttpServletRequest request, CacheAccessor cacheAccessor) {
            super(request);
            this.cacheAccessor = cacheAccessor;
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * javax.servlet.ServletRequestWrapper#getAttribute(java.lang.String)
         */
        public Object getAttribute(String name) {

            if (cacheAccessor.contains(name)) {

                if (logger.isDebugEnabled()) {
                    logger.debug("CacheAccessor contains key .. " + name + ", will return from Cache.");
                }

                return cacheAccessor.evaluate(name);
            }

            return super.getAttribute(name);
        }

    }
}
