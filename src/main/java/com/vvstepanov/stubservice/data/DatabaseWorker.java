package com.vvstepanov.stubservice.data;

import java.sql.*;

public class DatabaseWorker {
    private static final String JDBC_URL = "jdbc:postgresql://192.168.0.13:5432/mydatabase";
    private static final String USERNAME = "myuser";
    private static final String PASSWORD = "mypassword";

    public User selectUser(String login) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();) {

            String selectSql = "SELECT t1.*, t2.email\n" +
                    "FROM user_credentials AS t1 JOIN user_emails AS t2 \n" +
                    "ON t1.login = t2.login\n" +
                    "WHERE t1.login = '" + login + "';";

            ResultSet resultSet = statement.executeQuery(selectSql);
            if (resultSet.next()) {
                return new User(resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("registration_date"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return null;
    }

    public int insertUser(User user) {
        String insertLoginAndEmail = "INSERT INTO user_credentials (login, password) values (?, ?);\n" +
                "INSERT INTO user_emails (login, email) values (?, ?);";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(insertLoginAndEmail)) {

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getEmail());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}