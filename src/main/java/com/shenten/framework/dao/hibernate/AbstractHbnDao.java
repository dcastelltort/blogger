/* 
 * 
 */
package com.shenten.framework.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.shenten.blogger.article.web.ArticleController;
import com.shenten.framework.dao.Dao;

/**
 * @author 
 */
public abstract class AbstractHbnDao<T extends Object> implements Dao<T> {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractHbnDao.class);
	
	@Inject private SessionFactory sessionFactory;
	private Class<T> domainClass;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
	    if (domainClass == null) {
	    	ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
	        this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
	    }
	    return domainClass;
	}
	
	private String getDomainClassName() { 
		return getDomainClass().getName(); 
	}
	
	@Override
	public void create(T t) {
		
		// If there's a setDateCreated() method, then set the date.
		Method method = ReflectionUtils.findMethod(
				getDomainClass(), "setDateCreated", new Class[] { Date.class });
		if (method != null) {
			try {
				method.invoke(t, new Date());
			} catch (Exception e) {
				// Ignore any exception here; simply abort the setDate() attempt
				LOG.error("uncatched invoke exception", e);
			}
		}
		
		getSession().save(t);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return (T) getSession().get(getDomainClass(), id);
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public T load(Serializable id) {
		return (T) getSession().load(getDomainClass(), id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getSession()
			.createQuery("from " + getDomainClassName())
			.list();
	}
	

	@Override
	public void update(T t) { 
		getSession().update(t); 
	}
	

	@Override
	public void delete(T t) { 
		getSession().delete(t); 
	}
	

	@Override
	public void deleteById(Serializable id) { 
		delete(load(id)); 
	}
	

	@Override
	public void deleteAll() {
		getSession()
			.createQuery("delete " + getDomainClassName())
			.executeUpdate();
	}
	

	@Override
	public long count() {
		return (Long) getSession()
			.createQuery("select count(*) from " + getDomainClassName())
			.uniqueResult();
	}
	
	@Override
	public boolean exists(Serializable id) { 
		return get(id) != null; 
	}
}
