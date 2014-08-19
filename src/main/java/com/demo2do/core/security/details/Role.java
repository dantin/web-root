package com.demo2do.core.security.details;

/**
 * Role entity
 *
 * @author David
 */
public class Role {

    private String name;

    private String alias;

    private String description;

    /**
     * Default constructor
     */
    public Role() {

    }

    /**
     * Full constructor
     *
     * @param name        the name to set
     * @param alias       the alias to set
     * @param description the description to set
     */
    public Role(String name, String alias, String description) {
        this.name = name;
        this.alias = alias;
        this.description = description;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
