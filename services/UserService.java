package services;

import java.sql.*;
import models.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private Connection connection;

    public UserService(Connection connection) {
        this.connection = connection;
    }

    // Add a new user
    public boolean addUser(User user) {
        String query = "INSERT INTO Utilisateur (nom, prenom, email, mot_de_passe, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get user by ID
    public User getUserById(int id) {
        String query = "SELECT * FROM Utilisateur WHERE id_utilisateur = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return User.fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        return null;
    }

    // Login user (email and password)
    public User login(String email, String password) throws Exception {
        String query = "SELECT * FROM Utilisateur WHERE email = ? AND mot_de_passe = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return User.fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Invalid login credentials
    }

    // Get all users
    public List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Utilisateur";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                users.add(User.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
