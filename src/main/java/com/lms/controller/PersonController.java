package com.lms.controller;

import com.lms.dto.PersonRequest;
import com.lms.entity.Person;
import com.lms.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/getAllPerson")
    public ResponseEntity<List<Person>> getAllPeople() {
        List<Person> people = personService.getAllPeople();
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @GetMapping("/getPerson/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        return personService.getPersonById(id)
                .map(person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/updatePerson/{id}")
    public ResponseEntity<String> updatePerson(@PathVariable Long id, @RequestBody PersonRequest personRequest) {
        boolean update = personService.updatePerson(id, personRequest);
        if (update) {
            return new ResponseEntity<>("Person updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletePerson/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>("Person deleted successfully", HttpStatus.OK);
    }
}
