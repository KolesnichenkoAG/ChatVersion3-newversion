package com.geekbrains.chat.auth;


import java.sql.*;
import java.util.Set;

public class AuthService {

    private static Connection connection;
    private static Statement statement;

   /* private static Set<User> USER = Set.of(
            new User("login1", "pass1", "username1"),
            new User("login2", "pass2", "username2"),
            new User("login3", "pass3", "username3")
    );*/

    /*public String getUsernameByLoginAndPassword(String login, String password) {
        User requiredUser = new User(login, password);
        for (User user : USER) {
            if (requiredUser.equals(user)) {
                return user.getUsername();
            }
        }

        return null;
    }*/

    public String getUsernameByLoginAndPassword(String login, String password) {
        String sql = String.format("SELECT username FROM user1 WHERE login='%s' and password='%s'", login, password);
        try {
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:users1.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("org.sqlite.JDBC");
            statement = connection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeNickname(String username, String s) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("update user1 set username=? where username=?;");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, s);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
