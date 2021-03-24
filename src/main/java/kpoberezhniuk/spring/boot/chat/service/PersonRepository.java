package kpoberezhniuk.spring.boot.chat.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
    List<Person> findByCreationTimeBetween(LocalDateTime timeFrom, LocalDateTime timeTo);
}
