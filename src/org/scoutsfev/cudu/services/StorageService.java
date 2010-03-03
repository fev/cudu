package org.scoutsfev.cudu.services;

public interface StorageService<T> {
	public <Q> T find(Q id);
	public void merge(T entity);
}
