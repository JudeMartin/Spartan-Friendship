/**
 * 
 */
package com.cmpe275.lab2.service;

import org.springframework.stereotype.Service;

import com.cmpe275.lab2.model.Organization;

@Service
public interface OrganizationService {

	public Organization create(Organization organization);
	
	public Organization read(long id);
	
	public Organization update(Organization organization);

	public Organization delete(long id);

}