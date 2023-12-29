package com.lms.service;

import com.lms.dto.PersonRequest;
import com.lms.entity.Person;
import com.lms.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void addPerson(PersonRequest personRequest) {
        personRepository.addPerson(personRequest);
    }

    @Override
    public List<Person> getAllPeople() {
        return personRepository.getAllPeople();
    }

    @Override
    public Optional<Person> getPersonById(Long id) {
        return personRepository.getPersonById(id);
    }

    @Override
    public boolean updatePerson(Long id, PersonRequest personRequest) {
        return personRepository.updatePerson(id, personRequest);
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deletePerson(id);
    }
    
    @Override
    public Long getNumberOfPeopleRegistered() {
    	return personRepository.getNumberOfPeopleRegistered();
    }
}
