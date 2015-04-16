package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.Item;
import controleur.Controleur;
import controleur.ControleurTest;

public class MainPanel extends JFrame{

	//##############################################################################################
	//						Attribut Model
	//##############################################################################################

	Item itemSelected = new Item();
	ArrayList<Item> listItems = new ArrayList();
	
	//##############################################################################################
	//							Variable d'environment graphique
	//##############################################################################################

	private static final long serialVersionUID = 1L;

	private JLabel photo = new JLabel();

	private JButton rechercherButton = new JButton("Rechercher"),
			 		ajouterButton 	= new JButton("Ajouter"),
		 			supprimerButton = new JButton("Supprimer"),
					updateButton 	= new JButton("Mettre à jour"),
					purgerButton 	= new JButton("Purger"),
					annulerButton 	= new JButton("Annuler"),
					okButton 		= new JButton("OK"),
					quitterButton 	= new JButton("Quitter"),
					goButton 		= new JButton("GO");
	
	private JTable itemTable = new JTable();

	private JComboBox<String> encanBox= new JComboBox<String>();
	
	private JRadioButton fermeRadio = new JRadioButton("Fermé"),
						payeRadio = new JRadioButton("Payé");
	
	private JTextField 	noUniqueTextField = new JTextField(),
					 	noItemTextField = new JTextField(),
					 	titreTextField = new JTextField(),
					 	donateurTextField = new JTextField(),
					 	valeurTextField = new JTextField(),
					 	prixDepartTextField = new JTextField(),
					 	incrementTextField = new JTextField(),
					 	achatImediatTextField = new JTextField();
	
	private JTextArea  descriptionTextField = new JTextArea("");
	
	private Dimension 	dimLabelShort = new Dimension(40,20),
						dimLabelMedium = new Dimension(80,20),
						dimLabelLong = new Dimension(80,20);
	

	private JPanel conteneur = new JPanel();
	private final int espacementX =15, espacementY=15;
	private ControleurTest controleur = new ControleurTest();
	
	//##############################################################################################
	//					CONSTRUCTEUR
	//##############################################################################################

	public MainPanel() {
		super();
		
		this.setTitle("Box Layout");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 430);
		this.setResizable(false);

		this.listItems = controleur.getAllItem();
		
		conteneur.setLayout(new BoxLayout(conteneur, BoxLayout.PAGE_AXIS));
		conteneur.add(initTabItemPanel());
		conteneur.add(initIdentificationItemPanel());
		conteneur.add(initInfoItemPanel());
		conteneur.add(initButtonControlPanel());

		if(!this.listItems.isEmpty()){
			this.itemSelected = this.listItems.get(0);
			this.updateDataView(this.itemSelected);
		}

		this.setContentPane(this.conteneur);

		initLookFeelOS();
		this.setVisible(true);
	}
	
	//##############################################################################################
	//							INIT PANNELS
	//##############################################################################################

	private JPanel initTabItemPanel(){
		JPanel tabItemPanel= new JPanel();
		tabItemPanel.setPreferredSize(new Dimension(this.getWidth(),220));
		tabItemPanel.setLayout(new BoxLayout(tabItemPanel, BoxLayout.PAGE_AXIS));
		
		JLabel itemLabel = new JLabel("Items : ");
		itemLabel.setAlignmentX(0);
		tabItemPanel.add(itemLabel);
		

		Object[][] data = new Object[listItems.size()][5];
		if(!this.listItems.isEmpty()){
			int i=0;
			for (Item item : listItems) {
				data[i] = item.getIdentificationInfo();
				System.out.println(data[i]);
				i++;
			}
		}
		
		String title[] = {"No unique", "Encan", "No","Titre","Valeur"};
		itemTable = new JTable(data, title);
		
		
		tabItemPanel.add(new JScrollPane(this.itemTable));

		return tabItemPanel;
	}
	
	private JPanel initIdentificationItemPanel(){
		JPanel idItemPanel = new JPanel();
		idItemPanel.setPreferredSize(new Dimension(this.getWidth(),40));
		
		JLabel 	noUniqueLabel = new JLabel("No unique : "),
				encanLabel = new JLabel("Encans : "),
				noItemLabel = new JLabel("No item :"),
				titreLabel = new JLabel("Titre : ");
		
		idItemPanel.add(noUniqueLabel);		
		noUniqueTextField.setPreferredSize(dimLabelShort);
		idItemPanel.add(noUniqueTextField);
		idItemPanel.add(goButton);
		
		idItemPanel.add(encanLabel);
		encanBox.setPreferredSize(dimLabelLong);
		idItemPanel.add(encanBox);
		
		idItemPanel.add(noItemLabel);
		noItemTextField.setPreferredSize(dimLabelShort);
		idItemPanel.add(noItemTextField);
		
		idItemPanel.add(titreLabel);
		titreTextField.setPreferredSize(dimLabelLong);
		idItemPanel.add(titreTextField);
		
		return idItemPanel;
	}
	
	private JPanel initInfoItemPanel(){
		JPanel infoItemPanel = new JPanel();
		infoItemPanel.setPreferredSize(new Dimension(this.getWidth(),200));
		infoItemPanel.setLayout(new BoxLayout(infoItemPanel, BoxLayout.LINE_AXIS));
		
			//Panel Left-Label
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
		leftBoxPanel.setPreferredSize(new Dimension(this.getWidth()/4 +50,220));
		leftBoxPanel.add(buildEditArea(new JLabel("Donateur :"), donateurTextField, 0));
		leftBoxPanel.add(Box.createRigidArea(new Dimension(0,20)));
		leftBoxPanel.add(buildEditArea(new JLabel("Description :"), scroolTextArea));
		leftBoxPanel.add(Box.createRigidArea(new Dimension(0,13)));
		
			//Panel Center
		JPanel centerBoxPanel = new JPanel();
		centerBoxPanel.setPreferredSize(new Dimension(this.getWidth()/4-20,220));
		centerBoxPanel.setLayout(new BoxLayout(centerBoxPanel, BoxLayout.PAGE_AXIS));
		
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.LINE_AXIS));
		comboPanel.add(fermeRadio);
		comboPanel.add(Box.createRigidArea(new Dimension(30,0)));
		comboPanel.add(payeRadio);
		
		centerBoxPanel.add(comboPanel);
		centerBoxPanel.add(Box.createRigidArea(new Dimension(0,10)));
		centerBoxPanel.add(buildEditArea(new JLabel("Valeur :"), valeurTextField, 1));
		centerBoxPanel.add(Box.createRigidArea(new Dimension(0,espacementY)));
		centerBoxPanel.add(buildEditArea(new JLabel("Prix Depart :"), prixDepartTextField, 1));
		centerBoxPanel.add(Box.createRigidArea(new Dimension(0,espacementY)));
		centerBoxPanel.add(buildEditArea(new JLabel("Incrément :"), incrementTextField, 1));
		centerBoxPanel.add(Box.createRigidArea(new Dimension(0,espacementY)));
		centerBoxPanel.add(buildEditArea(new JLabel("Achat Imediat :"), achatImediatTextField, 1));
		centerBoxPanel.add(Box.createRigidArea(new Dimension(0,espacementY)));
		
			//Photo
		photo.setPreferredSize(new Dimension(this.getWidth()/4 -30,100));
		photo.setBorder(BorderFactory.createCompoundBorder(
                				BorderFactory.createLineBorder(Color.red),
                				photo.getBorder()));

		infoItemPanel.add(Box.createRigidArea(new Dimension(espacementX,0)));
		infoItemPanel.add(leftBoxPanel);
		infoItemPanel.add(Box.createRigidArea(new Dimension(espacementX,0)));
		infoItemPanel.add(centerBoxPanel);
		infoItemPanel.add(Box.createRigidArea(new Dimension(espacementX,0)));
		infoItemPanel.add(this.photo);
		infoItemPanel.add(Box.createRigidArea(new Dimension(espacementX,0)));
		
		return infoItemPanel;
	}
	
	private JPanel initButtonControlPanel(){
		JPanel controlItem= new JPanel();
		controlItem.setLayout(new BoxLayout(controlItem, BoxLayout.LINE_AXIS));
		
		controlItem.add(rechercherButton);
		controlItem.add(ajouterButton);
		controlItem.add(supprimerButton);
		controlItem.add(updateButton);
		controlItem.add(purgerButton);
		controlItem.add(annulerButton);
		controlItem.add(okButton);
		controlItem.add(quitterButton);
		
		rechercherButton.setEnabled(true);
		ajouterButton.setEnabled(true);
		updateButton.setEnabled(false);
		purgerButton.setEnabled(true);
		annulerButton.setEnabled(false);
		okButton.setEnabled(false);
		quitterButton.setEnabled(true);
		
		rechercherButton.addActionListener(new ButtonsHandler());
		ajouterButton.addActionListener(new ButtonsHandler());
		updateButton.addActionListener(new ButtonsHandler());
		purgerButton.addActionListener(new ButtonsHandler());
		annulerButton.addActionListener(new ButtonsHandler());
		okButton.addActionListener(new ButtonsHandler());
		quitterButton.addActionListener(new ButtonsHandler());
		
		return controlItem;
	}
	
	//##############################################################################################
	//						CONCTRUIT PANNEAU LABEL + TEXTFIELD
	//##############################################################################################

	private JPanel buildEditArea(JLabel label, JTextField textField, int style){
		JPanel idItemPanel = new JPanel();
		idItemPanel.setLayout(new BoxLayout(idItemPanel, BoxLayout.LINE_AXIS));
		
		if(style==0){
			label.setPreferredSize(this.dimLabelMedium);
			textField.setPreferredSize(new Dimension(110,20));
		}else if(style==1){
			label.setPreferredSize(this.dimLabelLong);
		}
		idItemPanel.add(label);
		idItemPanel.add(textField);
		
		return idItemPanel;
	}
	
	private JPanel buildEditArea(JLabel label, JScrollPane textField){
		JPanel idItemPanel = new JPanel();
		idItemPanel.setLayout(new BoxLayout(idItemPanel, BoxLayout.LINE_AXIS));
		
		label.setPreferredSize(this.dimLabelMedium);
		textField.setPreferredSize(new Dimension(110,150));
		
		idItemPanel.add(label);
		idItemPanel.add(textField);
		
		return idItemPanel;
	}

	//##############################################################################################
	//					CONCTRUIT PANNEAU LABEL + TEXTFIELD
	//##############################################################################################


	public class ButtonsHandler implements ActionListener{
		Item item;
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(((JButton)e.getSource()).getText()){
				case "GO" : 
					controleur.finditem();
					break;
					
				case "Recherche" : 	
					controleur.finditem();
					break;
					
				case "Ajouter" : 
					
					rechercherButton.setEnabled(false);
					ajouterButton.setEnabled(false);
					updateButton.setEnabled(false);
					purgerButton.setEnabled(false);
					annulerButton.setEnabled(true);
					okButton.setEnabled(true);
					
					break;
					
				case "Supprimer" : 
					controleur.supprimeItem(0);
					break;
					
				case "Mettre à jour" : 
					controleur.updateItem(getDataView());
					break;
					
				case "Purger" : 
					controleur.purgeItems();
					break;
					
				case "Annuler" : 
					updateDataView(itemSelected);
							
					rechercherButton.setEnabled(true);
					ajouterButton.setEnabled(true);
					updateButton.setEnabled(false);
					purgerButton.setEnabled(false);
					annulerButton.setEnabled(false);
					okButton.setEnabled(false);
					
					break;
					
				case "OK" : 
					controleur.ajoutItem(getDataView());
					break;
					
				case "Quitter" :
					System.exit(0);	
					break;
			}
		}
	}
	
	
	//##########################################################################################################	
	//								DATA - VIEW
	//##########################################################################################################
	

	private void updateDataView(Item item){
		if(item == null){
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
		}
		else{
			encanBox.setSelectedItem(item.getNameEncan());
			titreTextField.setText(item.getTitre());
			donateurTextField.setText(item.getDonateur());
			descriptionTextField.setText(item.getDescription());
			noUniqueTextField.setText(item.getNoUnique()+"");
			noItemTextField.setText(item.getNoItem()+"");
			valeurTextField.setText(item.getValeur()+"");
			prixDepartTextField.setText(item.getPrixDepart()+"");
			incrementTextField.setText(item.getIncrement()+"");
			achatImediatTextField.setText(item.getAchatImmediat()+"");
			photo.setText(item.getImagePath());
			fermeRadio.setSelected(item.isEstFerme());
			payeRadio.setSelected(item.isEstPaye());
		}
	}
	
	private Item getDataView(){
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

	//##########################################################################################################
	//						UTILISER "LOOK & FEEL" DU SYSTEME D'EXPLOITATION
	//##########################################################################################################

	private void initLookFeelOS()
	{
		try 
		{
			//On force à utiliser le « look and feel » du système
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());	
			//Ici on force tous les composants de notre fenêtre à se redessiner avec le « look and feel » du système
			SwingUtilities.updateComponentTreeUI(this);
		}
		catch (InstantiationException e) {}
		catch (ClassNotFoundException e) {}
		catch (UnsupportedLookAndFeelException e) {}
		catch (IllegalAccessException e) {}
	}

}

