package com.mentoring.jpa;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mentoring.jpa.model.User;
import com.mentoring.jpa.model.UserStatus;
import com.mentoring.jpa.web.dto.UserDto;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author ivanovaolyaa
 * @version 10/17/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserIntegrationTest {

    private Logger logger = LoggerFactory.getLogger(UserIntegrationTest.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED) // to persist entities without transactions
    private EntityManager em;

    @Test
    public void testFindUser() {
        User user = em.find(User.class, 1L);
        assertNotNull(user);
        logger.info(user.toString());
    }

    @Test
    public void testJavaDateApiWithHibernate() {
        User user = em.find(User.class, 1L);
        logger.info(user.toString());

        assertEquals(LocalDate.of(1995, Month.JULY, 20), user.getBirthDate());
    }

    @Test
    public void testSelectClauseWithCriteriaQuery() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserDto> query = cb.createQuery(UserDto.class);
        Root<User> root = query.from(User.class);
        query.select(cb.construct(UserDto.class, root.get("email"), root.get("birthDate")));

        List<UserDto> users = em.createQuery(query).getResultList();
        users.forEach(u -> logger.info(u.toString()));

        assertEquals(3, users.size());
    }

    @Test
    public void testWhereClauseWithCriteriaQuery() {
        final String userName = "Olya";
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        query.where(cb.equal(root.get("name"), userName));

        List<User> users = em.createQuery(query).getResultList();
        users.forEach(user -> {
            logger.info(user.toString());
            assertEquals(userName, user.getName());
        });
    }

    @Test
    public void testCountUsersWithCriteriaQuery() throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        final Root<User> root = query.from(User.class);
        query.select(cb.count(root));
        final Object result = em.createQuery(query).getSingleResult();

        assertEquals(result, 3L);
    }

    @Test
    public void testTupleWithCriteriaQuery() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> tupleQuery = cb.createTupleQuery();
        Root<User> root = tupleQuery.from(User.class);
        tupleQuery.multiselect(root.get("name").alias("n"),
                                root.get("email").alias("e"));

        List<Tuple> tuples = em.createQuery(tupleQuery).getResultList();

        tuples.forEach(t -> {
            assertNotNull(t.get("n"));
            assertNotNull(t.get("e"));
            logger.info(String.format("Name: %s, email: %s", t.get("n"), t.get("e")));
        });

    }

    @Test
    public void testPersistEnumValue() {
        User user = em.find(User.class, 1L);
        assertNull(user.getUserStatus());
        user.setUserStatus(UserStatus.ACTIVE);

        em.persist(user);
        final User notCachedUser = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", 1L).getSingleResult();
        assertEquals(UserStatus.ACTIVE, notCachedUser.getUserStatus());
    }
}
