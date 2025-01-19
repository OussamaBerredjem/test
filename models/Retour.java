package models;

import java.sql.Date;

public class Retour {
    private int id;
    private int reservationId;
    private Date returnDate;
    private String condition;
    private double additionalCharges;

    // Constructor
    public Retour(int id, int reservationId, Date returnDate, String condition, double additionalCharges) {
        this.id = id;
        this.reservationId = reservationId;
        this.returnDate = returnDate;
        this.condition = condition;
        this.additionalCharges = additionalCharges;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(double additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    // SQL to Object Mapping Method
    public static Retour fromResultSet(java.sql.ResultSet rs) throws Exception {
        return new Retour(
            rs.getInt("id_retour"),
            rs.getInt("id_reservation"),
            rs.getDate("date_retour"),
            rs.getString("etat_retour"),
            rs.getDouble("frais_supplementaires")
        );
    }
}
