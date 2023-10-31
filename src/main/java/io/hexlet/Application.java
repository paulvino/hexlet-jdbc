package io.hexlet;

import java.sql.DriverManager;
import java.sql.SQLException;
// import java.sql.Statement;

public class Application {
    public static void main(String[] args) throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            var sql = "CREATE TABLE users "
                + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            try (var statement = conn.createStatement()) {
                statement.execute(sql);
            }

//            var sql2 = "INSERT INTO users (username, phone) VALUES (?, ?)";
//            try (var preparedStatement = conn.prepareStatement(sql2)) {
//                preparedStatement.setString(1, "Tommy");
//                preparedStatement.setString(2, "123456789");
//                preparedStatement.executeUpdate();
//
//                preparedStatement.setString(1, "Maria");
//                preparedStatement.setString(2, "987654321");
//                preparedStatement.executeUpdate();
//            }
//
//            var sql3 = "INSERT INTO users (username, phone) VALUES (?, ?)";
//            try (var preparedStatement = conn.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS)) {
//                preparedStatement.setString(1, "Sarah");
//                preparedStatement.setString(2, "333333333");
//                preparedStatement.executeUpdate();
//                // Если ключ составной, значений может быть несколько
//                // В нашем случае, ключ всего один
//                var generatedKeys = preparedStatement.getGeneratedKeys();
//                if (generatedKeys.next()) {
//                    System.out.println(generatedKeys.getLong(1));
//                } else {
//                    throw new SQLException("DB have not returned an id after saving the entity");
//                }
//            }
//
//            var sql5 = "DELETE FROM users WHERE username = ?";
//            try (var preparedStatement = conn.prepareStatement(sql5)) {
//                preparedStatement.setString(1, "Maria");
//                preparedStatement.executeUpdate();
//            }
//
//            var sql6 = "SELECT * FROM users";
//            try (var statement4 = conn.createStatement()) {
//                var resultSet2 = statement4.executeQuery(sql6);
//                while (resultSet2.next()) {
//                    System.out.printf(
//                            "%s %s\n",
//                            resultSet2.getString("username"),
//                            resultSet2.getString("phone"));
//                }
//            }
            UserDAO dao = new UserDAO(conn);
            User tommy = new User("Tommy", "123456789");
            User sarah = new User("Sarah", "987654321");
            User maria = new User("Maria", "555555555");
            User ana = new User("Ana", "111111111");

            dao.save(tommy);
            dao.save(sarah);
            dao.save(maria);
            dao.save(ana);

            dao.delete(maria);

            var sql1 = "SELECT * FROM users";
            try (var statement4 = conn.createStatement()) {
                var resultSet2 = statement4.executeQuery(sql1);
                while (resultSet2.next()) {
                    System.out.printf(
                            "%s %s\n",
                            resultSet2.getString("username"),
                            resultSet2.getString("phone"));
                }
            }
        }
    }
}
