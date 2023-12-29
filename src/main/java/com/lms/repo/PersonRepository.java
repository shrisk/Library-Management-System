package com.lms.repo;

import com.lms.dto.PersonRequest;
import com.lms.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_PERSON_QUERY = "INSERT INTO person (name, email, password) VALUES (?, ?, ?)";
    private static final String GET_PERSON_BY_ID_QUERY = "SELECT * FROM person WHERE id = ?";
    private static final String GET_PERSON_BY_EMAIL_QUERY = "SELECT * FROM person WHERE email = ?";
    private static final String GET_ALL_PEOPLE_QUERY = "SELECT * FROM person";
    private static final String UPDATE_PERSON_QUERY = "UPDATE person SET name = ?, email = ? WHERE id = ?";
    private static final String DELETE_PERSON_QUERY = "DELETE FROM person WHERE id = ?";
    private static final String GET_NUMBER_OF_PEOPLE_REGISTERED_QUERY = "SELECT COUNT(*) FROM person";

    public void addPerson(PersonRequest personRequest) {
        jdbcTemplate.update(INSERT_PERSON_QUERY, personRequest.getName(), personRequest.getEmail(), personRequest.getPassword());
    }

    public Optional<Person> getPersonById(Long id) {
        List<Person> result = jdbcTemplate.query(GET_PERSON_BY_ID_QUERY, new BeanPropertyRowMapper<>(Person.class), id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
    
    public Optional<Person> getPersonByEmail(String email) {
        List<Person> result = jdbcTemplate.query(GET_PERSON_BY_EMAIL_QUERY, new BeanPropertyRowMapper<>(Person.class), email);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public List<Person> getAllPeople() {
        return jdbcTemplate.query(GET_ALL_PEOPLE_QUERY, new BeanPropertyRowMapper<>(Person.class));
    }

    public boolean updatePerson(Long id, PersonRequest personRequest) {
        int rowsUpdated = jdbcTemplate.update(UPDATE_PERSON_QUERY, personRequest.getName(), personRequest.getEmail(), id);
        return rowsUpdated > 0;
    }

    public void deletePerson(Long id) {
        jdbcTemplate.update(DELETE_PERSON_QUERY, id);
    }

    public Long getNumberOfPeopleRegistered() {
        return jdbcTemplate.queryForObject(GET_NUMBER_OF_PEOPLE_REGISTERED_QUERY, Long.class);
    }
}
