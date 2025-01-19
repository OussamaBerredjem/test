package models;

public class Vehicle {
    private int id;
    private String marque;
    private String model;
    private int year;
    private String fuelType;
    private double pricePerDay;
    private String status;
    private String type;

    // Constructor
    public Vehicle(int id, String marque, String model, int year, String fuelType, double pricePerDay, String status,String type) {
        this.id = id;
        this.marque = marque;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.pricePerDay = pricePerDay;
        this.status = status;
        this.type = type;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmarque() {
        return marque;
    }

    public void setmarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFuelType() {
        return fuelType;
    }
    public String getType() {
        return type;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setType(String type) {
        this.type = type;
    }

    // SQL to Object Mapping Method
    public static Vehicle fromResultSet(java.sql.ResultSet rs) throws Exception {
        return new Vehicle(
            rs.getInt("id_vehicule"),
            rs.getString("marque"),
            rs.getString("modele"),
            rs.getInt("annee"),
            rs.getString("carburant"),
            rs.getDouble("prix_location_jour"),
            rs.getString("etat"),
            rs.getString("type")
            
        );
    }
}
