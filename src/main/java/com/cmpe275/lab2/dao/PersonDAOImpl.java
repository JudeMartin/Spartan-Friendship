/**
 * 
 */
package com.cmpe275.lab2.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cmpe275.lab2.model.Organization;
import com.cmpe275.lab2.model.Person;

@Repository
public class PersonDAOImpl implements PersonDAO {

	
	@Autowired
	SessionFactory ses;
// create new person. 
	public Person create(Person person) {
		
		Session session = ses.openSession();
		Transaction tx =  session.beginTransaction();
		try{
			session.save(person);
			tx.commit();
		}
		catch(HibernateException h){
			tx.rollback();
		}finally{
			session.close();
		}
		return person;
	}
	
	// Retrieve the person details with id 
	public Person read(long id) {
		Session session = ses.openSession();
		Transaction tx =  session.beginTransaction();
		Person person = null;
		try{
			person = (Person)session.get(Person.class,id);
			tx.commit();
		}
		catch(HibernateException h){
			tx.rollback();
		}finally{
			session.close();
		}
		return person;
	}
	
	/**
	 * Update person DAO implementation
	 */
	public Person update(Person person) {
		Session session = ses.openSession();
		Transaction tx =  session.beginTransaction();
		try{
			session.update(person);
			tx.commit();
		}
		catch(Exception h){
			tx.rollback();
			person=null;
		}finally{
			session.close();
		}
		return person;
	}
	
	/**
	 * Delete person DAO implementation
	 */
	public Person delete(Person person) {
		Session session = ses.openSession();
		Transaction tx =  session.beginTransaction();
		try{
			session.delete(person);
			tx.commit();
		}
		catch(HibernateException h){
			tx.rollback();
		}finally{
			session.close();
		}
		return person;
	}
	

	public Person getPersonByOrganization(Organization organization) {
		Session session = ses.openSession();
		Transaction tx =  session.beginTransaction();
		List<Person> persons;
		Person person=null;
		try{
			String hql = "FROM com.cmpe275.lab2.model.Person as person WHERE person.organization = :organization";
			Query query = session.createQuery(hql);
			query.setEntity("organization", organization);
			query.setMaxResults(1);			
			persons = query.list();
			
			if(persons.size()!=0)
				person = persons.get(0);
			
			tx.commit();
		}
		catch(HibernateException h){
			tx.rollback();
		}finally{
			session.close();
		}
		return person;
	}
}