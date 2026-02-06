package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        //language=MySQL
        String sql = """
                CREATE TABLE pre_project.user(
                  user_id BIGINT AUTO_INCREMENT PRIMARY KEY ,
                  first_name VARCHAR(40) NOT NULL ,
                  last_name VARCHAR(40),
                  user_age TINYINT NOT NULL
                );
                """;
        try (var connection = Util.openConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }


    }

    public void dropUsersTable() {
        //language=MySQL
        String sql = """
                DROP TABLE IF EXISTS pre_project.user;
                """;
        try (var connection = Util.openConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Connection is unavailable");
            throw new RuntimeException(e);

        }



    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                INSERT INTO pre_project.user (first_name, last_name, user_age)
                VALUES (?, ?, ?);
                """;
        try (var connection = Util.openConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - "+name+" добавлен в базу данных.");
        } catch (SQLException e) {
            System.out.println("Connection is unavailable");
            throw new RuntimeException(e);

        }

    }

    public void removeUserById(long id) {
        String sql = """
                DELETE
                FROM pre_project.user
                WHERE user_id = ?;
                """;
        try (var connection = Util.openConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sql = """
                SELECT *
                FROM pre_project.user;
                """;
        try (var connection = Util.openConnection();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));

                allUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Connection is unavailable");
            throw new RuntimeException(e);

        }

        return allUsers;
    }

    public void cleanUsersTable() {
        String sql = """
                DELETE FROM pre_project.user;
                """;
        try (var connection = Util.openConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Connection is unavailable");
            throw new RuntimeException(e);

        }

    }
}
