package com.demo2do.core.persistence;

import com.demo2do.core.utils.StringUtils;
import org.hibernate.AssertionFailure;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.internal.util.StringHelper;

import java.io.Serializable;

/**
 * Customized Name Strategy
 *
 * @author David
 */
public class SmartNamingStrategy implements NamingStrategy, Serializable {

    private static final String UNDERSCORE_SEPERATOR = "_";

    /**
     * A convenient singleton instance
     */
    public static final NamingStrategy INSTANCE = new SmartNamingStrategy();

    /* (non-Javadoc)
     * @see org.hibernate.cfg.NamingStrategy#columnName(java.lang.String)
     */
    public String columnName(String columnName) {
        return StringUtils.convertCamel(columnName, UNDERSCORE_SEPERATOR);
    }

    /* (non-Javadoc)
     * @see org.hibernate.cfg.NamingStrategy#tableName(java.lang.String)
     */
    public String tableName(String tableName) {
        return StringUtils.convertCamel(tableName, UNDERSCORE_SEPERATOR);
    }

    /* (non-Javadoc)
     * @see org.hibernate.cfg.NamingStrategy#collectionTableName(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
        return tableName(ownerEntityTable + UNDERSCORE_SEPERATOR + propertyToColumnName(propertyName));
    }

    /* (non-Javadoc)
     * @see org.hibernate.cfg.NamingStrategy#classToTableName(java.lang.String)
     */
    public String classToTableName(String className) {
        return StringUtils.convertCamel(StringHelper.unqualify(className), UNDERSCORE_SEPERATOR);
    }

    /* (non-Javadoc)
     * @see org.hibernate.cfg.NamingStrategy#propertyToColumnName(java.lang.String)
     */
    public String propertyToColumnName(String propertyName) {
        return StringUtils.convertCamel(StringHelper.unqualify(propertyName), UNDERSCORE_SEPERATOR);
    }

    /* (non-Javadoc)
     * @see org.hibernate.cfg.NamingStrategy#logicalColumnName(java.lang.String, java.lang.String)
     */
    public String logicalColumnName(String columnName, String propertyName) {
        return StringHelper.isNotEmpty(columnName) ? columnName : StringHelper.unqualify(propertyName);
    }

    /* (non-Javadoc)
     * @see org.hibernate.cfg.NamingStrategy#logicalCollectionTableName(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName) {
        if (tableName != null) {
            return tableName;
        } else {
            // use of a stringbuffer to workaround a JDK bug
            return new StringBuffer(ownerEntityTable)
                    .append(UNDERSCORE_SEPERATOR)
                    .append(associatedEntityTable != null ? associatedEntityTable : StringHelper.unqualify(propertyName))
                    .toString();
        }
    }

    /*
     * (non-Javadoc)
     * @see org.hibernate.cfg.NamingStrategy#logicalCollectionColumnName(java.lang.String, java.lang.String, java.lang.String)
     */
    public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
        return StringHelper.isNotEmpty(columnName) ? columnName : StringHelper.unqualify(propertyName) + UNDERSCORE_SEPERATOR + referencedColumn;
    }

    /* (non-Javadoc)
     * @see org.hibernate.cfg.NamingStrategy#foreignKeyColumnName(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
        String header = propertyName != null ? StringHelper.unqualify(propertyName) : propertyTableName;
        if (header == null) throw new AssertionFailure("NamingStrategy not properly filled");
        return columnName(header + UNDERSCORE_SEPERATOR + referencedColumnName);
    }

    /* (non-Javadoc)
     * @see org.hibernate.cfg.NamingStrategy#joinKeyColumnName(java.lang.String, java.lang.String)
     */
    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        return columnName(joinedColumn);
    }
}
