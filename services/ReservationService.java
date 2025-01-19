package services;

import java.sql.*;
import models.Reservation;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private Connection connection;

    public ReservationService(Connection connection) {
        this.connection = connection;
    }

    // Add a new reservation
    public boolean addReservation(Reservation reservation) {
        String query = "INSERT INTO Reservation (id_vehicule, id_client, date_debut, date_fin, montant_total, statut) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservation.getVehicleId());
            statement.setInt(2, reservation.getClientId());
            statement.setDate(3, reservation.getStartDate());
            statement.setDate(4, reservation.getEndDate());
            statement.setDouble(5, reservation.getTotalAmount());
            statement.setString(6, reservation.getStatus());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get reservation by ID
    public Reservation getReservationById(int id) throws Exception {
        String query = "SELECT * FROM Reservation WHERE id_reservation = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Reservation.fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get reservation history for a specific client
    public List<Reservation> getReservationHistory(int clientId) throws Exception {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM Reservation WHERE id_client = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, clientId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                reservations.add(Reservation.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // Get all reservations
    public List<Reservation> getAllReservations() throws Exception {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM Reservation";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                reservations.add(Reservation.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // Remove a reservation by ID
    public boolean removeReservation(int id) throws SQLException {
        String query = "DELETE FROM Reservation WHERE id_reservation = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Remove reservations by vehicle ID
    public boolean removeReservationByVehicleId(int vehicleId) throws SQLException {
        String query = "DELETE FROM Reservation WHERE id_vehicule = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
