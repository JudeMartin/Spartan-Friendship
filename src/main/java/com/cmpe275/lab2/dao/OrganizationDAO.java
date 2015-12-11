/**
 * 
 */
package com.cmpe275.lab2.dao;

import org.springframework.stereotype.Repository;

import com.cmpe275.lab2.model.Organization;

@Repository
public interface OrganizationDAO {
	
	public Organization create(Organization organization);
	public Organization read(long id);
	public Organization update(Organization organization);
	public Organization delete(Organization organization);

}