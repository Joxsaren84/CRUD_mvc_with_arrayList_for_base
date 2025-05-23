package ru.joxaren.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.joxaren.springcourse.dao.PersonDAO;
import ru.joxaren.springcourse.models.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person =  (Person) o;

        if(personDAO.show(((Person) o).getEmail()).isPresent()){
            errors.rejectValue("email", "", "This email is already using");
        }

    }
}
