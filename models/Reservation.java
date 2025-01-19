package models;

import java.sql.Date;

public class Reservation {
    private int id;
    private int vehicleId;
    private int clientId;
    private Date startDate;
    private Date endDate;
    private double totalAmount;
    private String status;

    // Constructor
    public Reservation(int id, int vehicleId, int clientId, Date startDate, Date endDate, double totalAmount, String status) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.clientId = clientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // SQL to Object Mapping Method
    public static Reservation fromResultSet(java.sql.ResultSet rs) throws Exception {
        return new Reservation(
            rs.getInt("id_reservation"),
            rs.getInt("id_vehicule"),
            rs.getInt("id_client"),
            rs.getDate("date_debut"),
            rs.getDate("date_fin"),
            rs.getDouble("montant_total"),
            rs.getString("statut")
        );
    }
}
