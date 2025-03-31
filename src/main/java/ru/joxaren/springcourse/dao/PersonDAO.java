package ru.joxaren.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.joxaren.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int PEOPLE_COUNT;
    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("No connection");
            throw new RuntimeException(e);
        }
    }

    public List<Person> index(){
        List<Person> people = new ArrayList<>();

        String sql = "SELECT * FROM person;";

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                people.add(new Person(
                        result.getInt("id"), result.getString("name"),
                        result.getInt("age"), result.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    //менял логику поиска
    public Person show(int id){

        String sql = "Select * from person where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            result.next();
            return new Person(result.getInt("id"), result.getString("name"),
                    result.getInt("age"), result.getString("email"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person){

        String sql = "INSERT INTO person VALUES (100, ?, ?, ?)";


        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setString(3, person.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        System.out.println(sql);
    }

    public void update(int id, Person person){

        String sql = "UPDATE person SET name = ?, age = ?, email = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setString(3, person.getEmail());
            statement.setInt(4, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(int id){
        String sql = "DELETE FROM person where id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
