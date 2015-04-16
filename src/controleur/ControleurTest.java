package controleur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import model.Item;


public class ControleurTest{

	//##############################################################################################
	//							ATTRIBUTS
	//##############################################################################################

	private ArrayList<Item> listItem = new ArrayList<>();
	

	private String[] namesEncans = {"Encan Jeux Free-to-play","Encan Versions Collectors", "Encan Antiquites"};
	
	//##############################################################################################
	//							CONSTRUCTEUR
	//##############################################################################################

	public ControleurTest(){
		listItem.add(new Item(	
				"Encan Jeux Free-to-play",
				"Age of Empire",
				"Valve",
				"Traversé les ages et être le roi du nouveau monde",
				1,1,
				100.00,0.00,5.00, 500.00,
				"C:/aoe",
				true,true
			));
		listItem.add(new Item(	
				"Encan Jeux Free-to-play",
				"GTA V",
				"Sauf Une Fois",
				"Dernier opus du célèbre open world déjanté de Rockstar",
				2,2,
				50.00,0.00,5.00,400.00,
				"C:/gta",
				false, false
				
			));
		
		listItem.add(new Item(
				"Encan Antiquites",
				"Oreilles de Mickey",
				"Walt Disney",
				"Mickey se separe de sa perruque au profit d''oeuvres caritatives",
				3,1,
				5000.00, 500.00,100.00, 30000.00, 
				"C:/mickey",
				true,false
			));
		
		listItem.add(new Item(
				"Encan Antiquites", 
				"Queue de Bourriquet",
				"Walt Disney",
				"Ne fait que tomber",
				4,2,
				10.00,  1.00,  1.00, 100.00,
				"C:/bouriquet",
				true, false
			));
		
		listItem.add(new Item(
				"Encan Versions Collectors",
				"Black Pearl",
				"Valve",
				"You've seen a ship with black sails, that's crewed by the damned and captained by a man so evil that Hell itself spat him back out?",
				5,1,
				999999.99, 50000.00, 50000.00,999999.99,
				"C:/blackPearl",
				true,true
			));
		
		listItem.add(new Item(
				"Encan Versions Collectors",
				"Boite d'allumettes",
				"Sauf Une Fois",
				"Elle est en or massif quand même",
				6,2,
				499.99, 100.00, 50.00,800.00,
				"C:/alumette",
				true,false
			));

	}

	//##############################################################################################
	//							GET ALL ITEMS
	//##############################################################################################

	public ArrayList<Item> getAllItem(){
		System.out.println("###### GET ALL ITEMS #######");
		System.out.println(listItem);
		return listItem;
	}

	
	//##############################################################################################
	//							AJOUT
	//##############################################################################################
	
	public void ajoutItem(Item item){
		System.out.println("###### AJOUT ITEM #######");
		listItem.add(item);
	}

	
	//##############################################################################################
	//							SUPPRIME
	//##############################################################################################

	public void supprimeItem(int idUnique){
		System.out.println("######DELETE ITEM "+ idUnique + " #######");
		for (int i = 0; i < listItem.size(); i++) {
			if(listItem.get(i).getNoUnique() == idUnique)
				this.listItem.remove(i);
		}
	}
	
	
	//##############################################################################################
	//							UPDATE
	//##############################################################################################
	
	public void updateItem(Item item){
		System.out.println("######UPDATE ITEM#######");
		for (int i = 0; i < listItem.size(); i++) {
			if(listItem.get(i).getNoUnique() == item.getNoUnique())
				this.listItem.set(i,item);
		}

		System.out.println("=======================");
	}
	
	//##############################################################################################
	//							PURGE
	//##############################################################################################
	
	public void purgeItems(){
		System.out.println("######PURGE ITEM#######");
		

		System.out.println("=======================");
	}
	
	
	public Item finditem(){
		System.out.println("######RECHERCHE ITEM#######");
		
		System.out.println("===========================");
		return null;
	}
	

}
