package vue;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainPanel extends JFrame{

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
	
	private ButtonGroup groupOpenClose = new ButtonGroup();
	private JRadioButton fermeRadio = new JRadioButton("Fermé"),
						ouvertRadio = new JRadioButton("Ouvert");
	
	private JTextField 	noUniqueTextField = new JTextField(),
					 	noItemTextField = new JTextField(),
					 	titreTextField = new JTextField(),
					 	donateurTextField = new JTextField(),
					 	descriptionTextField = new JTextField(),
					 	valeurTextField = new JTextField(),
					 	prixDepartTextField = new JTextField(),
					 	incrementTextField = new JTextField(),
					 	achatImediatTextField = new JTextField();
	
	private Dimension 	dimLabelShort = new Dimension(40,20),
						dimLabelMedium = new Dimension(60,20),
						dimLabelLong = new Dimension(100,20);
	

	private JPanel conteneur = new JPanel();

	private Controleur controleur = new Controleur();
	
	//##############################################################################################
	//					CONSTRUCTEUR
	//##############################################################################################

	public MainPanel() {
		super();
		
		this.setTitle("Box Layout");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 400);
		this.setResizable(false);

		conteneur.setLayout(new BoxLayout(conteneur, BoxLayout.PAGE_AXIS));
		conteneur.add(initTabItemPanel());
		conteneur.add(initIdentificationItemPanel());
		conteneur.add(initInfoItemPanel());
		conteneur.add(initButtonControlPanel());
		this.setContentPane(this.conteneur);

		initLookFeelOS();
		this.setVisible(true);
	}
	
	//##############################################################################################
	//							INIT PANNELS
	//##############################################################################################

	private JPanel initTabItemPanel(){
		JPanel tabItemPanel= new JPanel();
		tabItemPanel.setPreferredSize(new Dimension(this.getWidth(),200));
		tabItemPanel.setLayout(new BoxLayout(tabItemPanel, BoxLayout.PAGE_AXIS));
		
		tabItemPanel.add(new JLabel("Items : "));
		tabItemPanel.add(this.itemTable);
		
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
		JPanel leftBoxPanel = new JPanel();
		leftBoxPanel.setLayout(new BoxLayout(leftBoxPanel, BoxLayout.PAGE_AXIS));
		leftBoxPanel.setPreferredSize(new Dimension(this.getWidth()/4,220));
		leftBoxPanel.add(buildEditArea(new JLabel("Donateur"), donateurTextField, 0));
		leftBoxPanel.add(buildEditArea(new JLabel("Descr"), descriptionTextField, 2));
		infoItemPanel.add(leftBoxPanel);
		
			//Panel Center
		JPanel centerBoxPanel = new JPanel();
		centerBoxPanel.setPreferredSize(new Dimension(this.getWidth()/4,220));
		centerBoxPanel.setLayout(new BoxLayout(centerBoxPanel, BoxLayout.PAGE_AXIS));
		
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.LINE_AXIS));
		groupOpenClose.add(fermeRadio);
		groupOpenClose.add(ouvertRadio);
		comboPanel.add(fermeRadio);
		comboPanel.add(ouvertRadio);
		centerBoxPanel.add(comboPanel);
		
		centerBoxPanel.add(buildEditArea(new JLabel("Valeur :"), valeurTextField, 1));
		centerBoxPanel.add(buildEditArea(new JLabel("Prix Depart :"), prixDepartTextField, 1));
		centerBoxPanel.add(buildEditArea(new JLabel("Incrément :"), incrementTextField, 1));
		centerBoxPanel.add(buildEditArea(new JLabel("Achat Imediat :"), achatImediatTextField, 1));
		infoItemPanel.add(centerBoxPanel);
		
			//Photo
		photo.setPreferredSize(new Dimension(this.getWidth()/4,220));
		infoItemPanel.add(this.photo);
		
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
			textField.setPreferredSize(this.dimLabelLong);
		}else if(style==1){
			label.setPreferredSize(this.dimLabelLong);
			textField.setPreferredSize(this.dimLabelMedium);
		}else{
			label.setPreferredSize(this.dimLabelMedium);
			textField.setPreferredSize(new Dimension(60,80));
		}
		textField.setMargin(new Insets(10,10,10,10));
		idItemPanel.add(label);
		idItemPanel.add(textField);
		
		return idItemPanel;
	}

	//##############################################################################################
	//					CONCTRUIT PANNEAU LABEL + TEXTFIELD
	//##############################################################################################

	public class ButtonsHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(((JButton)e.getSource()).getText()){
				case "GO" : 
					controleur.finditem();
					break;
				case "Recherche" : 	
					controleur.recherche();
					break;
				case "Ajouter" : 
					controleur.ajout();
					break;
				case "Supprimer" : 
					controleur.supprime();
					break;
				case "Mettre à jour" : 
					controleur.update();
					break;
				case "Purger" : 
					controleur.purger();
					break;
				case "Annuler" : 
					break;
				case "OK" : 
					break;
				case "Quitter" : 
					break;
			}
		}
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

