package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Contrat;


public interface IContratService {
	
	
	public List<Contrat> getAllContrats();
	public Contrat addOrUpdateContrat(Contrat contrat) ;
	public String getTypeContratById(int contratId);
	public void deleteContratById(int contratId);
	
	
	

	
}
