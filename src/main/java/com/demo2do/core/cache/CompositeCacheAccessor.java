package com.demo2do.core.cache;

import org.springframework.cglib.core.ReflectUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * Cache Accessor for JavaBean
 *
 * @author David
 */
public class CompositeCacheAccessor implements CacheAccessor {

    private ExpressionParser expressionParser = new SpelExpressionParser();

    private Set<String> keys;

    private EvaluationContext evaluationContext;

    private String key;

    /**
     * Generate evaluationContext and available keys by setting cacheRoot
     *
     * @param cacheRoot The cache root
     */
    public void setCacheRoot(Object cacheRoot) {
        this.evaluationContext = new StandardEvaluationContext(cacheRoot);
        this.keys = this.generateKeys(cacheRoot.getClass());
    }

    /**
     * populate keys according to the getter functions from cacheRootClass
     *
     * @return The cache root class
     */
    protected Set<String> generateKeys(Class<?> cacheRootClass) {
        PropertyDescriptor[] propertyDescriptors = ReflectUtils.getBeanGetters(cacheRootClass);
        Set<String> keys = new HashSet<String>(propertyDescriptors.length);

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            keys.add(propertyDescriptor.getName());
        }

        return keys;
    }

    /* (non-Javadoc)
     * @see com.demo2do.core.cache.CacheAccessor#contains(java.lang.String)
	 */
    public boolean contains(String key) {
        return keys.contains(key);
    }

    /* (non-Javadoc)
     * @see com.demo2do.core.cache.CacheAccessor#evaluate(java.lang.String)
	 */
    public Object evaluate(String key) {
        try {
            Expression expression = this.expressionParser.parseExpression(key);
            return expression.getValue(evaluationContext);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
