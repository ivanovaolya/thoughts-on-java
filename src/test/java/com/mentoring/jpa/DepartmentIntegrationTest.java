package com.mentoring.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import com.mentoring.jpa.model.Department;
import com.mentoring.jpa.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author ivanovaolyaa
 * @version 11/14/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentIntegrationTest {
    private Logger logger = LoggerFactory.getLogger(DepartmentIntegrationTest.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED) // to persist entities without transactions
    private EntityManager em;

    @Test
    public void testDepartmentUuidGeneration() throws Exception {
        final User user = em.find(User.class, 1L);
        final Department department = new Department();
        department.setName("Java Solutions 2");
        em.persist(department);

        user.setDepartment(department);

        em.persist(user);

        final User notCachedUser = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", 1L).getSingleResult();
        assertNotNull(notCachedUser.getDepartment().getId());
        logger.info("Generated UUID: " + notCachedUser.getDepartment().getId());
    }

    @Test
    public void testDepartmentNativeSqlQueryWithResultMapping() throws Exception {
        final List<Department> departments = em.createNativeQuery("select d.dep_id, d.name from departments d",
                "DepartmentValueMapping").getResultList();

        assertEquals(2, departments.size());
        departments.forEach(d -> logger.info("ID: " + d.getId() + "; Name: " + d.getName()));
    }
}
