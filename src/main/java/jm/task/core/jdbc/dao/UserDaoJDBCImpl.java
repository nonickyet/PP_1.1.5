package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     CREATE TABLE `mytestbd`.`user` (
                       `id` INT NOT NULL AUTO_INCREMENT,
                       `name` VARCHAR(45) NOT NULL,
                       `lastName` VARCHAR(45) NOT NULL,
                       `age` INT NOT NULL,
                       PRIMARY KEY (`id`));
                     """)) {
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement("drop table `mytestbd`.`user`;")) {
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement("insert user(name, lastName, age) values (?, ?, ?);")) {
            Savepoint savepoint = connection.setSavepoint();
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback(savepoint);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("DELETE from user WHERE id IN (?);")) {
            Savepoint savepoint = connection.setSavepoint();
            statement.setInt(1, (int) id);
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                connection.rollback(savepoint);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from user");
             ResultSet rs = statement.executeQuery()) {
            List<User> result = new ArrayList<>();
            connection.commit();
            while (rs.next()) {
                result.add(new User(rs.getLong(1), rs.getString(2), rs.getString(3), (byte) rs.getInt(4)));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE from user")) {
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
