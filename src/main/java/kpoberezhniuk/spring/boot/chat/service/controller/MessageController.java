package kpoberezhniuk.spring.boot.chat.service.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import kpoberezhniuk.spring.boot.chat.service.Person;
import kpoberezhniuk.spring.boot.chat.service.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("sortByTime")
    public List<Person> byTimeRange(@RequestParam(value = "timeFrom") String timeFrom,
                                    @RequestParam(value = "timeTo") String timeTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTimeFrom = LocalDateTime.parse(timeFrom, formatter);
        LocalDateTime dateTimeTo = LocalDateTime.parse(timeTo, formatter);
        return personRepository.findByCreationTimeBetween(dateTimeFrom, dateTimeTo);
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

    @GetMapping("batch/{pageNumber}/{pageSize}")
    public List<Person> sortByPageSizeAndNumber(@PathVariable("pageSize") Integer pageSize,
                                                @PathVariable("pageNumber") Integer pageNumber) {
        PageRequest page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "creationTime"));
        return personRepository.findAll(page).toList();
    }

    @PutMapping("{id}")
    public Person update(
        @PathVariable("id") String id,
        @RequestBody String messageText
    ) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            return null;
        }
        Person person = personOptional.get();
        person.setMessageText(messageText);
        person.setUpdateTime(LocalDateTime.now());
        return personRepository.save(person);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Person person) {
        personRepository.delete(person);
    }
}