package com.demo2do.core.service.impl;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.service.GenericService;

import java.io.Serializable;
import java.util.List;

/**
 * Generic Service implementation
 *
 * @param <T> the type
 * @author David
 */
public abstract class GenericServiceImpl<T> implements GenericService<T> {

    protected GenericDaoSupport genericDaoSupport;

    /**
     * @param genericDaoSupport the genericDaoSupport to set
     */
    public void setGenericDaoSupport(GenericDaoSupport genericDaoSupport) {
        this.genericDaoSupport = genericDaoSupport;
    }

    /* (non-Javadoc)
     * @see com.demo2do.core.service.GenericService#loadAll(java.lang.Class)
     */
    public List<T> loadAll(Class<T> persistentClass) {
        return genericDaoSupport.loadAll(persistentClass);
    }

    /* (non-Javadoc)
     * @see com.demo2do.core.service.GenericService#load(java.lang.Class, java.io.Serializable)
     */
    public T load(Class<T> persistentClass, Serializable id) {
        return genericDaoSupport.load(persistentClass, id);
    }

    /* (non-Javadoc)
     * @see com.demo2do.core.service.GenericService#save(java.lang.Object)
     */
    public Serializable save(Object entity) {
        return genericDaoSupport.save(entity);
    }

    /* (non-Javadoc)
     * @see com.demo2do.core.service.GenericService#update(java.lang.Object)
     */
    public void update(Object entity) {
        genericDaoSupport.update(entity);
    }

    /* (non-Javadoc)
     * @see com.demo2do.core.service.GenericService#saveOrUpdate(java.lang.Object)
     */
    public void saveOrUpdate(Object entity) {
        genericDaoSupport.saveOrUpdate(entity);
    }

    /* (non-Javadoc)
     * @see com.demo2do.core.service.GenericService#delete(java.lang.Object)
     */
    public void delete(Object entity) {
        genericDaoSupport.delete(entity);
    }

    /* (non-Javadoc)
     * @see com.demo2do.core.service.GenericService#delete(java.lang.Class, java.io.Serializable)
     */
    public void delete(Class<T> persistentClass, Serializable id) {
        genericDaoSupport.delete(persistentClass, id);
    }

}
