package tn.esprit.spring.entities;

import java.util.Date;



public class ContratDTO {

	
	private int reference;
	
	
	private Date dateDebut;
	
	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getTypeContrat() {
		return typeContrat;
	}

	public void setTypeContrat(String typeContrat) {
		this.typeContrat = typeContrat;
	}

	public Employe getEmploye() {
		return employe;
	}

	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

	public float getSalaire() {
		return salaire;
	}

	public void setSalaire(float salaire) {
		this.salaire = salaire;
	}

	private String typeContrat;
		
	
	private Employe employe;

	private float salaire;
	public ContratDTO() {
		super();
		this.getcontract();

	}
		
	public Contrat getcontract() {
		
		Contrat cont =new Contrat();
		cont.setReference(this.getReference());
		cont.setDateDebut(this.dateDebut);
		cont.setEmploye(this.getEmploye());
		cont.setSalaire(this.getSalaire());
		
		return cont;



}

}
