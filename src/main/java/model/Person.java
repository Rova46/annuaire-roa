package model;

public class Person {
	String date;
	String local;
	String formation;
	String secteur;
	String sexe;
	String nom;
	String prenom;
	
	public Person() {
		super();
	}

	public Person(String date, String local, String formation, String secteur, String sexe, String nom,
			String prenom) {
		super();
		this.date = date;
		this.local = local;
		this.formation = formation;
		this.secteur = secteur;
		this.sexe = sexe;
		this.nom = nom;
		this.prenom = prenom;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public String getSecteur() {
		return secteur;
	}

	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
		
}
