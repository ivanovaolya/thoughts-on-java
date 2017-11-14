package com.mentoring.jpa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Map;

import javax.persistence.*;

/**
 * @author ivanovaolyaa
 * @version 10/17/2017
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<AddressType, Address> addresses;

}
