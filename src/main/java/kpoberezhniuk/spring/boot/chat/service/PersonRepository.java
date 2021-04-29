package kpoberezhniuk.spring.boot.chat.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
    Page<Person> findByCreationTimeBetween(LocalDateTime timeFrom, LocalDateTime timeTo, Pageable pageable);


}
