package ru.joxaren.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.joxaren.springcourse.models.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Person> show(String email){

        String sql = "Select * from person where email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();

    }

    public void save(Person person){

        String sql = "INSERT INTO person (name, age, email, address) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }

    public void update(int id, Person person){

        String sql = "UPDATE person SET name = ?, age = ?, email = ?, address = ? WHERE id = ?";
        jdbcTemplate.update(sql, person.getName(), person.getAge(), person.getEmail(), person.getAddress(), id);

    }

    public void delete(int id){

        String sql = "DELETE FROM person where id = ?";
        jdbcTemplate.update(sql, id);

    }

    //methods for test Batch

    public void testMultipleUpdate(){
        List<Person> people = create1000people();
        long before = System.currentTimeMillis();

        String sql = "INSERT INTO person VALUES (?, ?, ?, ?)";

        for (Person person : people){
            jdbcTemplate.update(sql, person.getId(), person.getName(), person.getAge(), person.getEmail());
        }

        long after = System.currentTimeMillis();

        System.out.println("Time: " + (after - before));
    }

    private List<Person> create1000people() {

        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 1000; i++){
            people.add(new Person(i, "name" + i, 30, "name" + i + "@gmail.com", + i + "someaddress"));
        }

        return people;
    }

    public void testBatchUpdate(){
        List<Person> people = create1000people();
        long before = System.currentTimeMillis();

        String sql = "INSERT INTO person VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, people.get(i).getId());
                preparedStatement.setString(2, people.get(i).getName());
                preparedStatement.setInt(3, people.get(i).getAge());
                preparedStatement.setString(4, people.get(i).getEmail());
                preparedStatement.setString(5, people.get(i).getAddress());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });

        long after = System.currentTimeMillis();

        System.out.println("Time: " + (after - before));
    }

    public void deleteAll(){
        String sql = "TRUNCATE TABLE person";
        jdbcTemplate.update(sql);
    }
}
