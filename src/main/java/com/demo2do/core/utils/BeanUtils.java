package com.demo2do.core.utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * BeanUtils simply encapsulate <class>org.apache.commons.beanutils.PropertyUtils</class> and <class>org.apache.commons.beanutils.BeanUtils</class>
 *
 * @author David
 */
public abstract class BeanUtils {

    /**
     * Copy properties using {@link org.apache.commons.beanutils.PropertyUtils#copyProperties(Object, Object)}
     *
     * @param dest destination bean
     * @param source source bean
     */
    public static void copyProperties(Object dest, Object source) {
        try {
            PropertyUtils.copyProperties(dest, source);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populate bean from properties using {@link org.apache.commons.beanutils.BeanUtils#populate(Object, Map)}
     *
     * @param bean bean
     * @param properties properties map
     */
    @SuppressWarnings({"rawtypes"})
    public static void populate(Object bean, Map properties) {
        try {
            org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Descibe bean using {@link org.apache.commons.beanutils.BeanUtils#describe(Object)}
     *
     * @param bean bean
     * @return a map of properties
     */
    @SuppressWarnings({"rawtypes"})
    public static Map describe(Object bean) {
        Map result = new HashMap();
        try {
            result = org.apache.commons.beanutils.BeanUtils.describe(bean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Copy bean using {@link org.apache.commons.beanutils.BeanUtils#copyProperties(Object, Object)}
     *
     * @param dest destination bean
     * @param source source bean
     */
    public static void copyBean(Object dest, Object source) {
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(dest, source);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * check id of bean
     *
     * @param bean input bean
     * @param property target property
     * @return result
     */
    public static boolean isPropertyNotEmpty(Object bean, String property) {
        return isPropertyNotEmpty(bean, property, "id");
    }

    /**
     * check specified nested property of bean
     *
     * @param bean input bean
     * @param property target property
     * @return result
     */
    public static boolean isPropertyNotEmpty(Object bean, String property, String nestedCheckProperty) {
        if (bean == null) {
            return false;
        }

        try {
            Object target = PropertyUtils.getProperty(bean, property);
            if (target != null) {
                Object id = PropertyUtils.getProperty(target, nestedCheckProperty);
                if (id != null && id instanceof String && org.apache.commons.lang.StringUtils.isNotBlank((String) id)) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Check if array is null or empty
     *
     * @param array input array
     * @return result
     */
    public static boolean isArrayEmpty(String[] array) {
        boolean result = true;
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (org.apache.commons.lang.StringUtils.isNotBlank(array[i]))
                    return false;
            }
        }
        return result;
    }

    /**
     * Check if array has single value
     *
     * @param array input array
     * @return result
     */
    public static boolean isArrayContainsSingleValue(String[] array) {
        if (array == null)
            return false;
        return array.length == 1;
    }

    /**
     * Extract the properties of a bean as a List
     *
     * @param bean input bean
     * @param property target property
     * @return list of target property
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List extractPropertyList(Collection bean, String property) {
        List result = new ArrayList();
        for (Iterator iter = bean.iterator(); iter.hasNext(); ) {
            try {
                result.add(PropertyUtils.getProperty(iter.next(), property));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
