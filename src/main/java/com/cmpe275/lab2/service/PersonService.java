package com.cmpe275.lab2.service;

import com.cmpe275.lab2.model.Person;

public interface PersonService {
	
	
	/**
	 * create a new person entry
	 * @return 
	 */
	public Person create(Person person);
	
	/**
	 * read person's details 
	 */
	public Person read(long id);
	
	
	/**
	 * update person's details
	 */
	public Person update(Person person);
	
	/**
	 * delete person entry
	 */
	public Person delete(long id);

}
