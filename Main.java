

import java.awt.TextField;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import screens.AddCarPanel;
import screens.HomeScreen;
import screens.SecondTest;
import screens.Test;



public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HomeScreen home = new HomeScreen();
        frame.setContentPane(home);
        frame.setJMenuBar(home.buildMenuBar());
        frame.setVisible(true);

       
       
    }
}