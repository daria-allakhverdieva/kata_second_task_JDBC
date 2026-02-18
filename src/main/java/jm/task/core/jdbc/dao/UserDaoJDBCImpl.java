package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        //language=MySQL
        String sql = """
                CREATE TABLE pre_project.user(
                  user_id SERIAL PRIMARY KEY ,
                  first_name VARCHAR(40) NOT NULL ,
                  last_name VARCHAR(40),
                  user_age TINYINT NOT NULL
                );
                """;
        try (Connection connection = Util.openConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }


    }

    @Override
    public void dropUsersTable() {
        //language=MySQL
        String sql = """
                DROP TABLE IF EXISTS user;
                """;
        try (Connection connection = Util.openConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Connection is unavailable");
            throw new RuntimeException(e);

        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                INSERT INTO pre_project.user (first_name, last_name, user_age)
                VALUES (?, ?, ?);
                """;
        try (Connection connection = Util.openConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных.");
        } catch (SQLException e) {
            System.out.println("Connection is unavailable");
            throw new RuntimeException(e);

        }

    }

    @Override
    public void removeUserById(long id) {
        String sql = """
                DELETE
                FROM pre_project.user
                WHERE user_id = ?;
                """;
        try (Connection connection = Util.openConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sql = """
                SELECT *
                FROM pre_project.user;
                """;
        try (Connection connection = Util.openConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
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

    @Override
    public void cleanUsersTable() {
        String sql = """
                DELETE FROM pre_project.user;
                """;
        try (Connection connection = Util.openConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Connection is unavailable");
            throw new RuntimeException(e);

        }
    }
}
