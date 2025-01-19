package services;

import java.sql.*;
import models.Client;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private Connection connection;

    public ClientService(Connection connection) {
        this.connection = connection;
    }

    // Add a new client
    public boolean addClient(Client client) {
        String query = "INSERT INTO Client (nom, prenom, telephone, email, numero_permis) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getPhone());
            statement.setString(4, client.getEmail());
            statement.setString(5, client.getLicenseNumber());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get a client by ID
    public Client getClientById(int id) throws Exception {
        String query = "SELECT * FROM Client WHERE id_client = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Client.fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all clients
    public List<Client> getAllClients() throws Exception {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM Client";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                clients.add(Client.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    // Search clients based on filters (e.g., name, phone, email, license number)
    public List<Client> searchClients(String firstName, String lastName, String phone, String email, String licenseNumber) throws Exception {
        List<Client> clients = new ArrayList<>();

        // Start building the query
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Client WHERE 1=1");

        // Dynamically add conditions based on provided filters
        if (firstName != null && !firstName.isEmpty()) {
            queryBuilder.append(" OR nom LIKE ?");
        }
        if (lastName != null && !lastName.isEmpty()) {
            queryBuilder.append(" OR prenom LIKE ?");
        }
        if (phone != null && !phone.isEmpty()) {
            queryBuilder.append(" OR telephone LIKE ?");
        }
        if (email != null && !email.isEmpty()) {
            queryBuilder.append(" OR email LIKE ?");
        }
        if (licenseNumber != null && !licenseNumber.isEmpty()) {
            queryBuilder.append(" OR numero_permis LIKE ?");
        }

        try (PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
            int paramIndex = 1;

            // Set values dynamically based on the filters provided
            if (firstName != null && !firstName.isEmpty()) {
                statement.setString(paramIndex++, "%" + firstName + "%");  // Use % for partial matching
            }
            if (lastName != null && !lastName.isEmpty()) {
                statement.setString(paramIndex++, "%" + lastName + "%");
            }
            if (phone != null && !phone.isEmpty()) {
                statement.setString(paramIndex++, "%" + phone + "%");
            }
            if (email != null && !email.isEmpty()) {
                statement.setString(paramIndex++, "%" + email + "%");
            }
            if (licenseNumber != null && !licenseNumber.isEmpty()) {
                statement.setString(paramIndex++, "%" + licenseNumber + "%");
            }

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                clients.add(Client.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }
}
