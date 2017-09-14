package org.sgolub.web;

import java.util.List;

import org.sgolub.domain.User;
import org.sgolub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class ApplicationRestController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "ping", method = RequestMethod.GET)
	public String ping(){
		return "pong";
	}
	
	/*  PAGINATION */
	
	@RequestMapping(value = "users/list-paginated", method = RequestMethod.GET)
	public Page<User>  listPaginatedUsers(@RequestParam(required=false, defaultValue="0", name="page") Integer page,
								 		  @RequestParam(required=false, defaultValue="5", name="size") Integer size){
		return userService.listPageableSortedById(page,size);
	}

	/*  FILTERING */
	
	@RequestMapping(value = "users/find-by-example", method = RequestMethod.GET)
	public List<User>  findByExample(@RequestParam(required=false, defaultValue="", name="firstName") String firstName,
			@RequestParam(required=false, defaultValue="", name="lastName") String  lastName,
			@RequestParam(required=false, defaultValue="", name="email") String  email) {
		
		return userService.findByExample(firstName, lastName,email);
	}

	@RequestMapping(value = "users/find-by-nested-property", method = RequestMethod.GET)
	public List<User>  findByNestedProperty(@RequestParam(required=false, defaultValue="", name="firstName") String firstName,
			@RequestParam(required=false, defaultValue="", name="lastName") String  lastName,
			@RequestParam(required=false, defaultValue="", name="email")String  email,
			@RequestParam(required=false, defaultValue="", name="roleName") String  roleName) {
		
		return userService.findByNestedProperty(firstName, lastName, email, roleName);
	}
	 
	@RequestMapping(value = "users/find-by-spec", method = RequestMethod.GET)
	public List<User>  findBySpec(@RequestParam(required=false, defaultValue="", name="firstName") String firstName,
			@RequestParam(required=false, defaultValue="", name="lastName") String  lastName,
			@RequestParam(required=false, defaultValue="", name="email")String  email,
			@RequestParam(required=false, defaultValue="", name="roleName") String  roleName) {
		
		return userService.findBySpec(firstName, lastName, email, roleName);
	}
	
	/* SORTING*/
	@RequestMapping(value = "users/sort-by", method = RequestMethod.GET)
	public List<User>  sortBy(@RequestParam(required=false, defaultValue="", name="firstName") String firstName,
			@RequestParam(required=false, defaultValue="", name="lastName") String  lastName,
			@RequestParam(required=false, defaultValue="", name="email")String  email) {
		
		return userService.sortBy(firstName, lastName, email);
	}
	
	
	/* ALL TOGETHER */
	@RequestMapping(value = "users/find-sorted-paged", method = RequestMethod.GET)
	public Page<User> findSortedPaged(
			@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
			@RequestParam(required = false, defaultValue = "5", name = "size") Integer size,
			@RequestParam(required = false, defaultValue = "", name = "fFirstName") String fFirstName,
			@RequestParam(required = false, defaultValue = "", name = "fLastName") String fLastName,
			@RequestParam(required = false, defaultValue = "", name = "fEmail") String fEmail,
			@RequestParam(required = false, defaultValue = "", name = "fRoleName") String fRoleName,
			@RequestParam(required = false, defaultValue = "", name = "sFirstName") String sFirstName,
			@RequestParam(required = false, defaultValue = "", name = "sLastName") String sLastName,
			@RequestParam(required = false, defaultValue = "", name = "sEmail") String sEmail
			) {
		return userService.putAllTogether(fFirstName, fLastName, fEmail, fRoleName, sFirstName, sLastName, sEmail, page, size);
	}
	
	
	
}
