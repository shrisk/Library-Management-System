package com.lms.service;

import com.lms.dto.PersonRequest;
import com.lms.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    void addPerson(PersonRequest personRequest);

    List<Person> getAllPeople();

    Optional<Person> getPersonById(Long id);

    boolean updatePerson(Long id, PersonRequest personRequest);

    void deletePerson(Long id);
    
    /**
     * Get the number of people registered in the library.
     *
     * @return The number of people registered.
     */
    Long getNumberOfPeopleRegistered();
}
