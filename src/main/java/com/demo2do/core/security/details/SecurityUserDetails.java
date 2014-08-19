package com.demo2do.core.security.details;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Extended UserDetails interface
 *
 * @author David
 */
public interface SecurityUserDetails extends UserDetails {

    /**
     * Whether a principal has any of roles
     *
     * @param roles the role set
     * @return the result
     */
    public boolean hasAnyPrincipalRole(String... roles);

    /**
     * Whether a principle can access the resource
     *
     * @param type the type of resource
     * @param key  the key of the resource
     * @return the result
     */
    public boolean hasResource(String type, String key);

}
