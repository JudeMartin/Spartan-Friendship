package com.cmpe275.lab2.service.impl;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe275.lab2.dao.FriendDAO;
import com.cmpe275.lab2.dao.PersonDAO;
import com.cmpe275.lab2.model.Person;
import com.cmpe275.lab2.service.FriendService;

@Service
@Transactional
public  class FriendServiceImpl implements FriendService {
	
	@Autowired
	FriendDAO fdao;
	@Autowired
	PersonDAO pdao;


	@Override
	public String create(long id1, long id2) {
		// TODO Auto-generated method stub
		
	Person p1 = pdao.read(id1);
	Person p2 = pdao.read(id2);
	if(p1==null || p2==null)
		return "404 Bad Request : Id is not available";

	boolean frienship = false;

	Iterator iterator = p1.getFriends().iterator();
	while(iterator.hasNext()){	
		Person p = (Person) iterator.next();
		if(p.getId()==id2){
			frienship=true;
			break;
		}		
	}

	if(!frienship){
		iterator = p2.getFriends().iterator();
		while(iterator.hasNext()){	
			Person p = (Person) iterator.next();
			if(p.getId()==id1){
				frienship=true;
				break;
			}		
		}
	}

	if(frienship)
	{
		return "200#"+p1.getFirstname()+" "+p1.getLastname()+" and "+p2.getFirstname()+" "+p2.getLastname()+" are already friends";
	}
	p1.getFriends().add(p2);
	fdao.create(p1);
	return "200  status"+p1.getFirstname()+" "+p1.getLastname()+" and "+p2.getFirstname()+" "+p2.getLastname()+" became friends ow ";

}

/**
 * Service method for removing friends
 */
public String delete(long id1, long id2) {

	Person p1 = pdao.read(id1);
	Person p2 = pdao.read(id2);
	if(p1==null || p2==null)
		return "404 "+" Person id does not exist ";
	
	Person person;
	boolean frienship = false;

	Iterator iterator = p1.getFriends().iterator();

	while(iterator.hasNext()){	
		person = (Person) iterator.next();
		if(person.getId()==id2){
			iterator.remove();
			frienship = true;
			break;
		}
	}
	person=p1;
	
	if(!frienship){			
		iterator = p2.getFriends().iterator();

		while(iterator.hasNext()){
			person = (Person) iterator.next();
			if(person.getId()==id1){
				iterator.remove();
				frienship = true;
				break;
			}		
		}
		person=p2;
	}
	
	if(!frienship)
		return "404"+p1.getFirstname()+" "+p1.getLastname()+" and "+p2.getFirstname()+" "+p2.getLastname()+" are  not friends";
		
	fdao.delete(person);
	return "200 Error"+p1.getFirstname()+" "+p1.getLastname()+" and "+p2.getFirstname()+" "+p2.getLastname()+" friendship is deleted";
}



}