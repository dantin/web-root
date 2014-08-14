package com.demo2do.core.security.expression;

import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

/**
 * Customized Web Security Expression Handler using PrincipalExpressionRoot
 *
 * @author David
 */
public class PrincipalWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {

    /* (non-Javadoc)
     * @see org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler#createSecurityExpressionRoot(org.springframework.security.core.Authentication, org.springframework.security.web.FilterInvocation)
	 */
    protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, FilterInvocation filterInvocation) {
        PrincipalExpressionRoot root = new PrincipalExpressionRoot(authentication, filterInvocation);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(new AuthenticationTrustResolverImpl());
        root.setRoleHierarchy(getRoleHierarchy());
        return root;
    }

}
