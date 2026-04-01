package io.hexlet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {

        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            try (var statement = conn.createStatement()) {
                statement.execute(sql);
            }

            var userDAO = new UserDAO(conn);

            var user1 = new User("Tommy", "123456789");
            var user2 = new User("Maria", "44444444");
            var user3 = new User("John", "55555555");

            userDAO.save(user1);
            userDAO.save(user2);
            userDAO.save(user3);

            System.out.println("Сохраненные пользователи:");
            System.out.println(user1);
            System.out.println(user2);
            System.out.println(user3);

            System.out.println("\nУдаляем пользователя с ID=2...");
            userDAO.delete(2L);

            var found = userDAO.find(2L);
            if (found.isEmpty()) {
                System.out.println("Пользователь с ID=2 не найден (успешно удален)");
            }

            System.out.println("\nУдаляем пользователя " + user1.getUsername() + "...");
            userDAO.delete(user1);

            var found2 = userDAO.find(1L);
            if (found2.isEmpty()) {
                System.out.println("Пользователь с ID=1 не найден");
            }
        }
    }
}