package com.cmpe275.lab2.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmpe275.lab2.model.Person;

public class FriendDAOImpl implements FriendDAO {


	
	@Autowired
	SessionFactory ses;
	
	public void create(Person person) {
		Session session = ses.openSession();
		Transaction tx =  session.beginTransaction();
		try{
			session.update(person);
			tx.commit();
		}
		catch(Exception h){
			tx.rollback();
		}finally{
			session.close();
		}
	}
	
	
	public void delete(Person person) {
		
		Session session = ses.openSession();
		Transaction tx =  session.beginTransaction();
		try{
			session.update(person);
			tx.commit();
		}
		catch(Exception h){
			tx.rollback();
		}finally{
			session.close();
		}
	}
}