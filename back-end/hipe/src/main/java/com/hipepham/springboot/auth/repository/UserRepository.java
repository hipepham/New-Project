package com.hipepham.springboot.auth.repository;

import com.hipepham.springboot.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * The interface User role repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	@Query(value = "SELECT u FROM User u WHERE u.username = :username")
	User findByUsername(@Param("username") String username);

}
