package kpoberezhniuk.spring.boot.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        personRepository.deleteAll();
        personRepository.save(new Person("Alice", "Copper","text1"));
        personRepository.save(new Person("John", "Travolta","text2"));
        personRepository.save(new Person("Johy", "Moni","text3"));

        for (Person person : personRepository.findAll()) {
            System.out.println(person);
        }
        System.out.println();
        System.out.println(personRepository.findByFirstName("Alice"));
    }
}
