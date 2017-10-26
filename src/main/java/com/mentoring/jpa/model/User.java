package com.mentoring.jpa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ivanovaolyaa
 * @version 10/17/2017
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(callSuper = false)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

}
