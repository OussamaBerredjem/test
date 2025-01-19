package services;

import java.sql.*;
import models.Vehicle;
import java.util.ArrayList;
import java.util.List;

public class VehicleService {

    private Connection connection;

    public VehicleService(Connection connection) {
        this.connection = connection;
    }

    // Add a new vehicle
    public boolean addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO Vehicule (marque, modele, annee, carburant, prix_location_jour, etat, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, vehicle.getmarque());
            statement.setString(2, vehicle.getModel());
            statement.setInt(3, vehicle.getYear());
            statement.setString(4, vehicle.getFuelType());
            statement.setDouble(5, vehicle.getPricePerDay());
            statement.setString(6, vehicle.getStatus());
            statement.setString(7, vehicle.getType());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get a vehicle by ID
    public Vehicle getVehicleById(int id) throws Exception {
        String query = "SELECT * FROM Vehicule WHERE id_vehicule = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Vehicle.fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all vehicles
    public List<Vehicle> getAllVehicles() throws Exception {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM Vehicule";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                vehicles.add(Vehicle.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
   // Search available vehicles by filters
public List<Vehicle> searchAvailableVehicles(String fuelType, String type, Integer year, 
String marque, String model, Double minPrice, Double maxPrice) throws Exception {
List<Vehicle> availableVehicles = new ArrayList<>();

// Start building the query
StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Vehicule WHERE LOWER(etat) = 'disponible'");

// Dynamically add conditions based on provided filters
if (fuelType != null && !fuelType.isEmpty()) {
queryBuilder.append(" AND LOWER(carburant) LIKE ? ");
}
if (type != null && !type.isEmpty()) {
queryBuilder.append(" AND LOWER(type) LIKE ? ");
}
if (year != null) {
queryBuilder.append(" AND annee = ? ");
}
if (marque != null && !marque.isEmpty()) {
queryBuilder.append(" AND LOWER(marque) LIKE ? ");
}
if (model != null && !model.isEmpty()) {
queryBuilder.append(" AND LOWER(modele) LIKE ? ");
}
if (minPrice != null) {
queryBuilder.append(" AND prix_location_jour >= ? ");
}
if (maxPrice != null) {
queryBuilder.append(" AND prix_location_jour <= ? ");
}

// Prepare the statement based on the dynamically constructed query
try (PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
int paramIndex = 1;

// Set the values dynamically based on the filters provided
if (fuelType != null && !fuelType.isEmpty()) {
statement.setString(paramIndex++, fuelType.toLowerCase() + "%");
}
if (type != null && !type.isEmpty()) {
statement.setString(paramIndex++, type.toLowerCase() + "%");
}
if (year != null) {
statement.setInt(paramIndex++, year);
}
if (marque != null && !marque.isEmpty()) {
statement.setString(paramIndex++, marque.toLowerCase() + "%");
}
if (model != null && !model.isEmpty()) {
statement.setString(paramIndex++, model.toLowerCase() + "%");
}
if (minPrice != null) {
statement.setDouble(paramIndex++, minPrice);
}
if (maxPrice != null) {
statement.setDouble(paramIndex++, maxPrice);
}

// Execute the query and process the results
ResultSet rs = statement.executeQuery();
while (rs.next()) {
availableVehicles.add(Vehicle.fromResultSet(rs));
}
} catch (SQLException e) {
e.printStackTrace();
}

return availableVehicles;
}


    // Remove a vehicle by ID after removing related reservations by vehicle ID
    public boolean removeVehicleById(int vehicleId) throws SQLException {
        // First, remove related reservations using the ReservationService
        ReservationService reservationService = new ReservationService(connection);
        boolean reservationsRemoved = reservationService.removeReservationByVehicleId(vehicleId);

        if (!reservationsRemoved) {
            return false; // If removal of reservations fails, return false
        }

        // Now, remove the vehicle
        String query = "DELETE FROM Vehicule WHERE id_vehicule = ?";
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
