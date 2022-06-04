package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
@Slf4j
@Service
public class TimesheetServiceImpl implements ITimesheetService {
	
	private static final Logger log = LogManager.getLogger(TimesheetServiceImpl.class);

	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository departementRepository;
	public int ajouterMission(Mission mission) {
		missionRepository.save(mission);
		return mission.getId();
	}
	
	@Override
	public Mission addMission(Mission mission) {
		return missionRepository.save(mission);
	}
    
	public void affecterMissionADepartement(int missionId, int depId) {
		
		missionRepository.findById(missionId).ifPresent(m -> {
			if (  deptRepoistory.findById(depId).isPresent() ) {
				m.setDepartement( deptRepoistory.findById(depId).get());
				missionRepository.save(m);
			}
		});
		
	}

	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); //par defaut non valide
		timesheetRepository.save(timesheet);
		
	}

	
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		
		//verifier s'il est un chef de departement (interet des enum)
		
		employeRepository.findById(validateurId).ifPresent(e -> {
			if(!e.getRole().equals(Role.CHEF_DEPARTEMENT)){
				log.info("l'employe doit etre chef de departement pour valider une feuille de temps !");
				return;
			}
			if ( missionRepository.findById(missionId).isPresent() ) {
				boolean chefDeLaMission = false;
				for(Departement dep : e.getDepartements()){
					if(dep.getId() == missionRepository.findById(missionId).get().getDepartement().getId()){
						chefDeLaMission = true;
						break;
					}
				}
				if(!chefDeLaMission){
					log.info("l'employe doit etre chef de departement de la mission en question");
					return;
				}
			}
		});
		

		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
		timesheet.setValide(true);
		
		//Comment Lire une date de la base de données
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		log.info("dateDebut : " + dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
		
	}

	
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		return timesheetRepository.findAllMissionByEmployeJPQL(employeId);
	}

	
	public List<Employe> getAllEmployeByMission(int missionId) {
		return timesheetRepository.getAllEmployeByMission(missionId);
	}
	@Override
	public List<Mission> getAllMission() {
	return  (List<Mission>) missionRepository.findAll() ;
	
	}
	@Override
	public void deleteVolume()
	{   timesheetRepository.deleteAll();
		missionRepository.deleteAll();
		departementRepository.deleteAll();
		timesheetRepository.deleteAll();
		employeRepository.deleteAll();
	
	 
       
	}

}
