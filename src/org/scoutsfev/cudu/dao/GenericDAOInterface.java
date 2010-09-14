package org.scoutsfev.cudu.dao;

import java.util.List;

public interface GenericDAOInterface<T, KeyType> {

	    public T loadHB(KeyType id);
	    
	    public T find(KeyType id);
	    
	    public void update(T object);

	    public void save(T object);

	    public void delete(T object);
	    
	    public void deleteById(KeyType id);

	    public List<T> getList();
	    
	    public void deleteAll();
	    
	    public int count();

	    public T merge(T entity);
}
