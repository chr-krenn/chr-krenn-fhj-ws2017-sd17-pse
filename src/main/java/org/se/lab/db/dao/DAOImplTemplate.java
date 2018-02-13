package org.se.lab.db.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public abstract class DAOImplTemplate<E> implements DAOTemplate<E> {

	@PersistenceContext
	protected EntityManager em;

	public DAOImplTemplate() {}

	public EntityManager getEntityManager() {
		return em;
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	protected abstract Class<E> getEntityClass();

	@Override
	public E insert(E entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public E update(E entity) {
		return em.merge(entity);
	}

	@Override
	public void delete(E entity) {
		em.remove(entity);
	}

	@Override
	public E findById(int id) {
		return em.find(getEntityClass(), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll() {
		final String hql = "SELECT u FROM " + getEntityClass().getName() + " AS u";
		return em.createQuery(hql).getResultList();
	}

}
