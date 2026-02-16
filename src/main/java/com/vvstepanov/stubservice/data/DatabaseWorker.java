package com.vvstepanov.stubservice.data;

import com.vvstepanov.stubservice.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
@ConfigurationProperties(prefix = "db.params")
public class DatabaseWorker {
    @Value("${db.params.prefix:jdbc:postgresql}")
    private String prefix;

    @Value("${db.params.host}")
    private String host;

    @Value("${db.params.dbname}")
    private String dbName;

    @Value("${db.params.username}")
    private String username;

    @Value("${db.params.password}")
    private String password;

    private String getJdbcUrl() {
        return prefix + "://" + host + "/" + dbName;
    }

    public User selectUser(String login) throws UserNotFoundException {
        String jdbcUrl = getJdbcUrl();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
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
            } else {
                throw new UserNotFoundException("User with login " + login + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int insertUser(User user) {
        String insertLoginAndEmail = "INSERT INTO user_credentials (login, password) values (?, ?);\n" +
                "INSERT INTO user_emails (login, email) values (?, ?);";

        try (Connection connection = DriverManager.getConnection(getJdbcUrl(), username, password);
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