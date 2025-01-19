package screens;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;
import models.Vehicle;
import services.VehicleService;

public class VehicleSearchPanel extends JPanel {

    private VehicleService vehicleService;
    private JTable vehicleTable;
    private JTextField marqueField, modelField, yearField, minPriceField, maxPriceField;
    private JComboBox<String> fuelTypeBox, typeBox;
    private DefaultTableModel tableModel;
    private JPopupMenu tableMenu;
    private int size = 0;

    public VehicleSearchPanel(Connection connection) {
        // Initialize the VehicleService
        vehicleService = new VehicleService(connection);

        // Set layout with padding
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Center Panel: Vehicle Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Marque", "Model", "Year", "Fuel Type", "Type", "Price/Day"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        vehicleTable = new JTable(tableModel);
        customizeTableAppearance(vehicleTable);

        JScrollPane tableScrollPane = new JScrollPane(vehicleTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder());  // Removes the border


        JPanel t = new JPanel();
        t.setSize(Integer.MAX_VALUE, 40);
        t.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        t.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel tPane = new JPanel();

        tPane.setLayout(new BoxLayout(tPane, BoxLayout.Y_AXIS));

        JLabel tit = new JLabel("Vehicule List");
        tit.setFont(new Font("SansSerif", Font.BOLD, 15));  // Increase font size for header
        t.setBackground(Color.WHITE);
        t.add(tit);
        tPane.add(t);
        tPane.setBackground(Color.WHITE);

        tPane.add(tableScrollPane);

        tPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),   // Border for outline
            BorderFactory.createEmptyBorder(10, 5, 10, 5)   // Padding inside the scroll pane (top, left, bottom, right)
        ));

        

        add(tPane, BorderLayout.CENTER);

        setBackground(Color.WHITE);

        // Right Panel: Search Form
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
    BorderFactory.createLineBorder(Color.BLACK, 1),   // Border for outline
    BorderFactory.createEmptyBorder(10, 10, 10, 10)   // Padding inside the scroll pane (top, left, bottom, right)
));
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBackground(Color.WHITE);

        JPanel tPanel = new JPanel();

        JLabel title = new JLabel("Search Filters");
        title.setFont(new Font("SansSerif", Font.BOLD, 15));  // Increase font size for header

        tPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        tPanel.add(title);
        tPanel.setBackground(Color.WHITE);
        searchPanel.add(tPanel);

        // Search fields
        marqueField = new JTextField();
        modelField = new JTextField();
        yearField = new JTextField();
        minPriceField = new JTextField();
        maxPriceField = new JTextField();

         fuelTypeBox = new JComboBox<>(new String[]{"","Essence", "Diesel", "Électrique", "Hybride"});
        typeBox = new JComboBox<>(new String[]{"","SUV", "Berline", "Coupé", "Hatchback", "Pick-Up"});

        searchPanel.add(createLabeledComponent("Marque", marqueField));
        searchPanel.add(createLabeledComponent("Model", modelField));
        searchPanel.add(createLabeledComponent("Year", yearField));
        searchPanel.add(createLabeledComponent("Min Price", minPriceField));
        searchPanel.add(createLabeledComponent("Max Price", maxPriceField));
        searchPanel.add(createLabeledComponent("Fuel Type", fuelTypeBox));
        searchPanel.add(createLabeledComponent("Type", typeBox));

        JPanel tl = new JPanel(new FlowLayout());

       

        JButton searchButton = createHoverableButton("Search");
        searchButton.addActionListener(e -> searchVehicles());
        searchPanel.add(Box.createVerticalStrut(10)); // Add space
        searchPanel.add(tl);

        add(searchPanel, BorderLayout.EAST);

        JButton resetButton = createHoverableButton("Reset");
        resetButton.addActionListener(e -> resetSearchFilters()); // ActionListener to reset filters
        searchPanel.add(Box.createVerticalStrut(10)); // Add space
        tl.add(searchButton);
        tl.add(resetButton); // Add the reset button to the panel
        tl.setBackground(Color.WHITE);

        // Right-Click Context Menu for Table
        setupTableContextMenu();

        // Load Initial Vehicle List
        loadAllVehicles();
    }

    private JPanel createLabeledComponent(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(labelText + ":");
        label.setPreferredSize(new Dimension(80, 25));
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(label, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panel.setOpaque(false); // Transparent background
        return panel;
    }

    private JButton createHoverableButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLUE);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.BLUE);
                System.out.println("mouse entered");
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.GRAY);
            }
        });

        return button;
    }

    private void resetSearchFilters() {
        // Reset all search fields to their default values
        marqueField.setText("");
        modelField.setText("");
        yearField.setText("");
        minPriceField.setText("");
        maxPriceField.setText("");
        fuelTypeBox.setSelectedIndex(0); // Reset combo box to default (empty value)
        typeBox.setSelectedIndex(0);     // Reset combo box to default (SUV)
    
        // Optionally, reload the full vehicle list
        loadAllVehicles(); // This method reloads all vehicles
    }

    private void customizeTableAppearance(JTable table) {
        // Increase the row height and header height
        table.setRowHeight(40);  // Set row height to 40px (increase as needed)
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(70, 130, 180));
        table.setSelectionForeground(Color.WHITE);
        table.setShowGrid(false);
        table.setGridColor(new Color(70, 130, 180));

       
        

        
        // Increase header height
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.PLAIN, 15));  // Increase font size for header
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(header.getWidth(), 27));  // Increase header height to 50px (adjust as needed)
        
        // Center-align table text (both header and cells) and add padding
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setVerticalAlignment(SwingConstants.CENTER);

      
        // Add padding to each cell (10px padding all around)
        centerRenderer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Alternate row colors, but no gray for the first row
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    // Alternate row colors, but don't apply gray to the first row
                    if (row % 2 == 0) {
                        component.setBackground(Color.WHITE);  // First row and even rows are white
                    } else {
                        component.setBackground(new Color(240, 240, 240)); // Light gray for odd rows
                    }
                }
                return component;
            }
        };
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    
        // Add hover effect on rows
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1) {
                    table.setRowSelectionInterval(row, row);
                    table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }
        });
    
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                table.clearSelection(); // Deselect when mouse leaves the table
            }
        });
    }
        private void setupTableContextMenu() {
        tableMenu = new JPopupMenu();
        JMenuItem removeItem = new JMenuItem("Remove");
        JMenuItem modifyItem = new JMenuItem("Modify");
        JMenuItem affecterItem = new JMenuItem("Affecter");

        removeItem.addActionListener(e -> removeVehicle());
        modifyItem.addActionListener(e -> modifyVehicle());
        affecterItem.addActionListener(e -> affectVehicle());

        tableMenu.add(removeItem);
        tableMenu.add(modifyItem);
        tableMenu.add(affecterItem);

        vehicleTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                showPopupMenu(e);
            }

            public void mouseReleased(MouseEvent e) {
                showPopupMenu(e);
            }

            private void showPopupMenu(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = vehicleTable.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        vehicleTable.setRowSelectionInterval(row, row);
                        tableMenu.show(vehicleTable, e.getX(), e.getY());
                    }
                }
            }
        });
    }

    private void loadAllVehicles() {
        try {
            List<Vehicle> vehicles = vehicleService.getAllVehicles();
            size = vehicles.size();
            updateTable(vehicles);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading vehicles: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchVehicles() {
        try {
            String marque = marqueField.getText();
            String model = modelField.getText();
            String fuelType = (String) fuelTypeBox.getSelectedItem();
            String type = (String) typeBox.getSelectedItem();
            Integer year = yearField.getText().isEmpty() ? null : Integer.parseInt(yearField.getText());
            Double minPrice = minPriceField.getText().isEmpty() ? null : Double.parseDouble(minPriceField.getText());
            Double maxPrice = maxPriceField.getText().isEmpty() ? null : Double.parseDouble(maxPriceField.getText());

            List<Vehicle> vehicles = vehicleService.searchAvailableVehicles(fuelType, type, year, marque, model, minPrice, maxPrice);
            size = vehicles.size();

            updateTable(vehicles);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during search: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<Vehicle> vehicles) {
        tableModel.setRowCount(0); // Clear existing data
        for (Vehicle vehicle : vehicles) {
            tableModel.addRow(new Object[]{
                    vehicle.getId(), vehicle.getmarque(), vehicle.getModel(),
                    vehicle.getYear(), vehicle.getFuelType(), vehicle.getType(), vehicle.getPricePerDay()
            });
        }
    }

    private void removeVehicle() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a vehicle to remove.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int vehicleId = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            boolean success = vehicleService.removeVehicleById(vehicleId);
            if (success) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Vehicle removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error removing vehicle: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyVehicle() {
        // Implement modification logic
    }

    private void affectVehicle() {
        // Implement affectation logic
    }
}
