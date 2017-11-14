package com.mentoring.jpa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import org.hibernate.annotations.Formula;

/**
 * @author ivanovaolyaa
 * @version 10/17/2017
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = "addresses")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private Double rate;

    @Formula("rate * 26.83")
    private Double salary;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ElementCollection
    private List<String> phones;

    /*Hibernate: create table user_phones (
        user_user_id bigint not null,
        phones varchar(255)
    )*/

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<AddressType, Address> addresses;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dep_id")
    private Department department;

}
