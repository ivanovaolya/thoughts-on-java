package com.mentoring.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
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
@SqlResultSetMapping(name = "DepartmentValueMapping",
        classes = @ConstructorResult(
                targetClass = Department.class,
                columns = {
                        @ColumnResult(name = "dep_id", type = UUID.class),
                        @ColumnResult(name = "name", type = String.class)
                }
        ))
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @Column(name = "dep_id")
    @GeneratedValue
    private UUID id;

    @Column
    private String name;

    @OneToMany(mappedBy = "department")
    private List<User> users;

    public Department(final UUID id, final String name) {
        this.id = id;
        this.name = name;
    }

}
