package screens;

import javax.swing.*;

// import models.Vehicle;
// import models.VehiculeEtat;
// import services.VehicleService;

import java.awt.*;
import java.sql.Connection;


public class AddCarPanel extends JPanel {

    public AddCarPanel(Connection connection) {
        // Définir la mise en page du panneau
        setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel("Ajouter une Voiture", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Formulaire pour saisir les informations de la voiture
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Champs du formulaire
        formPanel.add(new JLabel("Marque:"));
        JTextField brandField = new JTextField();
        formPanel.add(brandField);

        formPanel.add(new JLabel("Modèle:"));
        JTextField modelField = new JTextField();
        formPanel.add(modelField);

        formPanel.add(new JLabel("Année:"));
        JTextField yearField = new JTextField();
        formPanel.add(yearField);

        formPanel.add(new JLabel("Type de Véhicule:"));
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"SUV", "Berline", "Coupé", "Hatchback", "Pick-Up"});
        formPanel.add(typeCombo);

        formPanel.add(new JLabel("Type de Carburant:"));
        JComboBox<String> fuelTypeCombo = new JComboBox<>(new String[]{"Essence", "Diesel", "Électrique", "Hybride"});
        formPanel.add(fuelTypeCombo);

        formPanel.add(new JLabel("Prix de Location (par jour):"));
        JTextField priceField = new JTextField();
        formPanel.add(priceField);

        formPanel.add(new JLabel("État:"));
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Disponible", "Loué", "En Maintenance"});
        formPanel.add(statusCombo);

        add(formPanel, BorderLayout.CENTER);

        // Boutons d'action
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Ajouter");
        JButton resetButton = new JButton("Réinitialiser");
        buttonPanel.add(addButton);
        buttonPanel.add(resetButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Actions des boutons
        addButton.addActionListener(e -> {
            
            String brand = brandField.getText();
            String model = modelField.getText();
            String year = yearField.getText();
            String type = (String) typeCombo.getSelectedItem();
            String fuelType = (String) fuelTypeCombo.getSelectedItem();
            String price = priceField.getText();
            String status = (String) statusCombo.getSelectedItem();

            // add verhicule to db
            // VehicleService service = new VehicleService(connection);
            // service.addVehicle(
            //     new Vehicle(4,brand, model,
            //      Integer.parseInt(year),
            //      fuelType, Integer.parseInt(price),
            //      VehiculeEtat.disponible,type));

            // Afficher les données (remplacez cette partie par la logique de sauvegarde si nécessaire)
            JOptionPane.showMessageDialog(this,
                    "Voiture ajoutée:\n" +
                            "Marque: " + brand + "\n" +
                            "Modèle: " + model + "\n" +
                            "Année: " + year + "\n" +
                            "Type: " + type + "\n" +
                            "Carburant: " + fuelType + "\n" +
                            "Prix: " + price + " / jour\n" +
                            "État: " + status,
                    "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        });

        resetButton.addActionListener(e -> {
            brandField.setText("");
            modelField.setText("");
            yearField.setText("");
            priceField.setText("");
            typeCombo.setSelectedIndex(0);
            fuelTypeCombo.setSelectedIndex(0);
            statusCombo.setSelectedIndex(0);
        });
    }
}
