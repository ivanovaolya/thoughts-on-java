package com.mentoring.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mentoring.jpa.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author ivanovaolyaa
 * @version 11/15/2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserStreamsTest {

    private Logger logger = LoggerFactory.getLogger(UserStreamsTest.class);

    @PersistenceContext
    private EntityManager em;

    private List<User> users;

    private List<String> userNames;

    @Before
    public void setUp() throws Exception {
        users = em.createQuery("SELECT u FROM User u").getResultList();
        userNames = Arrays.asList("Olya", "Vasya", "Ivan");
    }

    @Test
    public void testUserFilteringByRate() throws Exception {
        assertEquals(3, users.size());

        final long count = users.stream()
                                .filter(u -> u.getRate() > 400.0)
                                .count();
        assertEquals(2, count);
    }

    @Test
    public void testUserMapping() throws Exception {
        final List<String> names = users.stream()
                                        .map(User::getName)
                                        .collect(Collectors.toList());

        assertEquals(userNames, names);

        final List<String> upperNames = names.stream()
                                             .map(String::toUpperCase)
                                             .collect(Collectors.toList());

        assertEquals(Arrays.asList("OLYA", "VASYA", "IVAN"), upperNames);
    }

    @Test
    public void testUserFlatMapping() throws Exception {
        final List<String> names = users.stream()
                                        .map(User::getName)
                                        .collect(Collectors.toList());

        final List<String> emails = users.stream()
                                         .map(User::getEmail)
                                         .collect(Collectors.toList());

        final List<String> collected = Stream.of(names, emails)
                                             .flatMap(Collection::stream)
                                             .collect(Collectors.toList());

        assertEquals(names.size() + emails.size(), collected.size());
    }

    @Test
    public void testFindUserWithMaxRate() throws Exception {
        final User user = users.stream()
                               .max(Comparator.comparing(User::getRate))
                               .get();

        assertEquals("Olya", user.getName());
    }


    @Test
    public void testReduceUsersSalary() throws Exception {
        final Stream<Double> salaries = users.stream().map(User::getSalary);
        final Double generalSalary = salaries.reduce(0., (acc, element) -> acc + element);

        assertEquals(45611.0, generalSalary, 1e-15);
    }

    @Test
    public void testUserSalariesInSortedOrder() throws Exception {
        final Stream<Double> salaries = users.stream()
                                             .map(User::getSalary)
                                             .sorted();
        logger.info("Sorted: " + Arrays.deepToString(salaries.toArray()));
    }

    @Test
    public void testUserPartitioningByStatus() throws Exception {
        final Map<Boolean, List<User>> partitionedByStatus = users.stream()
                .collect(Collectors.partitioningBy(User::isActive));

        assertEquals(2, partitionedByStatus.size()); // ACTIVE / INACTIVE
    }
}
