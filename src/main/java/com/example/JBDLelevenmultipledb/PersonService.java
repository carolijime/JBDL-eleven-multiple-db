package com.example.JBDLelevenmultipledb;


import com.example.JBDLelevenmultipledb.persondb.Person;
import com.example.JBDLelevenmultipledb.persondb.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    private static Logger logger = LoggerFactory.getLogger(PersonService.class);

    public void createPerson(CreatePersonRequest createPersonRequest) {

        Person person = createPersonRequest.to();

        //example of how to have a field in the request, but not saving it in the DB, just using for doing some logic
//        if(person.getCountryCode() == "+91"){
//
//        }else{
//
//        }

        Person personFromDB = personRepository.save(person);

        logger.info("Person saved - {}", personFromDB);
    }

    public Person getPerson(int id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() ->
                        new PersonNotFoundException("Person with Id " + id + " not present"));
    }

    /*Even though we would be passing age from the controller, but the underlying repository
        //layer will search primary key. So it will not work and we have to create our own query
    public Person getPerson2(int age){
        return personRepository.findById(age).get();
    }*/

    public List<Person> getPeople() {
        return personRepository.findAll();
    }
}
