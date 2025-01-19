package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

public  class Test extends JPanel{ 


    private JCheckBox tennis,squash,natation,athletisme,randonne,foot,basket,volley,petanque;
    private JRadioButton homme,femme;
    private JLabel nom,prenom,adress,sexe;
    JTextField nomField,prenomField;
    private JTextArea adressField;
    private JPanel westPanel,eastPanel,footerPanel,east_footer;
    private JButton ok,annuller;

    public Test(){
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        init();
        buildWestPanel();
        buildEastPanel();
        buildFooterPanel();
    }

    private void init(){
        westPanel = new JPanel();
        eastPanel = new JPanel();
        footerPanel = new JPanel();
        east_footer = new JPanel();

        nom = new JLabel("Nom");
        prenom = new JLabel("Prenom");
        adress = new JLabel("Email");
        sexe = new JLabel("Sexe");

        nomField = new JTextField(10);
        prenomField = new JTextField(10);
        adressField = new JTextArea(40,10);

        tennis = new JCheckBox("Tennis");
        squash = new JCheckBox("Squash");
        natation = new JCheckBox("Natation");
        athletisme = new JCheckBox("Athletisme");
        randonne = new JCheckBox("Randonne");
        foot = new JCheckBox("Foot");
        basket = new JCheckBox("Basket");
        volley = new JCheckBox("Volley");
        petanque = new JCheckBox("Petanque");

        homme = new JRadioButton("Homme");
        femme = new JRadioButton("Femme");

        ok = new JButton("Ok");
        annuller = new JButton("Annuler");
    }

    private void buildEastPanel() {

        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.add(tennis);
        westPanel.add(squash);
        westPanel.add(natation);
        westPanel.add(athletisme);
        westPanel.add(randonne);
        westPanel.add(foot);
        westPanel.add(basket);
        westPanel.add(volley);
        westPanel.add(petanque);

        add(westPanel, BorderLayout.CENTER);
    }

    private void buildWestPanel() {

        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
    
        eastPanel.add(nom);
        eastPanel.add(nomField);
        eastPanel.add(prenom);
        eastPanel.add(prenomField);
        eastPanel.add(adress);
        eastPanel.add(adressField);

        east_footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        east_footer.add(sexe);
        east_footer.add(homme);
        east_footer.add(femme);

        eastPanel.add(east_footer);

        add(eastPanel, BorderLayout.WEST);
    }

    private void buildFooterPanel() {
        footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        footerPanel.add(ok);
        footerPanel.add(annuller);

        add(footerPanel, BorderLayout.SOUTH);
    }
    
}
