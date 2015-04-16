package model;

public class Item {
	
	//##############################################################################################
	//							ATTRIBUTS
	//##############################################################################################

	private String nameEncan,titre, donateur,description;
	
	private int noUnique, noItem;
	private double valeur, prixDepart, increment, achatImmediat;
	
	private String imagePath;

	private boolean estFerme, estPaye;
	
	//##############################################################################################
	//							CONSTRUCTEUR
	//##############################################################################################


	public Item() {}



	//##############################################################################################
	//							ACCESSEURS
	//##############################################################################################

	public Item(String nameEncan, String titre, String donateur,
			String description, int noUnique, int noItem, double valeur,
			double prixDepart, double increment, double achatImmediat,
			String imagePath, boolean estFerme, boolean estPaye) {
		super();
		this.nameEncan = nameEncan;
		this.titre = titre;
		this.donateur = donateur;
		this.description = description;
		this.noUnique = noUnique;
		this.noItem = noItem;
		this.valeur = valeur;
		this.prixDepart = prixDepart;
		this.increment = increment;
		this.achatImmediat = achatImmediat;
		this.imagePath = imagePath;
		this.estFerme = estFerme;
		this.estPaye = estPaye;
	}



	public String getNameEncan() {
		return nameEncan;
	}

	public String getTitre() {
		return titre;
	}

	public String getDonateur() {
		return donateur;
	}

	public String getDescription() {
		return description;
	}

	public int getNoUnique() {
		return noUnique;
	}

	public int getNoItem() {
		return noItem;
	}

	public double getValeur() {
		return valeur;
	}

	public double getPrixDepart() {
		return prixDepart;
	}

	public double getIncrement() {
		return increment;
	}

	public double getAchatImmediat() {
		return achatImmediat;
	}

	public String getImagePath() {
		return imagePath;
	}

	public boolean isEstFerme() {
		return estFerme;
	}

	public boolean isEstPaye() {
		return estPaye;
	}

	public String[] getIdentificationInfo(){
		String[] tabValor = new String[5];
		tabValor[0] = this.noUnique+"";
		tabValor[1] = this.nameEncan;
		tabValor[2] = this.noItem+"";
		tabValor[3] = this.titre;
		tabValor[4] = this.valeur +"$";
		return tabValor;
		
	}
	//##############################################################################################
	//							MODIFICATEURS
	//##############################################################################################



	public void setNameEncan(String nameEncan) {
		this.nameEncan = nameEncan;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setDonateur(String donateur) {
		this.donateur = donateur;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setNoUnique(int noUnique) {
		this.noUnique = noUnique;
	}

	public void setNoItem(int noItem) {
		this.noItem = noItem;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}

	public void setPrixDepart(double prixDepart) {
		this.prixDepart = prixDepart;
	}

	public void setIncrement(double increment) {
		this.increment = increment;
	}

	public void setAchatImmediat(double achatImmediat) {
		this.achatImmediat = achatImmediat;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public void setEstFerme(boolean estFerme) {
		this.estFerme = estFerme;
	}

	public void setEstPaye(boolean estPaye) {
		this.estPaye = estPaye;
	}
	
	
}
