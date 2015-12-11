/**
 * 
 */
package com.cmpe275.lab2.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmpe275.lab2.model.Organization;

public class OrganizationDAOImpl implements OrganizationDAO {

	@Autowired
	SessionFactory ses;
	
	public Organization read(long id) {
		
		Session session = ses.openSession();
		Transaction tx = session.beginTransaction();
		Organization organization = null;
		
		try{
			organization = (Organization) session.get(Organization.class, id);
			tx.commit();
		}catch(HibernateException h){
			tx.rollback();
		}
		finally{
			session.close();
		}
		return organization;
	}

	
	
	public Organization create(Organization organization) {
		
		Session session = ses.openSession();
		Transaction tx = session.beginTransaction();
		try{
			session.save(organization);
			tx.commit();
		}catch(HibernateException e){
			tx.rollback();
		}finally{
			session.close();
		}
		return organization;
	}
	
	public Organization update(Organization organization) {
		
		Session session = ses.openSession();
		Transaction tx = session.beginTransaction();
		try{
			session.update(organization);
			tx.commit();
		}catch(HibernateException h){
			tx.rollback();
			organization=null;
		}finally{
			session.close();
		}
		return organization;
	}

	
	public Organization delete(Organization organization) {
		
		Session session = ses.openSession();
		Transaction tx = session.beginTransaction();
		try{
			session.delete(organization);
			tx.commit();
		}catch(HibernateException h){
			tx.rollback();
			organization=null;
		}finally{
			session.close();
		}
		return organization;
	}

}
