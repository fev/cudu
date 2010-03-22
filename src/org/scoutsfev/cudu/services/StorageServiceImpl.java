package org.scoutsfev.cudu.services;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class StorageServiceImpl<T> implements StorageService<T> {
	
	private final Class<T> persistentClass;
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public StorageServiceImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public <Q> T find(Q id) {
		return entityManager.find(this.persistentClass, id);
	}

	@Override
	@Transactional
	public T merge(T entity) {
		return this.entityManager.merge(entity);
	}
}
