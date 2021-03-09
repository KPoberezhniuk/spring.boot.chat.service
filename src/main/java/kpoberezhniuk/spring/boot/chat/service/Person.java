package kpoberezhniuk.spring.boot.chat.service;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Person {
    @Id
    public String id;

    @Field
    public String firstName;
    @Field
    public String lastName;
    @Field
    public String messageText;

    public Person() {
    }

    public Person(String firstName, String lastName, String messageText) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.messageText = messageText;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id='" + id + '\'' +
            "' firstName='" + firstName + '\'' +
            "' lastName='" + lastName + '\'' +
            "' messageText='" + messageText + '\'' +
            '}';

    }
}
