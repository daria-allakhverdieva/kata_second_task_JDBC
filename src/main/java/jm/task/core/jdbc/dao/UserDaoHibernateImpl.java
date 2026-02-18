package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (SessionFactory sessionFactory = Util.sessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sql = """
                    CREATE TABLE IF NOT EXISTS user (
                    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    firstName VARCHAR(255) NOT NULL,
                    lastName VARCHAR(255) NOT NULL,
                    age TINYINT NOT NULL)
                    """;

            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }

    }

    @Override
    public void dropUsersTable() {
        try (SessionFactory sessionFactory = Util.sessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sql = """
                    DROP TABLE if exists user
                    """;

            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (SessionFactory sessionFactory = Util.sessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (SessionFactory sessionFactory = Util.sessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers;
        try (SessionFactory sessionFactory = Util.sessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            allUsers = session.createQuery("SELECT u FROM User u", User.class).list();
            session.getTransaction().commit();
            return allUsers;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (SessionFactory sessionFactory = Util.sessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User u").executeUpdate();
            session.getTransaction().commit();
        }

    }
}
