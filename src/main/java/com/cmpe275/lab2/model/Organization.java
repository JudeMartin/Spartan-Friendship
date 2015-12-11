package com.cmpe275.lab2.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonUnwrapped;

@Entity
@Table(name="Organization")
public class Organization {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long organization_id;
	@Column(nullable=false)
    private String name;
    private String description;
    @Embedded
    @JsonUnwrapped
    private Address address;
	

	public long getOrganization_id() {
		return organization_id;
	}
	
	public void setOrganization_id(long organization_id) {
		this.organization_id = organization_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	@Override
	public String toString() {
		return "organization : "+this.getName()+","+this.getDescription()+","+this.getAddress().toString();
	}
}