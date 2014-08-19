package com.demo2do.core.persistence;

import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic DAO Support
 *
 * @author David
 */
public class GenericDaoSupport {

    private SessionFactory sessionFactory;

    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Get current session if exists, otherwise open a new session
     *
     * @return session
     */
    private Session getCurrentSession() {
        try {
            return this.sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return this.sessionFactory.openSession();
        }
    }

    /**
     * @return the jdbcTemplate
     */
    private NamedParameterJdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    /**
     * Save entity using current session
     *
     * @param entity input entity
     * @return Serializable
     */
    public Serializable save(Object entity) {
        return this.getCurrentSession().save(entity);
    }

    /**
     * Update entity using current session
     *
     * @param entity input entity
     */
    public void update(Object entity) {
        this.getCurrentSession().update(entity);
    }

    /**
     * Persist entity
     *
     * @param entity input entity
     */
    public void persist(Object entity) {
        this.getCurrentSession().persist(entity);
    }

    /**
     * Save or update entity using current session
     *
     * @param entity input entity
     */
    public void saveOrUpdate(Object entity) {
        this.getCurrentSession().saveOrUpdate(entity);
    }

    /**
     * Delete entity using current session
     *
     * @param entity input entity
     */
    public void delete(Object entity) {
        this.getCurrentSession().delete(entity);
    }

    /**
     * Delete entity according to persistentClass and primary key using current
     * session
     *
     * @param <T>             the type
     * @param persistentClass the class
     * @param id              the
     */
    public <T> void delete(Class<T> persistentClass, Serializable id) {
        delete(load(persistentClass, id));
    }

    /**
     * Load entity according to persistentClass and primary key using current
     * session
     *
     * @param <T>             the type
     * @param persistentClass the class
     * @param id              the id
     * @return target entity
     */
    @SuppressWarnings("unchecked")
    public <T> T load(Class<T> persistentClass, Serializable id) {
        return (T) this.getCurrentSession().load(persistentClass, id);
    }

    /**
     * Load all the entities according to persistentClass using current session
     *
     * @param <T>             the type
     * @param persistentClass the class
     * @return target entities
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> loadAll(Class<T> persistentClass) {
        Criteria criteria = this.getCurrentSession().createCriteria(persistentClass);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setCacheable(true);
        return criteria.list();
    }

    /**
     * Get entity according to persistentClass and primary key using current
     * session
     *
     * @param <T>             the type
     * @param persistentClass the class
     * @param id              the id
     * @return the entity referenced by id
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> persistentClass, Serializable id) {
        return (T) this.getCurrentSession().get(persistentClass, id);
    }

    /**
     * Search by hibernate for int result, using Map basic parameters. Mostly
     * used for HQL like: 'SELECT count(*) ...'
     *
     * @param sentence   HQL sentence
     * @param parameters parameters map
     * @return result size
     */
    @SuppressWarnings("rawtypes")
    public int searchForInt(final String sentence, final Map<String, Object> parameters) {

        Query query = this.getCurrentSession().createQuery(sentence);
        query.setProperties(parameters);

        List result = query.list();

        // deal with the condition when hql contains group by
        return result.size() > 1 ? result.size() : ((Long) result.get(0)).intValue();
    }

    /**
     * Search by hibernate for count, adding prefix: 'SELECT count(*) ...'
     *
     * @param sentence   query sentence
     * @param parameters parameters map
     * @return result size
     */
    @SuppressWarnings("rawtypes")
    public int count(final String sentence, final Map<String, Object> parameters) {
        Query query = this.getCurrentSession().createQuery(this.getHQLCountSentence(sentence));
        query.setProperties(parameters);

        List result = query.list();

        // deal with the condition when hql contains group by
        return result.size() > 1 ? result.size() : ((Long) result.get(0)).intValue();

    }

    /**
     * Generate HQL Count sentence
     *
     * @param sentence query sentence
     * @return HQL count sentence
     */
    private String getHQLCountSentence(String sentence) {
        StringBuffer sb = new StringBuffer("SELECT count(*) ");
        String tempSentence = StringUtils.trim(sentence).toUpperCase();
        if (tempSentence.startsWith("FROM")) {
            return sb.append(sentence).toString();
        } else {
            int index = tempSentence.indexOf(" FROM ");
            return sb.append(sentence.substring(index)).toString();
        }
    }

    /**
     * Search by hibernate for list result, using single parameter
     *
     * @param sentence  query sentence
     * @param key       parameter's key
     * @param parameter parameter's value
     * @return result
     */
    @SuppressWarnings({"rawtypes", "serial"})
    public List searchForList(final String sentence, final String key, final Object parameter) {
        return searchForList(sentence, new HashMap<String, Object>() {{
            put(key, parameter);
        }});
    }

    /**
     * Search by hibernate for list result, using single parameter and query cache
     *
     * @param sentence  query sentence
     * @param key       parameter's key
     * @param parameter parameter's value
     * @return cached result
     */
    @SuppressWarnings({"rawtypes", "serial"})
    public List searchForCacheableList(final String sentence, final String key, final Object parameter) {
        return searchForCacheableList(sentence, new HashMap<String, Object>() {{
            put(key, parameter);
        }});
    }

    /**
     * Search by hibernate for list result
     *
     * @param sentence query sentence
     * @return result
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List searchForList(final String sentence) {
        return searchForList(sentence, Collections.EMPTY_MAP);
    }

    /**
     * Search by hibernate for list result, using Map as basic parameter.
     *
     * @param sentence   query sentence
     * @param parameters parameters map
     * @return result
     */
    @SuppressWarnings({"rawtypes"})
    public List searchForList(final String sentence, final Map<String, Object> parameters) {
        Query query = this.getCurrentSession().createQuery(sentence);
        query.setProperties(parameters);
        return query.list();
    }

    /**
     * Search by hibernate for list result, using bean as basic parameter
     *
     * @param sentence query sentence
     * @param bean     the target bean
     * @return result
     */
    @SuppressWarnings("rawtypes")
    public List searchForList(final String sentence, final Object bean) {
        Query query = this.getCurrentSession().createQuery(sentence);
        query.setProperties(bean);
        return query.list();
    }

    /**
     * Search by hibernate for list result, using Map basic parameter and query cache.
     *
     * @param sentence   query sentence
     * @param parameters parameters map
     * @return result
     */
    @SuppressWarnings({"rawtypes"})
    public List searchForCacheableList(final String sentence, final Map<String, Object> parameters) {
        Query query = this.getCurrentSession().createQuery(sentence);
        query.setProperties(parameters);
        query.setCacheable(true);
        return query.list();
    }

    /**
     * Search by hibernate for list result from beginIndex to the number of maxResult
     *
     * @param sentence   query sentence
     * @param beginIndex begin index
     * @param maxResult  size of the result set
     * @return result
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List searchForList(final String sentence, final int beginIndex, final int maxResult) {
        return searchForList(sentence, Collections.EMPTY_MAP, beginIndex, maxResult);
    }

    /**
     * Search by hibernate for list result from beginIndex to the number of
     * maxResult, using Map as basic parameter.
     *
     * @param sentence   query sentence
     * @param parameters parameters map
     * @param beginIndex begin index
     * @param maxResult  size of the result set
     * @return result
     */
    @SuppressWarnings({"rawtypes"})
    public List searchForList(final String sentence, final Map<String, Object> parameters, final int beginIndex, final int maxResult) {
        Query query = this.getCurrentSession().createQuery(sentence);
        query.setFirstResult(beginIndex);
        query.setMaxResults(maxResult);
        query.setProperties(parameters);
        return query.list();
    }

    /**
     * Search by hibernate for list result, using single parameter
     *
     * @param sentence  query sentence
     * @param key       parameter's key
     * @param parameter parameter's value
     * @return result
     */
    @SuppressWarnings({"rawtypes", "serial"})
    public List searchForList(final String sentence, final String key, final Object parameter, final int beginIndex, final int maxResult) {
        return searchForList(sentence, new HashMap<String, Object>() {{
            put(key, parameter);
        }}, beginIndex, maxResult);
    }

    /**
     * Execute HQL
     *
     * @param sentence  query sentence
     * @param key       parameter's key
     * @param parameter parameter's value
     * @return the number of records affected
     */
    @SuppressWarnings("serial")
    public int executeHQL(final String sentence, final String key, final Object parameter) {
        return this.executeHQL(sentence, new HashMap<String, Object>() {{
            put(key, parameter);
        }});
    }

    /**
     * Execute HQL
     *
     * @param sentence   query sentence
     * @param parameters parameters map
     */
    public int executeHQL(final String sentence, final Map<String, Object> parameters) {
        Query query = this.getCurrentSession().createQuery(sentence);
        query.setProperties(parameters);
        return query.executeUpdate();
    }

    /**
     * Query by JDBC for int result, using Map as basic parameter.
     *
     * @param sentence   query sentence
     * @param parameters parameters map
     * @return the size of the result set
     */
    public int queryForInt(String sentence, Map<String, Object> parameters) {
        Number number = getJdbcTemplate().queryForObject(sentence, parameters, Integer.class);
        return (number != null ? number.intValue() : 0);
    }

    /**
     * Query by JDBC for int result, using single parameter.
     *
     * @param sentence  query sentence
     * @param key       parameter's key
     * @param parameter parameter's value
     * @return the number of records affected
     */
    @SuppressWarnings("serial")
    public int queryForInt(String sentence, final String key, final Object parameter) {
        Number number = getJdbcTemplate().queryForObject(sentence, new HashMap<String, Object>() {{
            put(key, parameter);
        }}, Integer.class);
        return (number != null ? number.intValue() : 0);
    }


    /**
     * Query by JDBC for int result, using a typical JavaBean as basic
     * parameters.
     *
     * @param sentence   query sentence
     * @param properties parameter properties
     * @return result
     */
    public int queryForInt(String sentence, Object properties) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(properties);
        Number number = getJdbcTemplate().queryForObject(sentence, namedParameters, Integer.class);
        return (number != null ? number.intValue() : 0);
    }

    /**
     * Query by JDBC for List result, using Map as basic parameter.
     *
     * @param sentence   query sentence
     * @param parameters parameters map
     * @return result
     */
    public List<Map<String, Object>> queryForList(String sentence, Map<String, Object> parameters) {
        return getJdbcTemplate().queryForList(sentence, parameters);
    }

    /**
     * Query by JDBC for List result, using single parameter.
     *
     * @param sentence  query sentence
     * @param key       parameter's key
     * @param parameter parameter's value
     * @return result
     */
    @SuppressWarnings("serial")
    public List<Map<String, Object>> queryForList(String sentence, final String key, final Object parameter) {
        return getJdbcTemplate().queryForList(sentence, new HashMap<String, Object>() {{
            put(key, parameter);
        }});
    }

    /**
     * Query by JDBC for List result, using a typical JavaBean as basic
     * parameters.
     *
     * @param sentence   query sentence
     * @param properties parameter properties
     * @return result
     */
    public List<Map<String, Object>> queryForList(String sentence, Object properties) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(properties);
        return getJdbcTemplate().queryForList(sentence, namedParameters);
    }

    /**
     * Query by JDBC for list of resultClass as result, using Map as basic parameter,
     *
     * @param <T>         the type
     * @param sentence    query sentence
     * @param parameters  parameters map
     * @param resultClass the persistent class
     * @return result
     */
    public <T> List<T> queryForList(String sentence, Map<String, Object> parameters, Class<T> resultClass) {
        return getJdbcTemplate().query(sentence, parameters, ParameterizedBeanPropertyRowMapper.newInstance(resultClass));
    }

    /**
     * Query by JDBC for list of resultClass (single column result) as result, using Map as basic parameter,
     *
     * @param <T>         the type
     * @param sentence    query sentence
     * @param parameters  parameters map
     * @param resultClass the persistent class
     * @return result
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List<T> queryForSingleColumnList(String sentence, Map<String, Object> parameters, Class<T> resultClass) {
        return getJdbcTemplate().query(sentence, parameters, new SingleColumnRowMapper(resultClass));
    }

    /**
     * Query by JDBC for list of resultClass as result, using single parameter
     *
     * @param <T>         the type
     * @param sentence    query sentence
     * @param key         parameter's key
     * @param parameter   parameter's value
     * @param resultClass the persistent class
     * @return result
     */
    @SuppressWarnings("serial")
    public <T> List<T> queryForList(String sentence, final String key, final Object parameter, Class<T> resultClass) {
        return getJdbcTemplate().query(sentence, new HashMap<String, Object>() {{
            put(key, parameter);
        }}, ParameterizedBeanPropertyRowMapper.newInstance(resultClass));
    }

    /**
     * Query by JDBC for list of resultClass (single column result) as result, using single parameter
     *
     * @param <T>         the type
     * @param sentence    query sentence
     * @param key         parameter's key
     * @param parameter   parameter's value
     * @param resultClass the persistent class
     * @return result
     */
    @SuppressWarnings({"serial", "unchecked", "rawtypes"})
    public <T> List<T> queryForSingleColumnList(String sentence, final String key, final Object parameter, Class<T> resultClass) {
        return getJdbcTemplate().query(sentence, new HashMap<String, Object>() {{
            put(key, parameter);
        }}, new SingleColumnRowMapper(resultClass));
    }

    /**
     * Query by JDBC for list result, using a typical JavaBean as parameter.
     *
     * @param <T>         the type
     * @param sentence    query sentence
     * @param properties  parameter properties
     * @param resultClass the persistent class
     * @return result
     */
    public <T> List<T> queryForList(String sentence, Object properties, Class<T> resultClass) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(properties);
        return getJdbcTemplate().query(sentence, namedParameters, ParameterizedBeanPropertyRowMapper.newInstance(resultClass));
    }

    /**
     * Execute SQL according to parameters
     *
     * @param sql       HQL sentence
     * @param key       parameter's key
     * @param parameter parameter's value
     */
    @SuppressWarnings("serial")
    public void executeSQL(String sql, final String key, final Object parameter) {
        getJdbcTemplate().update(sql, new HashMap<String, Object>() {{
            put(key, parameter);
        }});
    }

    /**
     * Execute SQL according to parameters
     *
     * @param sql        HQL sentence
     * @param parameters parameters map
     */
    public void executeSQL(String sql, Map<String, Object> parameters) {
        getJdbcTemplate().update(sql, parameters);
    }

}
