package com.cmpe275.lab2.controller;

import org.hibernate.HibernateException;
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

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cmpe275.lab2.model.Address;
import com.cmpe275.lab2.model.Organization;
import com.cmpe275.lab2.service.OrganizationService;

@Controller
@RequestMapping(value="/org")
public class OrganizationController {

	@Autowired
	OrganizationService organizationService;
	
	/**
	(6) Get a organization
Path:org/{id}?format={json | xml | html} 
Method: GET

This returns a full organization object with the given ID in the given format. 
All existing fields, including the optional ones should be returned. 
If the organization of the given user ID does not exist, the HTTP return code should be 404; otherwise, 200.
The format parameter is optional, and the value is case insensitive. If missing, JSON is assumed.

	 */
	@RequestMapping(method=RequestMethod.GET,value="{id}")
	@ResponseBody
	public ResponseEntity<Organization> getOrg(@PathVariable long id){
			
		Organization organization = organizationService.read(id);
		if(organization==null)
			return new ResponseEntity<Organization>(organization, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<Organization>(organization, HttpStatus.OK);
	}
	

	
	/**
(5) Create an organization
Path: org?name=XX&description=YY&street=ZZ&...
Method: POST

This API creates an organization object. 
For simplicity, all the fields (name, description, street, city, etc), except ID, are passed in as query parameters. Only name is required. 
The request returns the newly created organization object in JSON in its HTTP payload, including all attributes. (Please note this differs from generally recommended practice of only returning the ID.)
If the request is invalid, e.g., missing required parameters, the HTTP status code should be 400; otherwise 200.

	 */
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Organization> createOrg(@ModelAttribute Organization organization,@ModelAttribute Address address){
		if(organization.getName()==null){
			organization=null;
			return new ResponseEntity<Organization>(organization, HttpStatus.BAD_REQUEST);
		}
		
		if(address!=null)
			organization.setAddress(address);
			
		organization=organizationService.create(organization);
		return new ResponseEntity<Organization>(organization, HttpStatus.OK);
	}
	
	
	/**
(7) Update an organization
Path: org/{id}?name=XX&description=YY&street=ZZ&...
Method: POST

This API updates an organization object. 
For simplicity, all the fields (name, description, street, city, etc), except ID, are passed in as query parameters. Only name is required. 
Similar to the get method, the request returns the updated organization object, including all attributes in JSON. If the organization ID does not exist, 404 should be returned. If required parameters are missing, return 400 instead. Otherwise, return 200.

	 */
	@RequestMapping(method=RequestMethod.POST,value="{id}")
	@ResponseBody
	public ResponseEntity<Organization> updateOrg(@PathVariable long id,@ModelAttribute Organization organization,@ModelAttribute Address address){
		if(organization.getName()==null){
			organization=null;
			return new ResponseEntity<Organization>(organization, HttpStatus.BAD_REQUEST);
		}
		
		if(address!=null)
			organization.setAddress(address);
		organization.setOrganization_id(id);	
		organization=organizationService.update(organization);
		if(organization==null)
			return new ResponseEntity<Organization>(organization, HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Organization>(organization, HttpStatus.OK);
	}
	
	/**
(8) Delete an organization
URL: http://org/{id} 
Method: DELETE

This method deletes the organization object with the given ID. 
If there is still any person belonging to this organization, return 400. 
If the organization with the given ID does not exist, return 404.
Return HTTP code 200 and the deleted object in JSON if the object is deleted; 

	 */
	@RequestMapping(method=RequestMethod.DELETE,value="{id}")
	@ResponseBody
	public ResponseEntity<Organization> deleteOrg(@PathVariable long id){
		Organization organization = null;
		try{
			organization = organizationService.delete(id);
		}catch(HibernateException e){
			return new ResponseEntity<Organization>(organization, HttpStatus.BAD_REQUEST);
		}
		if(organization==null)
			return new ResponseEntity<Organization>(organization, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<Organization>(organization, HttpStatus.OK);
	}
}