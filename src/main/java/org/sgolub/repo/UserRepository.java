package org.sgolub.repo;

import java.util.List;

import org.sgolub.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserRepository extends PagingAndSortingRepository<User, Long> , JpaSpecificationExecutor<User>, QueryByExampleExecutor<User>{
	
	List<User> findDistinctByFirstNameContainingAndLastNameContainingAndEmailContainingAndRoles_RoleNameContainingAllIgnoreCase(String firstName, String  lastName,String  email, String roleName);

}
