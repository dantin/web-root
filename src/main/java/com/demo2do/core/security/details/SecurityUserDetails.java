package com.demo2do.core.security.details;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Extended UserDetails interface
 *
 * @author David
 */
public interface SecurityUserDetails extends UserDetails {

    public boolean hasAnyPrincipalRole(String... roles);

    public boolean hasResource(String type, String key);

}
