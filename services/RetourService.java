package services;

import java.sql.*;
import models.Retour;
import java.util.ArrayList;
import java.util.List;

public class RetourService {

    private Connection connection;

    public RetourService(Connection connection) {
        this.connection = connection;
    }

    // Add a new return record
    public boolean addRetour(Retour retour) {
        String query = "INSERT INTO Retour (id_reservation, date_retour, etat_retour, frais_supplementaires) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, retour.getReservationId());
            statement.setDate(2, retour.getReturnDate());
            statement.setString(3, retour.getCondition());
            statement.setDouble(4, retour.getAdditionalCharges());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get return details by reservation ID
    public Retour getRetourByReservationId(int reservationId) throws Exception {
        String query = "SELECT * FROM Retour WHERE id_reservation = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Retour.fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get a list of all vehicle returns
    public List<Retour> getRetourList() throws Exception {
        List<Retour> retourList = new ArrayList<>();
        String query = "SELECT * FROM Retour";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                retourList.add(Retour.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retourList;
    }

    // Get a list of vehicle returns for a specific client (via client id)
    public List<Retour> getRetourListByClient(int clientId) throws Exception {
        List<Retour> retourList = new ArrayList<>();
        String query = "SELECT * FROM Retour WHERE id_reservation IN (SELECT id_reservation FROM Reservation WHERE id_client = ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, clientId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                retourList.add(Retour.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retourList;
    }
}
