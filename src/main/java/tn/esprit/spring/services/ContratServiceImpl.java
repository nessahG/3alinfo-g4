package tn.esprit.spring.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.repository.ContratRepository;

@Service
public class ContratServiceImpl implements IContratService {


	@Autowired
	ContratRepository contratRepository;


	public List<Contrat> getAllContrats() {
		return (List<Contrat>) contratRepository.findAll();
	}
	
	
	
	@Override
	public Contrat addOrUpdateContrat(Contrat contrat) {
		return contratRepository.save(contrat);
		
	}
	
	



	@Override
	public String getTypeContratById(int contratId) {
		Optional<Contrat> optionalContrat = contratRepository.findById(contratId);
		return optionalContrat.isPresent() ? optionalContrat.get().getTypeContrat()
				: new NoSuchElementException("contrat with the id " + contratId + " doesent exist in the db")
				.getMessage();
	}
	@Override
	public void deleteContratById(int contratId) {

		contratRepository.findById(contratId).ifPresent(c -> contratRepository.delete(c));

	}

}
