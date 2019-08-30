package com.mountain.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mountain.springdemo.entity.Mountain;

@Repository("mountainDAO")
public class MountainDAOImpl implements MountainDAO {
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Mountain> getAllMountains() {
		Session session = this.sessionFactory.getCurrentSession();
		try {			
			session.beginTransaction();

			Query query=session.createQuery("from Mountain", Mountain.class);
			List<Mountain> mountains = query.list();

			session.close();

			return mountains;
			
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void saveOrUpdateMountain(Mountain theMountain) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			// get current hibernate session			
			session.beginTransaction();
			
			// save/upate the Mountain ... finally LOL
			session.saveOrUpdate(theMountain);
			
			session.getTransaction().commit();
			session.close();
			
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<Mountain> getMountains(String keyword) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			// get the current hibernate session		
			session.beginTransaction();
			
			Query query =session.createQuery("from Mountain "
					+ "where name like '%"+keyword+"%' "
					+ "or location like '%"+keyword+"%' "
					+ "or type like '%"+keyword+"%'", Mountain.class);
			
			List<Mountain> mountains = query.list();
						
			session.close();
			
			return mountains;
			
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void deleteMountain(Mountain theMountain) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			// get the current hibernate session			
			session.beginTransaction();
			
			// delete object with primary key
			session.delete(theMountain);

			session.getTransaction().commit();
			session.close();
			
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e.getMessage());
		}
	}

}
