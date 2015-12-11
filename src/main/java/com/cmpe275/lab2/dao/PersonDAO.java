/**
 * 
 */
package com.cmpe275.lab2.dao;

import com.cmpe275.lab2.model.Organization;
import com.cmpe275.lab2.model.Person;

public interface PersonDAO {
	
	public Person create(Person person);
	public Person read(long id);
	public Person update(Person person);
	public Person delete(Person person);
	public Person getPersonByOrganization(Organization organization);

}
