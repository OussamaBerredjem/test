package TextFeild;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextFeildComponent extends JPanel {
    
    public JTextField text;

    public TextFeildComponent(String label) {
        // Set the layout of the panel
        setLayout(new FlowLayout(FlowLayout.CENTER));
        
        // Create the label with the text passed
        JLabel jlabel = new JLabel(label);
        jlabel.setEnabled(false); // Disable label (as per your code)

        try {
            Font robotoFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/path/to/Roboto-Regular.ttf"));
            jlabel.setFont(robotoFont.deriveFont(15f)); // Set font size to 24px
        } catch (Exception e) {
            jlabel.setFont(new Font("Arial", Font.PLAIN, 15)); // Fallback if Roboto is not found
        }
        jlabel.setPreferredSize(new Dimension(60, 35)); // Constrain width and height for the TextField

        add(jlabel);
        
        // Create the text field and set a preferred size
        text = new JTextField();
        text.setPreferredSize(new Dimension(250, 35)); // Constrain width and height for the TextField
        add(text);

        // Set a maximum size for the panel, limiting it to a fixed height
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // This will constrain the height
    }
}