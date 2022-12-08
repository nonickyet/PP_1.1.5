package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/mytestbd";
    private static final String user = "root";
    private static final String password = "1290";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration cfg = new Configuration();
                Properties prop = new Properties();
                prop.put("hibernate.connection.url", url);
                prop.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");
                prop.setProperty("hibernate.connection.username", user);
                prop.setProperty("hibernate.connection.password", password);
                prop.setProperty("hibernate.hbm2ddl.auto", "create");
                prop.setProperty("hibernate.enable_lazy_load_no_trans", "true");
                cfg.addAnnotatedClass(User.class);
                cfg.setProperties(prop);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(cfg.getProperties()).build();
                return sessionFactory = cfg.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}