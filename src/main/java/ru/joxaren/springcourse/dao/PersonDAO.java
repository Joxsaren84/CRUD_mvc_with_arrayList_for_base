package ru.joxaren.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.joxaren.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public List<Person> index(){
        String sql = "SELECT * FROM person;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class));
    }


    public Person show(int id){

        String sql = "Select * from person where id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);

    }

    public void save(Person person){

        String sql = "INSERT INTO person VALUES (100, ?, ?, ?)";
        jdbcTemplate.update(sql, person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person person){

        String sql = "UPDATE person SET name = ?, age = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, person.getName(), person.getAge(), person.getEmail(), person.getId());

    }

    public void delete(int id){

        String sql = "DELETE FROM person where id = ?";
        jdbcTemplate.update(sql, id);

    }

}
