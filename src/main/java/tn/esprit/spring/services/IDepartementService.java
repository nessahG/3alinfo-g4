package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Departement;


public interface IDepartementService {
	
	
	public List<Departement> getAllDepartements();

	public Departement addOrUpdateDepartement(Departement departement);
	public String getDepartementName(int departementId);
	public void deletedepartementById(int departementId);

	
	

	
}
