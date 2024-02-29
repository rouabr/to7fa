package services;

import models.User;
import utils.DBConnection;

import java.sql.*;

public class ServiceUser {
    public User getUserById(int userId) {
        User user = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE user_id = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_phone"),
                        resultSet.getString("user_email"),
                        resultSet.getString("user_password"),
                        resultSet.getDate("date_birth"),
                        resultSet.getString("user_sexe"),
                        resultSet.getString("user_role"),
                        resultSet.getDate("date_creation"),
                        resultSet.getDate("date_last_login"),
                        resultSet.getString("code_check")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
