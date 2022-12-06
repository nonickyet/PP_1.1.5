package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = new Util().getConnection();

    public void createUsersTable() {
        try (var statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE `mytestbd`.`user` (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastName` VARCHAR(45) NOT NULL,
                      `age` INT NOT NULL,
                      PRIMARY KEY (`id`));
                    """);
        } catch (SQLException e) {
        }
    }

    public void dropUsersTable() {
        try (var statement = connection.createStatement()) {
            statement.execute("drop table `mytestbd`.`user`;");
        } catch (SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (var statement = connection.createStatement()) {
            statement.execute("insert user(name, lastName, age) values ('" + name + "','" + lastName + "', " + age + ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (var statement = connection.createStatement()) {
            statement.execute("DELETE from user WHERE id IN (" + id + ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try (var statement = connection.prepareStatement("select * from user")) {
            List<User> result = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result.add(new User(rs.getLong(1), rs.getString(2), rs.getString(3), (byte) rs.getInt(4)));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (var statement = connection.createStatement()) {
            statement.execute("DELETE from user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
