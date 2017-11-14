package com.mentoring.jpa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author ivanovaolyaa
 * @version 11/14/2017
 */
@Entity
@Table(name = "departments")
@Getter
@Setter
@ToString(exclude = "users")
public class Department {

    @Id
    @Column(name = "dep_id")
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "department")
    private List<User> users;

}
