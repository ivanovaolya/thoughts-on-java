package com.mentoring.jpa;

import java.time.LocalDate;
import java.time.Month;

import javax.persistence.EntityManager;

import com.mentoring.jpa.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author ivanovaolyaa
 * @version 10/17/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserIntegrationTest {

    private Logger logger = LoggerFactory.getLogger(UserIntegrationTest.class);

    @Autowired
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

}
