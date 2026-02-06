package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl user = new UserServiceImpl();
        user.createUsersTable();
        user.saveUser("Daria", "Allakhverdieva", (byte) 24);
        user.saveUser("Raphael", "Allakhverdiev", (byte) 1);
        user.saveUser("Roman", "Allakhverdiev", (byte) 25);
        user.saveUser("Elena", "Allakhverdieva", (byte) 30);
        System.out.println("--------------------------------------------");
        System.out.println(user.getAllUsers());
        user.cleanUsersTable();
        user.dropUsersTable();




        // реализуйте алгоритм здесь
    }
}
