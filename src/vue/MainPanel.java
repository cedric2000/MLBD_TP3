package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Item;
import controleur.Controleur;
import controleur.ControleurTest;

public class MainPanel extends JFrame {

    Statement stmt;

    // ##############################################################################################
    // Attribut Model
    // ##############################################################################################

    Item itemSelected = new Item();
    List<Item> listItems = new LinkedList<Item>();
    List<String> listEncans = new LinkedList<String>();

    // ##############################################################################################
    // Variable d'environment graphique
    // ##############################################################################################

    private static final long serialVersionUID = 1L;

    private JLabel photo = new JLabel();

    private JButton rechercherButton = new JButton("Rechercher"),
            ajouterButton = new JButton("Ajouter"),
            supprimerButton = new JButton("Supprimer"),
            updateButton = new JButton("Mettre à jour"),
            purgerButton = new JButton("Purger"),
            annulerButton = new JButton("Annuler"),
            okButton = new JButton("OK"),
            quitterButton = new JButton("Quitter"),
            goButton = new JButton("GO");

    private JScrollPane itemTableScrollPane = new JScrollPane();

    private JTable itemTable = new JTable();

    private JComboBox<String> encanBox = new JComboBox<String>();

    private JRadioButton fermeRadio = new JRadioButton("Fermé"),
            payeRadio = new JRadioButton("Payé");

    private JTextField noUniqueTextField = new JTextField(),
            noItemTextField = new JTextField(),
            titreTextField = new JTextField(),
            donateurTextField = new JTextField(),
            valeurTextField = new JTextField(),
            prixDepartTextField = new JTextField(),
            incrementTextField = new JTextField(),
            achatImediatTextField = new JTextField();

    private JTextArea descriptionTextField = new JTextArea("");

    private Dimension dimComboBox = new Dimension(130, 20),
            dimLabelShort = new Dimension(40, 20),
            dimLabelMedium = new Dimension(80, 20),
            dimLabelLong = new Dimension(80, 20);

    private JPanel conteneur = new JPanel();
    private final int espacementX = 15, espacementY = 15;
    private ControleurTest controleur = new ControleurTest();

    // ##############################################################################################
    // CONSTRUCTEUR
    // ##############################################################################################

    public MainPanel() {
        super();

        if (!connexion()) {
            return;
        }

        this.setTitle("Mon encan bénéfices Inc.");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 430);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        getItems();

        conteneur.setLayout(new BoxLayout(conteneur, BoxLayout.PAGE_AXIS));
        conteneur.add(initTabItemPanel());
        conteneur.add(initIdentificationItemPanel());
        conteneur.add(initInfoItemPanel());
        conteneur.add(initButtonControlPanel());

        encanBox.setModel(new DefaultComboBoxModel(listEncans.toArray()));
        encanBox.setRenderer(new MyComboBoxRenderer());

        if (!this.listItems.isEmpty()) {
            this.itemSelected = this.listItems.get(0);
            this.updateDataView(this.itemSelected);
        }
        this.setContentPane(this.conteneur);

        initLookFeelOS();
        this.setVisible(true);
    }

    private void getItems() {
        String requeteSQL = "select e.NOM_ENC, i.TITRE_ITE, i.DONATEUR_ITE, i.DESC_ITE, i.NO_ITEM, "
                + "i.NO_ITEM_ENCAN_ITE, i.MNT_VALEUR_ITE, i.MNT_PRIX_DEPART_ITE, "
                + "i.MNT_INCREMENT_MINI_ITE, i.MNT_ACHAT_IMMEDIAT_ITE, i.CHEMIN_PHOTO_ITE, "
                + "i.EST_FERME_ITE, i.EST_PAYE_ITE from ITEM i, ENCAN e where i.NO_ENCAN = e.NO_ENCAN";
        try {
            ResultSet rs = stmt.executeQuery(requeteSQL);
            listItems = new LinkedList<Item>();
            while (rs.next()) {
                Item item = new Item();
                item.setNameEncan(rs.getString(1));
                item.setTitre(rs.getString(2));
                item.setDonateur(rs.getString(3));
                item.setDescription(rs.getString(4));
                item.setNoUnique(rs.getInt(5));
                item.setNoItem(rs.getInt(6));
                item.setValeur(rs.getDouble(7));
                item.setPrixDepart(rs.getDouble(8));
                item.setIncrement(rs.getDouble(9));
                item.setAchatImmediat(rs.getDouble(10));
                item.setImagePath(rs.getString(11));
                item.setEstFerme(rs.getBoolean(12));
                item.setEstPaye(rs.getBoolean(13));
                listItems.add(item);
                if (!listEncans.contains(rs.getString(1))) {
                    listEncans.add(rs.getString(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ##############################################################################################
    // CONNEXION
    // ##############################################################################################

    private boolean connexion() {
        String login = "";
        String password = "";
        String hostChain = "";
        int nbFailures = 0;
        boolean connected = false;

        while ((nbFailures < 2) && (!connected)) {
            try {
                login = JOptionPane.showInputDialog("Que est le nom de l'utilisateur ?");
                password = JOptionPane.showInputDialog("Quel est le mot de passe ?");
                hostChain = JOptionPane.showInputDialog("Quelle est la chaîne d'hôte ?",
                        "jdbc:oracle:thin:@ift-oracle2k3.fsg.ulaval.ca:1521:ora11g");

                if ((login == null) && (password == null)) {
                    return false;
                }
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection(hostChain, login, password);
                connected = true;
                stmt = conn.createStatement();
            } catch (Throwable e) {
                nbFailures++;
                if (e.getMessage().contains("invalid username/password")) {
                    JOptionPane.showMessageDialog(null, "La combinaison mot de passe / usager est invalide.");
                } else if (e.getMessage().contains("ORA-12505")) {
                    JOptionPane.showMessageDialog(null, "Problème de connexion à l'hôte.");
                } else {
                    e.printStackTrace();
                }
            }
        }
        return connected;
    }

    // ##############################################################################################
    // INIT PANNELS
    // ##############################################################################################

    private JPanel initTabItemPanel() {
        JPanel tabItemPanel = new JPanel();
        tabItemPanel.setPreferredSize(new Dimension(this.getWidth(), 220));
        tabItemPanel.setLayout(new BoxLayout(tabItemPanel, BoxLayout.PAGE_AXIS));

        JLabel itemLabel = new JLabel("Items : ");
        itemLabel.setAlignmentX(0);
        tabItemPanel.add(itemLabel);

        Object[][] data = new Object[listItems.size()][5];
        if (!listItems.isEmpty()) {
            int i = 0;
            for (Item item : listItems) {
                data[i] = item.getIdentificationInfo();
                i++;
            }
        }

        DefaultTableModel tm = (DefaultTableModel) itemTable.getModel();
        for (int i = 0; i < tm.getRowCount(); i++) {
            for (int j = 0; j < tm.getColumnCount(); j++) {
                tm.fireTableRowsDeleted(i, j);
            }
        }

        String title[] = {"No unique", "Encan", "No", "Titre", "Valeur"};
        TableModel dataModel = new DefaultTableModel(data, title) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        itemTable = new JTable(dataModel);

        itemTableScrollPane = new JScrollPane(itemTable);
        tabItemPanel.add(itemTableScrollPane);

        return tabItemPanel;
    }

    private JPanel initIdentificationItemPanel() {
        JPanel idItemPanel = new JPanel();
        idItemPanel.setPreferredSize(new Dimension(this.getWidth(), 40));

        JLabel noUniqueLabel = new JLabel("No unique : "), encanLabel = new JLabel("Encans : "), noItemLabel = new JLabel("No item :"), titreLabel = new JLabel(
                "Titre : ");

        idItemPanel.add(noUniqueLabel);
        noUniqueTextField.setPreferredSize(dimLabelShort);
        idItemPanel.add(noUniqueTextField);
        idItemPanel.add(goButton);

        idItemPanel.add(encanLabel);
        encanBox.setPreferredSize(dimComboBox);
        idItemPanel.add(encanBox);

        idItemPanel.add(noItemLabel);
        noItemTextField.setPreferredSize(dimLabelShort);
        idItemPanel.add(noItemTextField);

        idItemPanel.add(titreLabel);
        titreTextField.setPreferredSize(dimLabelLong);
        idItemPanel.add(titreTextField);

        return idItemPanel;
    }

    private JPanel initInfoItemPanel() {
        JPanel infoItemPanel = new JPanel();
        infoItemPanel.setPreferredSize(new Dimension(this.getWidth(), 200));
        infoItemPanel.setLayout(new BoxLayout(infoItemPanel, BoxLayout.LINE_AXIS));

        // Panel Left-Label
        descriptionTextField = new JTextArea("What if none of the components has a maximum width?"
                + " In this case, if all the components have identical X alignment, then all components "
                + "are made as wide as their container. If the X alignments are different,");

        descriptionTextField.setWrapStyleWord(true);
        descriptionTextField.setColumns(30);
        descriptionTextField.setLineWrap(true);
        descriptionTextField.setRows(10);
        descriptionTextField.setWrapStyleWord(true);

        JScrollPane scroolTextArea = new JScrollPane(descriptionTextField);

        JPanel leftBoxPanel = new JPanel();
        leftBoxPanel.setLayout(new BoxLayout(leftBoxPanel, BoxLayout.PAGE_AXIS));
        leftBoxPanel.setPreferredSize(new Dimension(this.getWidth() / 4 + 50, 220));
        leftBoxPanel.add(buildEditArea(new JLabel("Donateur :"), donateurTextField, 0));
        leftBoxPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftBoxPanel.add(buildEditArea(new JLabel("Description :"), scroolTextArea));
        leftBoxPanel.add(Box.createRigidArea(new Dimension(0, 13)));

        // Panel Center
        JPanel centerBoxPanel = new JPanel();
        centerBoxPanel.setPreferredSize(new Dimension(this.getWidth() / 4 - 20, 220));
        centerBoxPanel.setLayout(new BoxLayout(centerBoxPanel, BoxLayout.PAGE_AXIS));

        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.LINE_AXIS));
        comboPanel.add(fermeRadio);
        comboPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        comboPanel.add(payeRadio);

        centerBoxPanel.add(comboPanel);
        centerBoxPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerBoxPanel.add(buildEditArea(new JLabel("Valeur :"), valeurTextField, 1));
        centerBoxPanel.add(Box.createRigidArea(new Dimension(0, espacementY)));
        centerBoxPanel.add(buildEditArea(new JLabel("Prix Depart :"), prixDepartTextField, 1));
        centerBoxPanel.add(Box.createRigidArea(new Dimension(0, espacementY)));
        centerBoxPanel.add(buildEditArea(new JLabel("Incrément :"), incrementTextField, 1));
        centerBoxPanel.add(Box.createRigidArea(new Dimension(0, espacementY)));
        centerBoxPanel.add(buildEditArea(new JLabel("Achat Imediat :"), achatImediatTextField, 1));
        centerBoxPanel.add(Box.createRigidArea(new Dimension(0, espacementY)));

        // Photo
        photo.setPreferredSize(new Dimension(this.getWidth() / 4 - 30, 100));
        photo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                photo.getBorder()));

        infoItemPanel.add(Box.createRigidArea(new Dimension(espacementX, 0)));
        infoItemPanel.add(leftBoxPanel);
        infoItemPanel.add(Box.createRigidArea(new Dimension(espacementX, 0)));
        infoItemPanel.add(centerBoxPanel);
        infoItemPanel.add(Box.createRigidArea(new Dimension(espacementX, 0)));
        infoItemPanel.add(this.photo);
        infoItemPanel.add(Box.createRigidArea(new Dimension(espacementX, 0)));

        return infoItemPanel;
    }

    private JPanel initButtonControlPanel() {
        JPanel controlItem = new JPanel();
        controlItem.setLayout(new BoxLayout(controlItem, BoxLayout.LINE_AXIS));

        controlItem.add(rechercherButton);
        controlItem.add(ajouterButton);
        controlItem.add(supprimerButton);
        controlItem.add(updateButton);
        controlItem.add(purgerButton);
        controlItem.add(annulerButton);
        controlItem.add(okButton);
        controlItem.add(quitterButton);

        goButton.setEnabled(true);
        rechercherButton.setEnabled(true);
        ajouterButton.setEnabled(true);
        supprimerButton.setEnabled(false);
        updateButton.setEnabled(false);
        purgerButton.setEnabled(true);
        annulerButton.setEnabled(false);
        okButton.setEnabled(false);
        quitterButton.setEnabled(true);

        goButton.addActionListener(new ButtonsHandler());
        rechercherButton.addActionListener(new ButtonsHandler());
        ajouterButton.addActionListener(new ButtonsHandler());
        supprimerButton.addActionListener(new ButtonsHandler());
        updateButton.addActionListener(new ButtonsHandler());
        purgerButton.addActionListener(new ButtonsHandler());
        annulerButton.addActionListener(new ButtonsHandler());
        okButton.addActionListener(new ButtonsHandler());
        quitterButton.addActionListener(new ButtonsHandler());

        return controlItem;
    }

    // ##############################################################################################
    // CONCTRUIT PANNEAU LABEL + TEXTFIELD
    // ##############################################################################################

    private JPanel buildEditArea(JLabel label, JTextField textField, int style) {
        JPanel idItemPanel = new JPanel();
        idItemPanel.setLayout(new BoxLayout(idItemPanel, BoxLayout.LINE_AXIS));

        if (style == 0) {
            label.setPreferredSize(this.dimLabelMedium);
            textField.setPreferredSize(new Dimension(110, 20));
        } else if (style == 1) {
            label.setPreferredSize(this.dimLabelLong);
        }
        idItemPanel.add(label);
        idItemPanel.add(textField);

        return idItemPanel;
    }

    private JPanel buildEditArea(JLabel label, JScrollPane textField) {
        JPanel idItemPanel = new JPanel();
        idItemPanel.setLayout(new BoxLayout(idItemPanel, BoxLayout.LINE_AXIS));

        label.setPreferredSize(this.dimLabelMedium);
        textField.setPreferredSize(new Dimension(110, 150));

        idItemPanel.add(label);
        idItemPanel.add(textField);

        return idItemPanel;
    }

    // ##############################################################################################
    // CONCTRUIT PANNEAU LABEL + TEXTFIELD
    // ##############################################################################################

    public class ButtonsHandler implements ActionListener {
        Item item;

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (((JButton) e.getSource()).getText()) {
                case "GO":
                    go();
                    break;

                case "Rechercher":
                    rechercher();
                    break;

                case "Ajouter":
                    ajouter();
                    break;

                case "Supprimer":
                    supprimer();
                    break;

                case "Mettre à jour":
                    maj();
                    break;

                case "Purger":
                    System.out.println("Purger");
                    controleur.purgeItems();
                    break;

                case "Annuler":
                    annuler();
                    break;

                case "OK":
                    ok();
                    break;

                case "Quitter":
                    System.exit(0);
                    break;
            }
        }
    }

    // ##########################################################################################################
    // DATA - VIEW
    // ##########################################################################################################

    private void updateDataView(Item item) {
        if (item == null) {
            encanBox.setSelectedItem(0);
            titreTextField.setText("");
            donateurTextField.setText("");
            descriptionTextField.setText("");
            noUniqueTextField.setText("");
            noItemTextField.setText("");
            valeurTextField.setText("");
            prixDepartTextField.setText("");
            incrementTextField.setText("");
            achatImediatTextField.setText("");
            photo.setText("");
            fermeRadio.setSelected(false);
            payeRadio.setSelected(false);

            updateButton.setEnabled(false);
            supprimerButton.setEnabled(false);
        } else {
            encanBox.setSelectedItem(item.getNameEncan());
            titreTextField.setText(item.getTitre());
            donateurTextField.setText(item.getDonateur());
            descriptionTextField.setText(item.getDescription());
            noUniqueTextField.setText(item.getNoUnique() + "");
            noItemTextField.setText(item.getNoItem() + "");
            valeurTextField.setText(item.getValeur() + "");
            prixDepartTextField.setText(item.getPrixDepart() + "");
            incrementTextField.setText(item.getIncrement() + "");
            achatImediatTextField.setText(item.getAchatImmediat() + "");
            photo.setText(item.getImagePath());
            fermeRadio.setSelected(item.isEstFerme());
            payeRadio.setSelected(item.isEstPaye());

            supprimerButton.setEnabled(true);
            updateButton.setEnabled(true);
            itemSelected = item;
        }

    }

    private void updateDataTable(List<Item> items) {

        Object[][] data = new Object[items.size()][5];
        if (!items.isEmpty()) {
            int i = 0;
            for (Item item : items) {
                data[i] = item.getIdentificationInfo();
                i++;
            }
        }

        String title[] = {"No unique", "Encan",
                "No", "Titre", "Valeur"};
        TableModel dataModel = new DefaultTableModel(data, title) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int
                    column) {
                return false;
            }
        };

        itemTable.setModel(dataModel);
    }

    private Item getDataView() {
        return new Item(
                encanBox.getSelectedItem().toString(),
                titreTextField.getText(),
                donateurTextField.getText(),
                descriptionTextField.getText(),
                Integer.valueOf(noUniqueTextField.getText()),
                Integer.valueOf(noItemTextField.getText()),
                Double.valueOf(valeurTextField.getText()),
                Double.valueOf(prixDepartTextField.getText()),
                Double.valueOf(incrementTextField.getText()),
                Double.valueOf(achatImediatTextField.getText()),
                photo.getText(),
                fermeRadio.isSelected(),
                payeRadio.isSelected());
    }

    // ##########################################################################################################
    // UTILISER "LOOK & FEEL" DU SYSTEME D'EXPLOITATION
    // ##########################################################################################################

    private void initLookFeelOS()
    {
        try
        {
            // On force � utiliser le � look and feel � du syst�me
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Ici on force tous les composants de notre fen�tre � se redessiner avec le � look and feel � du syst�me
            SwingUtilities.updateComponentTreeUI(this);
        } catch (InstantiationException e) {
        } catch (ClassNotFoundException e) {
        } catch (UnsupportedLookAndFeelException e) {
        } catch (IllegalAccessException e) {
        }
    }

    // ##########################################################################################################
    // METHODES D ACTION
    // ##########################################################################################################

    private void go() {
        String noUniqueText = noUniqueTextField.getText();
        try {
            int noUnique = Integer.parseInt(noUniqueText);
            for (Item i : listItems) {
                if (i.getNoUnique() == noUnique) {
                    updateDataView(i);
                    return;
                }
            }
            updateDataView(null);
        } catch (NumberFormatException e) {
            updateDataView(null);
        }
    }

    private void rechercher() {
        String titre = "";
        String description = "";
        String donateur = "";
        List<Item> items = new LinkedList<Item>();
        for (Item i : listItems) {
            items.add(i);
        }

        titre = JOptionPane.showInputDialog("Quel est le titre de l'item ?");
        description = JOptionPane.showInputDialog("Quelle est la description de l'item ?");
        donateur = JOptionPane.showInputDialog("Quel est le donateur de l'item ?");

        if (titre == null) {
            titre = "";
        }
        if (description == null) {
            description = "";
        }
        if (donateur == null) {
            donateur = "";
        }

        for (int i = items.size() - 1; i >= 0; i--) {
            if (!(titre.isEmpty()) && !(items.get(i).getTitre().toLowerCase().contains(titre.toLowerCase()))) {
                items.remove(i);

            } else if (!(description.isEmpty())
                    && !(items.get(i).getTitre().toLowerCase().contains(description.toLowerCase()))) {
                items.remove(i);
            } else if (!(donateur.isEmpty())
                    && !(items.get(i).getTitre().toLowerCase().contains(donateur.toLowerCase()))) {
                items.remove(i);
            }
        }

        updateDataTable(items);
        if (items.size() > 0) {
            updateDataView(items.get(0));
        } else {
            updateDataView(null);
        }
    }

    private void ajouter() {
        updateDataView(null);
        goButton.setEnabled(false);
        rechercherButton.setEnabled(false);
        ajouterButton.setEnabled(false);
        updateButton.setEnabled(false);
        purgerButton.setEnabled(false);
        annulerButton.setEnabled(true);
        okButton.setEnabled(true);
    }

    private void ok() {
        if (!isDataViewCorrect()) {
            return;
        }
        int noUnique = Integer.parseInt(noUniqueTextField.getText());
        if (existeNoUnique(noUnique)) {
            JOptionPane.showMessageDialog(null, "Le numéro unique de l'item existe déjà.");
            return;
        }
        int noItem = Integer.parseInt(noItemTextField.getText());
        if (existeNoItem(encanBox.getSelectedItem().toString(), noItem)) {
            JOptionPane.showMessageDialog(null, "Le numéro d'item exite déjà pour cet encan.");
            return;
        }

        Item item = getDataView();
        ajouterItemBD(item);
        listItems.add(item);
        JOptionPane.showMessageDialog(null, "Item ajouté : " + titreTextField.getText() + ".");
        updateDataTable(listItems);
        updateDataView(item);
    }

    private boolean isDataViewCorrect() {
        if (noUniqueTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le numéro unique de l'item est vide.");
            return false;
        } else if (noItemTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le numéro d'item est vide.");
            return false;
        } else if (titreTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le titre de l'item est vide.");
            return false;
        } else if (valeurTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La valeur de l'item est vide.");
            return false;
        } else if (prixDepartTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le prix de départ de l'item est vide.");
            return false;
        } else if (incrementTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le montant d'incrément de l'item est vide.");
            return false;
        } else if (achatImediatTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le montant d'achat immédiat de l'item est vide.");
            return false;
        }

        String noUniqueText = noUniqueTextField.getText();
        String noItemText = noItemTextField.getText();
        int noItem = 0;
        try {
            Integer.parseInt(noUniqueText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Le numéro unique de l'item est mal formaté.");
            return false;
        }
        try {
            noItem = Integer.parseInt(noItemText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Le numéro d'item est mal formaté.");
            return false;
        }

        try {
            Double.valueOf(valeurTextField.getText());
            Double.valueOf(prixDepartTextField.getText());
            Double.valueOf(incrementTextField.getText());
            Double.valueOf(achatImediatTextField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Un des montant de l'item (valeur, prix départ, "
                    + "incrément ou achat immédiat) est mal formaté (xx.xx).");
            return false;
        }
        return true;
    }

    private void ajouterItemBD(Item item) {
        String requeteSQL = "select NO_ENCAN from ENCAN where NOM_ENC = '" + item.getNameEncan() + "'";
        int noEncan = 0;
        try {
            ResultSet rs = stmt.executeQuery(requeteSQL);
            rs.next();
            noEncan = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            requeteSQL = "insert into ITEM (NO_ITEM, NO_ENCAN, NO_ITEM_ENCAN_ITE, TITRE_ITE, CHEMIN_PHOTO_ITE, DESC_ITE, "
                    + "DONATEUR_ITE, MNT_VALEUR_ITE, MNT_PRIX_DEPART_ITE, MNT_INCREMENT_MINI_ITE, MNT_ACHAT_IMMEDIAT_ITE, "
                    + "EST_FERME_ITE, EST_PAYE_ITE) values ("
                    + item.getNoUnique() + ", " + noEncan + ", " + item.getNoItem() + ", '" + item.getTitre() + "', ";
            if (item.getImagePath() == null) {
                requeteSQL += "null, ";
            } else {
                requeteSQL += "'" + item.getImagePath() + "', ";
            }

            if (item.getDescription() == null) {
                requeteSQL += "null, ";
            } else {
                requeteSQL += "'" + item.getDescription() + "', ";
            }

            requeteSQL += "'" + item.getDonateur() + "', " + item.getValeur() + ", " + item.getPrixDepart() + ", "
                    + item.getIncrement() + ", " + item.getAchatImmediat() + ", ";

            requeteSQL += item.isEstFerme() ? 1 : 0;
            requeteSQL += ", ";
            requeteSQL += item.isEstPaye() ? 1 : 0;
            requeteSQL += ")";

            stmt.executeUpdate(requeteSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean existeNoItem(String nomEncan, int noItem) {
        for (Item i : listItems) {
            if ((i.getNameEncan().equals(nomEncan)) && (i.getNoItem() == noItem)) {
                return true;
            }
        }
        return false;
    }

    private boolean existeNoUnique(int noUnique) {
        for (Item i : listItems) {
            if (i.getNoUnique() == noUnique) {
                return true;
            }
        }
        return false;
    }

    private void supprimer() {
        if (!noUniqueTextField.getText().isEmpty()) {
            try {
                int noUnique = Integer.parseInt(noUniqueTextField.getText());
                Item item = itemParNoUnique(noUnique);
                String requeteSQL = "delete from ITEM where NO_ITEM = " + noUnique;
                int res = JOptionPane.showConfirmDialog(null, "Confirmer suppression de l'item '" + item.getTitre() + "' ?");
                if (res != 0) {
                    return;
                }
                stmt.executeUpdate(requeteSQL);
                JOptionPane.showMessageDialog(null, "L'item '" + item.getTitre() + "' a bien été supprimé.");
                listItems.remove(item);
                updateDataTable(listItems);
                updateDataView(listItems.get(0));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Mauvais numéro unique.");
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Mauvais numéro unique.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Item itemParNoUnique(int noUnique) {
        for (Item i : listItems) {
            if (i.getNoUnique() == noUnique) {
                return i;
            }
        }
        return null;
    }

    private void maj() {
        if (!isDataViewCorrect()) {
            return;
        }
        int noItem = Integer.parseInt(noItemTextField.getText());
        if ((noItem != itemSelected.getNoItem())
                && (existeNoItem(itemSelected.getNameEncan(), noItem))) {
            JOptionPane.showMessageDialog(null, "Ce numéro d'item existe déjà pour l'encan '"
                    + itemSelected.getNameEncan() + "'.");
            return;
        }

        Item newItem = new Item(itemSelected.getNameEncan(), itemSelected.getTitre(), itemSelected.getDonateur(),
                itemSelected.getDescription(), itemSelected.getNoUnique(), itemSelected.getNoItem(), itemSelected.getValeur(),
                itemSelected.getPrixDepart(), itemSelected.getIncrement(), itemSelected.getAchatImmediat(), itemSelected.getImagePath(),
                itemSelected.isEstFerme(), itemSelected.isEstPaye());
        String requeteSQL = "select NO_ENCAN from ENCAN where NOM_ENC = '" + itemSelected.getNameEncan() + "'";
        int noEncan = 0;
        try {
            ResultSet rs = stmt.executeQuery(requeteSQL);
            rs.next();
            noEncan = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int nbChangement = 0;
        requeteSQL = "update ITEM set ";
        if (!encanBox.getSelectedItem().toString().equals(itemSelected.getNameEncan())) {
            if (nbChangement > 0) {
                requeteSQL += ", ";
            }
            requeteSQL += "NO_ENCAN = " + noEncan;
            nbChangement++;
            newItem.setNameEncan(encanBox.getSelectedItem().toString());
        }
        if (noItem != itemSelected.getNoItem()) {
            if (nbChangement > 0) {
                requeteSQL += ", ";
            }
            requeteSQL += "NO_ITEM_ENCAN_ITE = " + noItem;
            nbChangement++;
            newItem.setNoItem(noItem);
        }
        if (!titreTextField.getText().equals(itemSelected.getTitre())) {
            if (nbChangement > 0) {
                requeteSQL += ", ";
            }
            requeteSQL += "TITRE_ITE = '" + titreTextField.getText() + "'";
            nbChangement++;
            newItem.setTitre(titreTextField.getText());
        }
        if (!descriptionTextField.getText().equals(itemSelected.getDescription())) {
            if (nbChangement > 0) {
                requeteSQL += ", ";
            }
            requeteSQL += "DESC_ITE = '" + descriptionTextField.getText() + "'";
            nbChangement++;
            newItem.setDescription(descriptionTextField.getText());
        }
        if (!donateurTextField.getText().equals(itemSelected.getDonateur())) {
            if (nbChangement > 0) {
                requeteSQL += ", ";
            }
            requeteSQL += "DONATEUR_ITE = '" + donateurTextField.getText() + "'";
            nbChangement++;
            newItem.setDonateur(donateurTextField.getText());
        }

        if (Double.valueOf(valeurTextField.getText()) != itemSelected.getValeur()) {
            if (nbChangement > 0) {
                requeteSQL += ", ";
            }
            requeteSQL += "MNT_VALEUR_ITE = " + Double.valueOf(valeurTextField.getText());
            nbChangement++;
            newItem.setValeur(Double.valueOf(valeurTextField.getText()));
        }
        if (Double.valueOf(prixDepartTextField.getText()).equals(itemSelected.getValeur())) {
            if (nbChangement > 0) {
                requeteSQL += ", ";
            }
            requeteSQL += "MNT_PRIX_DEPART_ITE = " + Double.valueOf(prixDepartTextField.getText());
            nbChangement++;
            newItem.setPrixDepart(Double.valueOf(prixDepartTextField.getText()));
        }
        if (Double.valueOf(incrementTextField.getText()).equals(itemSelected.getValeur())) {
            if (nbChangement > 0) {
                requeteSQL += ", ";
            }
            requeteSQL += "MNT_INCREMENT_MINI_ITE = " + Double.valueOf(incrementTextField.getText());
            nbChangement++;
            newItem.setIncrement(Double.valueOf(incrementTextField.getText()));
        }
        if (Double.valueOf(achatImediatTextField.getText()).equals(itemSelected.getValeur())) {
            if (nbChangement > 0) {
                requeteSQL += ", ";
            }
            requeteSQL += "MNT_ACHAT_IMMEDIAT_ITE = " + Double.valueOf(achatImediatTextField.getText());
            nbChangement++;
            newItem.setAchatImmediat(Double.valueOf(achatImediatTextField.getText()));
        }

        if (fermeRadio.isSelected() != itemSelected.isEstFerme()) {
            if (nbChangement > 0) {
                requeteSQL += ", ";
            }
            requeteSQL += "EST_FERME_ITE = " + (fermeRadio.isSelected() ? 1 : 0);
            nbChangement++;
            newItem.setEstFerme(fermeRadio.isSelected());
        }
        if (payeRadio.isSelected() != itemSelected.isEstPaye()) {
            if (nbChangement > 0) {
                requeteSQL += ", ";
            }
            requeteSQL += "EST_PAYE_ITE = " + (payeRadio.isSelected() ? 1 : 0);
            nbChangement++;
            newItem.setEstPaye(payeRadio.isSelected());
        }
        requeteSQL += " where NO_ITEM = " + itemSelected.getNoItem();

        if (nbChangement == 0) {
            return;
        }

        try {
            stmt.executeUpdate(requeteSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listItems.remove(itemSelected);
        listItems.add(newItem);

        JOptionPane.showMessageDialog(null, "L'item '" + itemSelected.getTitre() + "' a été mis à jour.");
        updateDataView(newItem);
        updateDataTable(listItems);
    }

    private void annuler() {
        updateDataView(itemSelected);

        goButton.setEnabled(true);
        rechercherButton.setEnabled(true);
        ajouterButton.setEnabled(true);
        updateButton.setEnabled(false);
        purgerButton.setEnabled(false);
        annulerButton.setEnabled(false);
        okButton.setEnabled(false);
    }

    // ##########################################################################################################
    // MODELE DE RENDU DE LA COMBOBOX
    // ##########################################################################################################

    class MyComboBoxRenderer extends BasicComboBoxRenderer {
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
                if (-1 < index) {
                    list.setToolTipText(listEncans.toArray()[index].toString());
                }
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setFont(list.getFont());
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

}
