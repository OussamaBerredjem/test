package models;

public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String licenseNumber;

    // Constructor
    public Client(int id, String firstName, String lastName, String phone, String email, String licenseNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.licenseNumber = licenseNumber;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    // SQL to Object Mapping Method
    public static Client fromResultSet(java.sql.ResultSet rs) throws Exception {
        return new Client(
            rs.getInt("id_client"),
            rs.getString("nom"),
            rs.getString("prenom"),
            rs.getString("telephone"),
            rs.getString("email"),
            rs.getString("numero_permis")
        );
    }
}