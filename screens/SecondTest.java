package screens;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
public class SecondTest extends JPanel{

    private JButton ok,annuller;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> comboBox;
    private JLabel email,password,role;
    private JPanel footer;
    private String[] list = 
        {
            "Admin",
            "Buyer",
            "Seller"
        };

    public SecondTest(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        buildPanel();
    }

    private void buildPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        email = new JLabel("Email");
        password = new JLabel("Password");
        role = new JLabel("Role");
        emailField = new JTextField(10);
        emailField.setMaximumSize(new Dimension(400, 45));
        passwordField = new JPasswordField(10);
        passwordField.setMaximumSize(new Dimension(400, 45));

        comboBox = new JComboBox<String>(list);

        ok = new JButton("Ok");
        annuller = new JButton("Annuler");

        footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        footer.add(ok);
        footer.add(annuller);

        add(email);
        add(emailField);
        add(password);
        add(passwordField);
        add(role);
        add(comboBox);
        add(footer);

        


    }
}
