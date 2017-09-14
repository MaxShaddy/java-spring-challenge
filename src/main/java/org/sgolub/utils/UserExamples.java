package org.sgolub.utils;

import org.sgolub.domain.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

public class UserExamples {
	
	public static Example<User> buildAndExample(String firstName, String  lastName,String  email){
		
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase();
		
		return Example.of(buildUserForExample( firstName, lastName,email), matcher) ;
	}
	
	private static User buildUserForExample(String firstName, String  lastName,String  email) {
		User userForExample = new User();
		userForExample.setFirstName(firstName);
		userForExample.setLastName(lastName);
		return userForExample;
	}

}
