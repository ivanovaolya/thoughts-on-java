package com.mentoring.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.mentoring.jpa.model.Address;
import com.mentoring.jpa.model.AddressType;
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

    @Test
    public void testAddresses() throws Exception {
        final List<Address> addresses = em.createQuery("SELECT a FROM Address a").getResultList();
        addresses.forEach(address -> logger.info(address.getAddressId() + "; " + address.getAddressType()));
    }

    @Test
    public void testHibernateMapAssociation() throws Exception {
        final User user = em.find(User.class, 3L);
        assertNotNull(user);
        assertEquals(0, user.getAddresses().size());

        final Address address = new Address(4L, "NY", "Brighton Beach", "678421", AddressType.WORK, user);

        user.getAddresses().put(address.getAddressType(), address);
        em.persist(user);

        final User notCachedUser = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", 3L).getSingleResult();

        assertEquals(1, notCachedUser.getAddresses().size());
        assertEquals("NY", notCachedUser.getAddresses().get(AddressType.WORK).getCity());
    }

    @Test
    public void testHibernateMapAssociationWithDuplicateKey() throws Exception {
        final User user = em.find(User.class, 3L);
        assertNotNull(user);
        assertEquals(0, user.getAddresses().size());

        final Address address = new Address(4L, "NY", "Brighton Beach", "678421", AddressType.WORK, user);
        final Address anotherAddress = new Address(5L, "London", "Abbey Road", "12354", AddressType.WORK, user);

        user.getAddresses().put(address.getAddressType(), address);

        em.persist(user);

        user.getAddresses().put(address.getAddressType(), anotherAddress);
        em.persist(user);

        final User notCachedUser = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", 3L).getSingleResult();

        assertEquals(1, notCachedUser.getAddresses().size());
        assertEquals("London", notCachedUser.getAddresses().get(AddressType.WORK).getCity());


    }
}
