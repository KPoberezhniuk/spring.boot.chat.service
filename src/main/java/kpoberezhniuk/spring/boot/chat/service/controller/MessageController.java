package kpoberezhniuk.spring.boot.chat.service.controller;

import java.time.LocalDateTime;
import java.util.List;

import kpoberezhniuk.spring.boot.chat.service.Person;
import kpoberezhniuk.spring.boot.chat.service.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message")
public class MessageController {
    private final PersonRepository personRepository;

    @Autowired
    public MessageController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> list() {
        return personRepository.findAll();
    }

    @GetMapping("{id}")
    public Person getOne(@PathVariable("id") Person person) {
        return person;
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        person.setCreationTime(LocalDateTime.now());
        return personRepository.save(person);
    }

    @PutMapping("{id}")
    public Person update(
        @PathVariable("id") Person personFromDb,
        @RequestBody Person person
    ) {
        BeanUtils.copyProperties(person, personFromDb, "id");
        return personRepository.save(personFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Person person) {
        personRepository.delete(person);
    }
}
