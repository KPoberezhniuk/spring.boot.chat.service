package kpoberezhniuk.spring.boot.chat.service.controller

import kpoberezhniuk.spring.boot.chat.service.Person
import kpoberezhniuk.spring.boot.chat.service.PersonRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import spock.lang.Specification

import java.time.LocalDateTime

class MessageControllerSpec extends Specification {
    static final TIME_FROM = "2021-03-04T11:30"
    static final TIME_TO = "2021-04-04T11:30"
    static final PAGE_SIZE = 1
    static final PAGE_NUMBER = 5
    static final PERSON = createPersonInfo()
    static final PERSON_ID = "id"
    static final MESSAGE_TEXT = "messageText"

    def repository = Mock(PersonRepository.class)
    def controller = new MessageController(repository)

    def "should return list of users"() {
        when:
        def result = controller.list()
        then:
        result == [PERSON]
        1 * repository.findAll() >> [PERSON]
    }

    def "should return list of users sorted by time range and amount on page"() {
        given:
        def from = LocalDateTime.of(2021, 03, 04, 11, 30)
        def to = LocalDateTime.of(2021, 04, 04, 11, 30)
        def page = PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "creationTime"))
        when:
        def result = controller.sortByTimeAndSize(PAGE_SIZE, PAGE_NUMBER, TIME_FROM, TIME_TO)
        then:
        result == [createPersonInfo()]
        1 * repository.findByCreationTimeBetween(from, to, page) >> new PageImpl<>([createPersonInfo()])
    }

    def "should create new user"() {
        when:
        def result = controller.create(PERSON)
        then:
        result == createPersonInfo()
        1 * repository.save(PERSON) >> createPersonInfo()
    }

    def "should delete user from the list"() {
        given:
        def person = Person.builder()
                .id("person")
                .firstName("petr")
                .lastName("petrov")
                .build()
        when:
        controller.delete(person)
        then:
        1 * repository.delete(person)
    }

    def "should not update if user absent in db"() {
        when:
        def result = controller.update(PERSON_ID, MESSAGE_TEXT)
        then:
        result == null
        1 * repository.findById(PERSON_ID) >> Optional.empty()
        0 * repository.save(_)
    }

    def "should update user message text"() {
        given:
        def newMessageText = "newMessageText"
        def person = Person.builder().id(PERSON_ID).messageText(MESSAGE_TEXT).build()
        def updatedPerson = Person.builder().id(PERSON_ID).messageText(newMessageText).build()
        controller.create(person)
        when:
        def result = controller.update(PERSON_ID, newMessageText)
        then:
        result == updatedPerson
        1 * repository.findById(PERSON_ID) >> Optional.of(person)
        1 * repository.save(updatedPerson) >> updatedPerson
    }

    private def static createPersonInfo() {
        Person.builder()
                .id("1111")
                .firstName("name")
                .lastName("lastName")
                .messageText("someText")
                .creationTime(LocalDateTime.now())
                .build()
    }
}