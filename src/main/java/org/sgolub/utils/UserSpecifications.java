package org.sgolub.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.sgolub.domain.User;
import org.sgolub.domain.UserRole;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
	
	
	public static Specification<User> getAndSpecification(String firstName, String lastName, String  email, String roleName){
		return new Specification<User>() {
		@Override
		public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			
			Predicate criteria = cb.conjunction();
				// Creating and adding simple predicates
				criteria = cb.and(criteria, cb.like(cb.lower( root.get("firstName") ),  
											"%"+ firstName.toLowerCase() +"%" ));
	
				criteria = cb.and(criteria, cb.like(cb.lower( root.get("lastName") ),  
											"%"+ lastName.toLowerCase() +"%" ));
				
				criteria = cb.and(criteria, cb.like(cb.lower( root.get("email") ),  
											"%"+ email.toLowerCase() +"%" ));

			// Creating and adding predicate for search in UserRoles collection
			 Join<User, UserRole> join = root.join("roles");  
			 	criteria = cb.and(criteria, cb.like(cb.lower( join.get("roleName")) , "%"+ roleName.toLowerCase() + "%" ) ); 

			 return criteria;
		}

		};
	}
	
	

}
