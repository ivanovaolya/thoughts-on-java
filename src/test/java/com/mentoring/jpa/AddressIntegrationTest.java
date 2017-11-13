package com.mentoring.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ivanovaolyaa
 * @version 11/13/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressIntegrationTest {

    private Logger logger = LoggerFactory.getLogger(AddressIntegrationTest.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED) // to persist entities without transactions
    private EntityManager em;

    @Test
    public void testStandardJpqlAggregateFunction() throws Exception {
        final List<Object[]> list = em.createQuery("SELECT u.email, size(u.addresses) FROM User u GROUP BY u.id")
                .getResultList(); // automatically invoke count(addresses1_.user_id) in JPQL query
        list.forEach(l -> logger.info(l[0] + " has " + l[1] + " address(es)"));
    }

    @Test
    public void testStandardJpqlStringFunction() throws Exception {
        final List<String> list = em.createQuery("SELECT upper(u.email) FROM User u").getResultList();
        list.forEach(l -> logger.info(l));
    }

    @Test
    public void testStandardJpqlDateFunction() throws Exception {
        final List<Object[]> list = em.createQuery("SELECT u.email, CURRENT_DATE() FROM User u").getResultList();
        list.forEach(l -> logger.info("User: " + l[0] + "; current DB date: " + l[1]));

    }

}
