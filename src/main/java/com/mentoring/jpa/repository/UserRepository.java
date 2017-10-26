package com.mentoring.jpa.repository;

import java.util.List;

import com.mentoring.jpa.model.User;

import org.springframework.data.repository.CrudRepository;

/**
 * @author ivanovaolyaa
 * @version 10/26/2017
 */
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByName(String name);

}
