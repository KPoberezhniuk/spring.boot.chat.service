package kpoberezhniuk.spring.boot.chat.service.controller;

import java.util.List;
import java.util.Optional;

import kpoberezhniuk.spring.boot.chat.service.Person;
import kpoberezhniuk.spring.boot.chat.service.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private int counter = 5;

    @GetMapping
    public List<Person> list() {
        return personRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Person> getMessage (@PathVariable String id) {
        return personRepository.findById(id);
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        return personRepository.save(person);
    }

    /*@PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(id);
        messageFromDb.putAll(message);
        messageFromDb.put("id", id);
        return messageFromDb;
    }*/

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        personRepository.deleteById(id);
    }
}
