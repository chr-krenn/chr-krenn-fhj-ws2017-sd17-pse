package org.se.lab.integration;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CarEJB {

	@PersistenceContext
	private EntityManager em;

	public Car saveCar(Car car) {
		em.persist(car);
		return car;
	}

	@SuppressWarnings("unchecked")
	public List<Car> findAllCars() {
		Query query = em.createQuery("SELECT b FROM Car b ORDER BY b.name ASC");
		List<Car> entries = query.getResultList();

		return entries == null ? new ArrayList<>() : entries;
	}

	public void deleteCar(Car car) {
		car = em.merge(car);
		em.remove(car);
	}
}
