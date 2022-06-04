package tn.esprit.spring.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;


@Service
public class DepartementServiceImpl implements IDepartementService {


	@Autowired
	DepartementRepository deptRepoistory;
	
	@Autowired
	EmployeRepository employeRepository;

	public List<Departement> getAllDepartements() {
		return (List<Departement>) deptRepoistory.findAll();
	}
	@Override
	public Departement addOrUpdateDepartement(Departement departement) {
		
		return deptRepoistory.save(departement);
	}
	@Override
	public String getDepartementName(int departementId) {
		Optional<Departement> optionaldepratement = deptRepoistory.findById(departementId);
		return optionaldepratement.isPresent() ? optionaldepratement.get().getName()
				: new NoSuchElementException("departement with the id " + departementId + " doesent exist in the db")
						.getMessage();
	}
	@Override
	public void deletedepartementById(int departementId)
	{
		Optional<Departement> optionaldepratement = deptRepoistory.findById(departementId);
		optionaldepratement.ifPresent(o -> deptRepoistory.delete(o));

	}

}
