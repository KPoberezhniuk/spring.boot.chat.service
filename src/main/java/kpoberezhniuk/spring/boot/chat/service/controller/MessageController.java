package kpoberezhniuk.spring.boot.chat.service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kpoberezhniuk.spring.boot.chat.service.Person;
import kpoberezhniuk.spring.boot.chat.service.PersonRepository;
import kpoberezhniuk.spring.boot.chat.service.exceptions.NotFoundException;
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

    @Autowired
    private PersonRepository personRepository;

    private int counter = 5;
    public List<Map<String,String>> messages = new ArrayList<Map<String, String>>(){{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text","First message");}});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text","Second message");}});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text","Third message");}});
        add(new HashMap<String, String>() {{ put("id", "4"); put("text","Fought message");}});
    }};

    @GetMapping
    public List<Person> list (){
        return personRepository.findAll();
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage (id);
    }
            private Map<String, String> getMessage (@PathVariable String id){
            return messages.stream()
            .filter(message -> message.get("id").equals(id))
            .findFirst()
            .orElseThrow(NotFoundException::new);
    }


    @PostMapping
    public Person create(@RequestBody Person person){
       return personRepository.save(person);
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id,@RequestBody Map<String, String> message){
      Map<String,String> messageFromDb = getMessage(id);
      messageFromDb.putAll(message);
      messageFromDb.put("id", id);
      return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Map<String, String> message = getMessage(id);
        messages.remove(message);
    }
}
