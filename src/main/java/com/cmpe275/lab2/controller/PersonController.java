package com.cmpe275.lab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpe275.lab2.model.Address;
import com.cmpe275.lab2.model.Organization;
import com.cmpe275.lab2.model.Person;
import com.cmpe275.lab2.service.OrganizationService;
import com.cmpe275.lab2.service.PersonService;

/**
 * Controller for handling person related requests 
 */
@Controller
@RequestMapping(value="/person")
public class PersonController {

	@Autowired
	PersonService personService;

	@Autowired
	OrganizationService organizationService;
	
	
	/**
	 (2) Get a person
Path:person/{id}?format={json | xml | html} 
Method: GET

This returns a full person object with the given ID in the given format in its HTTP payload. 
All existing fields, including the optional organization and list of friends should be returned. 
The payload should contain the full organization object, if present.
The list of friends can be either (a) list of person IDs, or (b) list of “shallow” person objects that do not have their friends list populated. If you take option (b), you want to use techniques like lazy loading to avoid serializing the whole social network starting from the requested person in the returned payload.
If the person of the given user ID does not exist, the HTTP return code should be 404; otherwise, 200.
The format parameter is optional, and the value is case insensitive. If missing, JSON is assumed.

	 */
	@RequestMapping(method=RequestMethod.GET,value="{id}")
	@ResponseBody
	public ResponseEntity<Person> getPerson(@PathVariable long id,@RequestParam(required=false) String format){
		System.out.println(format);
		//TODO check for format and return accordingly
		
		

		Person person = personService.read(id);
		if(person==null)
			return new ResponseEntity<Person>(person, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<Person>(person, HttpStatus.OK);
	}
	
	
	/**
	(1) Create a person
Path: person?firstname=XX&lastname=YY&email=ZZ&description=UU&street=VV$...
Method: POST

This API creates a person object. 
For simplicity, all the person fields (firstname, lastname, email, street, city, organization, etc), except ID and friends, are passed in as query parameters. Only the firstname, lastname, and email are required. Anything else is optional.
Friends is not allowed to be passed in as a parameter.
The organization parameter, if present, must be the ID of an existing organization. 
The request returns the newly created person object in JSON in its HTTP payload, including all attributes. (Please note this differs from generally recommended practice of only returning the ID.)
If the request is invalid, e.g., missing required parameters, the HTTP status code should be 400; otherwise 200.

	 */
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Person> addUser(@ModelAttribute Person person,@ModelAttribute Address address, @ModelAttribute Organization organization){

		if(person.getEmail()==null || person.getFirstname()==null || person.getLastname()==null){
			person=null;
			return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
		}
		if(address!=null)
			person.setAddress(address);
		if(organization!=null){
			organization = organizationService.read(organization.getOrganization_id());
			if(organization==null){
				person=null;
				return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
			}else
				person.setOrganization(organization);
		}
		person = personService.create(person);
		return new ResponseEntity<Person>(person, HttpStatus.OK);	
	}
	
	
	/**
	(3) Update a person
Path: person/{id}?firstname=XX&lastname=YY&email=ZZ&description=UU&street=VV$...
Method: POST

This API updates a person object. 
For simplicity, all the person fields (firstname, lastname, email, street, city, organization, etc), except friends, should be passed in as query parameters. Required fields like email must be present. The object constructed from the parameters will completely replace the existing object in the server, except that it does not change the person’s list of friends.
Similar to the get method, the request returns the updated person object, including all attributes (first name, last name, email, friends, organization, etc), in JSON. If the person ID does not exist, 404 should be returned. If required parameters are missing, return 400 instead. Otherwise, return 200.
	 */
	@RequestMapping(method=RequestMethod.POST,value="{id}")
	@ResponseBody
	public ResponseEntity<Person> updatePerson(@PathVariable long id,@ModelAttribute Person person,@ModelAttribute Organization organization, @ModelAttribute Address address){

		person.setId(id);
		if(person.getEmail()==null || person.getFirstname()==null || person.getLastname()==null){
			person=null;
			return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
		}
		if(address!=null)
			person.setAddress(address);
		if(organization!=null){
			organization = organizationService.read(organization.getOrganization_id());
			if(organization==null){
				person=null;
				return new ResponseEntity<Person>(person, HttpStatus.BAD_REQUEST);
			}else
				person.setOrganization(organization);
		}
		person = personService.update(person);
		if(person==null)
			return new ResponseEntity<Person>(person, HttpStatus.NOT_FOUND);

		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}

	/**
	(4) Delete a person
URL: http://person/{id} 
Method: DELETE

This deletes the person object with the given ID. 
If the person with the given ID does not exist, return 404.
Otherwise, delete the person and remove any reference of this person from your persistence of friendship relations, and return HTTP status code 200 and the deleted person in JSON. 

	 */
	@RequestMapping(method=RequestMethod.DELETE,value="{id}")
	@ResponseBody
	public ResponseEntity<Person> deletePerson(@PathVariable long id){

		Person person = personService.delete(id);
		if(person==null)
			return new ResponseEntity<Person>(person, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<Person>(person, HttpStatus.OK);
	}

}