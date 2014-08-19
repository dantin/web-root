package com.demo2do.core.service;

import java.io.Serializable;
import java.util.List;

/**
 * Generic Service interface
 *
 * @param <T> the type
 * @author David
 */
public interface GenericService<T> {

    public List<T> loadAll(Class<T> persistentClass);

    public T load(Class<T> persistentClass, Serializable id);

    public Serializable save(Object entity);

    public void saveOrUpdate(Object entity);

    public void update(Object entity);

    public void delete(Object entity);

    public void delete(Class<T> persistentClass, Serializable id);

}
