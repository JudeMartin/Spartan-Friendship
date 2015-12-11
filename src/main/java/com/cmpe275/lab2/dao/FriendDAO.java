
package com.cmpe275.lab2.dao;

import org.springframework.stereotype.Repository;

import com.cmpe275.lab2.model.Person;


@Repository
public interface FriendDAO {
	
public void create(Person person);
public void delete(Person person);


}
