package com.demo2do.core.security.expression;

import com.demo2do.core.security.details.SecurityUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

/**
 * Customized Web Security Expression Root using SecurityUserDetails
 *
 * @author David
 */
public class PrincipalExpressionRoot extends WebSecurityExpressionRoot {

    /**
     * Constructor
     *
     * @param authentication   the input authentication
     * @param filterInvocation the input filterInvocation
     */
    public PrincipalExpressionRoot(Authentication authentication, FilterInvocation filterInvocation) {
        super(authentication, filterInvocation);
    }

    /**
     * Whether principal has any of specific roles
     *
     * @param roles the input roles
     * @return result
     */
    public boolean hasAnyPrincipalRole(String... roles) {
        Object principal = super.getPrincipal();
        if (principal != null && (principal instanceof SecurityUserDetails)) {
            return ((SecurityUserDetails) principal).hasAnyPrincipalRole(roles);
        }
        return false;
    }

    /**
     * Whether principal has specific type and key
     *
     * @param type the input type
     * @param key  the input key
     * @return result
     */
    public boolean hasResource(String type, String key) {
        Object principal = super.getPrincipal();
        if (principal != null && (principal instanceof SecurityUserDetails)) {
            return ((SecurityUserDetails) principal).hasResource(type, key);
        }
        return false;
    }

}
