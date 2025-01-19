package screens;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class HomeScreen extends JPanel {
    private JPanel east, west, footer, top, center;
    private JMenuBar menu;
    private JMenu file, editor, view;
    private JMenuItem newItem, openItem, closeItem;
    private JCheckBoxMenuItem nom, prenom, adress, sexe;
    private JRadioButtonMenuItem autoSave, manualSave;
    private ButtonGroup group;

    private String[] noms = {"Bensalah", "Djouhri", "Belkacem", "Mahrez", "Zidane"};
    private String[] prenoms = {"Amine", "Sofia", "Karim", "Yasmine", "Rachid"};
    private String[] adresses = {"Alger", "Oran", "Constantine", "Tizi Ouzou", "Annaba"};
    private String[] sexes = {"Male", "Female"};

    private Random random = new Random();
    // Use ArrayList for header columns
    private List<String> header = new ArrayList<>();
    private List<Map<String, String>> dataList = new ArrayList<>();

    private void initList() {
        for (int i = 0; i < noms.length; i++) {
            Map<String, String> entry = new HashMap<>();
            entry.put("nom", noms[i]);
            entry.put("prenom", prenoms[i]);
            entry.put("adress", adresses[i]);
            int s = random.nextInt(2); // Random index for sexes
            entry.put("sexe", sexes[s]);

            dataList.add(entry);
        }
    }

    public HomeScreen() {
        setLayout(new BorderLayout());
        buildWestPanel();
        initList();
        header.add("Nom");
        header.add("Prenom");
        header.add("Adress");
        header.add("Sexe");
        buildCenterPanel();
    }

    public JMenuBar buildMenuBar() {
        menu = new JMenuBar();
        file = new JMenu("File");
        editor = new JMenu("Editor");
        view = new JMenu("View");

        newItem = new JMenuItem("New");
        openItem = new JMenuItem("Open");
        closeItem = new JMenuItem("Close");
        nom = new JCheckBoxMenuItem("Nom", true);
        prenom = new JCheckBoxMenuItem("Prenom", true);
        adress = new JCheckBoxMenuItem("Adress", true);
        sexe = new JCheckBoxMenuItem("Sexe", true);
        autoSave = new JRadioButtonMenuItem("Auto Save", true);
        manualSave = new JRadioButtonMenuItem("Manual Save");
        group = new ButtonGroup();

        autoSave.addItemListener(e -> handleRadioButtonSelection());
        manualSave.addItemListener(e -> handleRadioButtonSelection());

        nom.addItemListener(e -> {
            if (e.getStateChange() != ItemEvent.SELECTED) {
                for (var p : dataList) {
                    p.remove("nom");
                }
                header.remove("Nom");
            } else {
                for (var p : dataList) {
                    p.put("nom", noms[random.nextInt(noms.length)]);
                }
                if (!header.contains("Nom")) {
                    header.add("Nom");
                }
            }
            buildCenterPanel();
        });

        prenom.addItemListener(e -> {
            if (e.getStateChange() != ItemEvent.SELECTED) {
                for (var p : dataList) {
                    p.remove("prenom");
                }
                header.remove("Prenom");
            } else {
                for (var p : dataList) {
                    p.put("prenom", prenoms[random.nextInt(prenoms.length)]);
                }
                if (!header.contains("Prenom")) {
                    header.add("Prenom");
                }
            }
            buildCenterPanel();
        });

        adress.addItemListener(e -> {
            if (e.getStateChange() != ItemEvent.SELECTED) {
                for (var p : dataList) {
                    p.remove("adress");
                }
                header.remove("Adress");
            } else {
                for (var p : dataList) {
                    p.put("adress", adresses[random.nextInt(adresses.length)]);
                }
                if (!header.contains("Adress")) {
                    header.add("Adress");
                }
            }
            buildCenterPanel();
        });

        sexe.addItemListener(e -> {
            if (e.getStateChange() != ItemEvent.SELECTED) {
                for (var p : dataList) {
                    p.remove("sexe");
                }
                header.remove("Sexe");
            } else {
                for (var p : dataList) {
                    p.put("sexe", sexes[random.nextInt(sexes.length)]);
                }
                if (!header.contains("Sexe")) {
                    header.add("Sexe");
                }
            }
            buildCenterPanel();
        });

        group.add(autoSave);
        group.add(manualSave);

        file.add(newItem);
        file.add(openItem);
        file.add(closeItem);

        editor.add(autoSave);
        editor.add(manualSave);

        view.add(nom);
        view.add(prenom);
        view.add(adress);
        view.add(sexe);

        menu.add(file);
        menu.add(editor);
        menu.add(view);

        return menu;
    }

    private void handleRadioButtonSelection() {
        if (autoSave.isSelected()) {
            System.out.println("Auto Save is selected");
        } else {
            System.out.println("Manual Save is selected");
        }
    }

    private void buildCenterPanel() {
        if (center != null) {
            remove(center);
        }

        center = new JPanel();
        DefaultTableModel model = new DefaultTableModel(header.toArray(new String[0]), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Map<String, String> r : dataList) {
            String[] row = new String[header.size()];
            for (int i = 0; i < header.size(); i++) {
                row[i] = r.get(header.get(i).toLowerCase());
            }
            model.addRow(row);
        }

        JTable table = new JTable(model);
        center.add(new JScrollPane(table));
        add(center, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void buildWestPanel() {
        west = new JPanel();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Home");
        DefaultMutableTreeNode seller = new DefaultMutableTreeNode("Sellers");
        DefaultMutableTreeNode admins = new DefaultMutableTreeNode("Admins");
        DefaultMutableTreeNode buyers = new DefaultMutableTreeNode("Buyers");

        DefaultMutableTreeNode oussama = new DefaultMutableTreeNode("Oussama");
        DefaultMutableTreeNode mohamed = new DefaultMutableTreeNode("Mohamed");

        DefaultMutableTreeNode yassine = new DefaultMutableTreeNode("Yassine");
        DefaultMutableTreeNode ilyes = new DefaultMutableTreeNode("Ilyes");

        DefaultMutableTreeNode imad = new DefaultMutableTreeNode("Imad");
        DefaultMutableTreeNode youcef = new DefaultMutableTreeNode("Youcef");

        seller.add(oussama);
        seller.add(mohamed);

        buyers.add(yassine);
        buyers.add(ilyes);

        admins.add(imad);
        admins.add(youcef);

        root.add(admins);
        root.add(seller);
        root.add(buyers);

        JTree tree = new JTree(root);
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }

        tree.addTreeSelectionListener(e -> {
            TreePath path = e.getPath();
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (selectedNode.isLeaf()) {
                System.out.println(selectedNode.getUserObject());
            }
        });

        JScrollPane scrollPane = new JScrollPane(tree);
        west.add(scrollPane);
        add(west, BorderLayout.WEST);
    }

    private void buildFooterPanel() {
        footer = new JPanel();
        add(footer, BorderLayout.SOUTH);
    }

    private void buildMenu() {
        top = new JPanel();
        add(top, BorderLayout.NORTH);
    }
}
