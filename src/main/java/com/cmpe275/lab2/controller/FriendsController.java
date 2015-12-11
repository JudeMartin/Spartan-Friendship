package com.cmpe275.lab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpe275.lab2.service.FriendService;

@Controller
@RequestMapping(value="/friends")
public class FriendsController {
	
	@Autowired
	FriendService friendService;
	
	
	 /* (9) Add a friend
Path:friends/{id1}/{id2} 
Method: PUT

This makes the two persons with the given IDs friends with each other. 
If either person does not exist, return 404. 
If the two persons are already friends, do nothing, just return 200. Otherwise,
Record this friendship relation. If all is successful, return HTTP code 200 and any informative text message in the HTTP payload.
 */
	@RequestMapping(method=RequestMethod.PUT,value="{id1}/{id2}")
	@ResponseBody
	public ResponseEntity<String> addFriend(@PathVariable long id1, @PathVariable long id2){
		
		String friendship = friendService.create(id1, id2);
		
		String response = friendship.split("#")[0];
		
		
		String returnValue = friendship.split("#")[1];
		if(response.equals("200"))
			return new ResponseEntity<String>(returnValue, HttpStatus.OK);
		
		return new ResponseEntity<String>(returnValue, HttpStatus.NOT_FOUND);
	}
	
	
	
	/**
	 (10) Remove a friend
Path:friends/{id1}/{id2} 
Method: DELETE

This request removes the friendship relation between the two persons.
If either person does not exist, return 404. 
If the two persons are not friends, return 404. Otherwise,
Remove this friendship relation. Return HTTP code 200 and a meaningful text message if all is successful.

	 */
	@RequestMapping(method=RequestMethod.DELETE,value="{id1}/{id2}")
	@ResponseBody
	public ResponseEntity<String> removeFriend(@PathVariable long id1, @PathVariable long id2){
		
		String friendship = friendService.delete(id1, id2);
		String response = friendship.split("#")[1];
		String returnValue = friendship.split("#")[0];
		
		if(response.equals("200"))
			return new ResponseEntity<String>(returnValue, HttpStatus.OK);
		
		return new ResponseEntity<String>(returnValue, HttpStatus.NOT_FOUND);
	}

}
