package ru.joxaren.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.joxaren.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(PEOPLE_COUNT, "Henry", 23, "mymail@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Thomas", 56, "pochta@google.com"));
        people.add(new Person(++PEOPLE_COUNT, "Julia", 34, "jl34@yandex.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Andrey", 26, "andrey@yahoo.com"));
        people.add(new Person(++PEOPLE_COUNT, "Helen", 12, "hel@hh.th"));

    }

    public List<Person> index(){
        return people;
    }


    //менял логику поиска
    public Person show(int id){
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person person){
        Person changePerson = people.get(id);
        changePerson.setName(person.getName());
        changePerson.setAge(person.getAge());
        changePerson.setEmail(person.getEmail());
    }

    public void delete(int id){
        people.removeIf(person -> person.getId() == id);
    }
}
