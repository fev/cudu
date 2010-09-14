package org.scoutsfev.cudu.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

public abstract class GenericDAOHibernateObject<T extends Serializable, KeyType extends Serializable> {

	protected final Log logger = LogFactory.getLog(getClass());
	protected Class<T> domainClass = getDomainClass();

    /**
     * Method to return the class of the domain object
     */
    protected abstract Class<T> getDomainClass();
	
	protected HibernateTemplate template;

	public GenericDAOHibernateObject(SessionFactory sessionFactory) {
		template = new HibernateTemplate(sessionFactory);
	}
	
    public T loadHB(KeyType id) {
        return (T) template.load(domainClass, id);
    }
    
    public T find(KeyType id) {
        return (T) template.get(domainClass, id);
    }

    public void update(T t) {
    	template.update(t);
    }

    public void save(T t) {
    	template.save(t);
    }

    public void delete(T t) {
    	template.delete(t);
    }

    @SuppressWarnings("unchecked")
    public List<T> getList() {
        return (template.find("from " + domainClass.getName() + " x"));
    }

    public void deleteById(KeyType id) {
        Object obj = loadHB(id);
        template.delete(obj);
    }

    @SuppressWarnings("unused")
    public void deleteAll() {
        template.execute(new HibernateCallback<T>() {
            public T doInHibernate(Session session) throws HibernateException {
                String hqlDelete = "delete " + domainClass.getName();
				int deletedEntities = session.createQuery(hqlDelete).executeUpdate();
                return null;
            }
        });
    }

    @SuppressWarnings("rawtypes")
	public int count() {
        List list = template.find(
                "select count(*) from " + domainClass.getName() + " x");
        Integer count = (Integer) list.get(0);
        return count.intValue();
    }
    
    @Transactional
	public T merge(T entity) {
    	return this.template.merge(entity);
	}

}
