package org.sgolub.service;

import java.util.ArrayList;
import java.util.List;

import org.sgolub.domain.User;
import org.sgolub.repo.UserRepository;
import org.sgolub.utils.UserExamples;
import org.sgolub.utils.UserSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserRepository  userRepository;

	/** 
	 * Get a paginated list of users, sorted by id, asc
	 * uses extension Interface of UserRepository - PagingAndSortingRepository for additional CRUD operations
	 * findAll(Pageable pageable)  on a repository for a specific type.
	 * 
	 * @param page - page number, 0 - based
	 * @param size - page size
	 * @return Page<User> - paginated list of users.
	 * defaults must be provided from controller
	 * */
	public Page<User> listPageableSortedById(Integer page, Integer size){
		Pageable pageRequest = new PageRequest(page, size, Direction.ASC, new String [] {"id"});
		return userRepository.findAll(pageRequest);
	}
	
	/**
	 * Get List of User's conform specified filtering params. User AND , LIKE, IGNORE CASE, DISTINCT
	 * params is ignored if null or ""
	 * uses extension Interface of UserRepository - QueryByExampleExecutor 
	 * to allow execution of Query by Example Example instances.
	 * QueryByExampleExecutor.findAll(Example<User> example) - example  is formed from method params.
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @return List<User>
	 */
	/* Does not support filtering by assigned ROLE*/
	public List<User> findByExample(String firstName, String  lastName, String  email){
		return (List<User>) userRepository.findAll(UserExamples.buildAndExample(firstName, lastName, email) );
	}
	
	/**
	 * Get List of User's conform specified filtering params. User AND , LIKE, IGNORE CASE, DISTINCT
	 * param is ignored if null or ""
	 * Uses specified manually defined  method query being derived from the method name.
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param roleName
	 * @return List<User>
	 */
	public List<User> findByNestedProperty(String firstName, String  lastName,String  email, String roleName){
		return userRepository.
				findDistinctByFirstNameContainingAndLastNameContainingAndEmailContainingAndRoles_RoleNameContainingAllIgnoreCase( firstName,lastName,email, roleName);
	}
	
	/**
	 * Get List of User's conform specified filtering params. User AND , LIKE, IGNORE CASE, DISTINCT
	 * params is ignored if null or "".
	 * 
	 * Uses extension Interface of UserRepository - JpaSpecificationExecutor to allow execution of Query by Specification instances.
	 *  JpaSpecificationExecutor.findAll(Specification<User> spec) - spec  is formed from method params.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @return List<User>
	 */
	public List<User> findBySpec(String firstName, String  lastName,String  email, String roleName){
		return userRepository.findAll(UserSpecifications.getAndSpecification( firstName ,lastName, email, roleName) );
	}

	
	/**
	 * Get List of User's conform specified sorting params. Params applied by FIFO
	 * params is ignored if null or "".
	 * 
	 * Uses extension Interface of UserRepository - PagingAndSortingRepository for additional CRUD operations
	 * findAll(Pageable pageable)  on a repository for a specific type.
	 *  PagingAndSortingRepository.findAll(Sort sort) - sort is built from method params.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @return List<User>
	 */
	public List<User> sortBy(String firstName, String  lastName,String  email){
				Sort sortClause = buildUserSort( firstName,   lastName,  email);
		if (sortClause != null ) {
			userRepository.findAll(sortClause);
			return (List<User>) userRepository.findAll(sortClause); 
		} else {
			return (List<User>) userRepository.findAll();
		}
		
	}
	
	private Sort buildUserSort(String firstName, String  lastName,String  email) {
		List<Order> ordersList = new ArrayList<>();
		appendCheked(ordersList, "firstName", firstName);
		appendCheked(ordersList, "lastName", lastName);
		appendCheked(ordersList, "email", email);

		if (!ordersList.isEmpty()) {
			return new Sort(ordersList); 
		} 
		return null;
	}
	
	/**  Adds the  new Order instance to provided orderList - if paramsa is OK.
	 * @param ordersList List<Order> ordersList  - is provided set of Orders
	 * @param fieldName - fieldName to apply sort
	 * @param orderMode - strong representation of order mode to apply 
	 * */
	private void appendCheked(List<Order> ordersList, String fieldName, String orderMode) {

		if (ordersList == null || fieldName == null || orderMode == null) return ; 
		if (! orderMode.isEmpty()) {
			Order order = null;
			if (orderMode.equalsIgnoreCase("ASC")) {
				order = new Order(Direction.ASC, fieldName);
			}  else if (orderMode.equalsIgnoreCase("DESC")) {
				order = new Order(Direction.DESC,fieldName);
			}
			if (order != null ) ordersList.add(order);		
		}
		
	}
	

	
	
	public Page<User> putAllTogether(String firstName, String  lastName,String  email, String roleName){
		
		final Specification<User> spec = UserSpecifications.getAndSpecification( firstName ,lastName, email, roleName);
		final PageRequest pageRequest = new PageRequest(
				  0, 20, new Sort(
				    new Order(Direction.ASC, "lastName"), 
				    new Order(Direction.DESC, "firstName")
				  )
				);
		
		return userRepository.findAll(spec, pageRequest);
	}
	
	
}
