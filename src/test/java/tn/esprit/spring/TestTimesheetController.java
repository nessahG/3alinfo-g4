package tn.esprit.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.IDepartementService;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@SpringBootTest
@Slf4j
@TestMethodOrder(OrderAnnotation.class)
public class TestTimesheetController {
	private static final Logger LOG = LogManager.getLogger(TestTimesheetController.class);

	@Autowired
	private ITimesheetService itimesheetservice;
	@Autowired
	private IEntrepriseService iEntrepriseService;
	@Autowired
	private IEmployeService iEmployeService;
	@Autowired
	private IDepartementService iDepartementService;
	@Autowired
	private MissionRepository missionRepository;

	@Autowired
	TimesheetRepository timesheetRepository;
	private static Mission mission;
	private static Departement departement;
	private static Employe employe;
	private static Employe chefDepartement;

	@BeforeAll
	public static void init() {
		mission = new Mission();
		mission.setName("missionTest");
		mission.setDescription("missionDescription");
		departement = new Departement();
		departement.setName("departmentTest");

		employe = new Employe();
		employe.setNom("employeTest");
		employe.setPrenom("employePrenom");
		employe.setEmail("employee@gmail.com");
		employe.setPassword("passwordEM");
		employe.setActif(true);
		employe.setRole(Role.INGENIEUR);

		chefDepartement = new Employe();
		chefDepartement.setNom("chefEmployee");
		chefDepartement.setPrenom("chefPrenomEmployee");
		chefDepartement.setEmail("chef@gmail.com");
		chefDepartement.setPassword("chefpassword");
		chefDepartement.setActif(true);
		chefDepartement.setRole(Role.CHEF_DEPARTEMENT);

	}

	@Test
	@Order(1)
	public void addMissionTest() {
		LOG.info("Method addMissionTest ");
		LOG.info(mission);
//		itimesheetservice.addMission(mission);
		assertTrue(itimesheetservice.addMission(mission) != null);
		LOG.info("mission created sucessfully ");
	}

	@Test
	@Order(2)
	public void addMissionToDepartementTest() {
		LOG.info("Method addMissionToDepartementTest ");
		iDepartementService.addOrUpdateDepartement(departement);
		this.departement.setId(iEntrepriseService.ajouterDepartement(this.departement));
		assertTrue(this.departement.getId() > 0);
		int idMiss = itimesheetservice.getAllMission().get(0).getId();
		itimesheetservice.affecterMissionADepartement(idMiss, this.departement.getId());
		Optional<Mission> missionR = missionRepository.findById(idMiss);
		if (missionR.isPresent()) {
			this.mission = missionR.get();

		}
		this.mission.getDepartement().getId();
		assertEquals(this.mission.getDepartement().getId(), this.departement.getId());

	}

	@Test
	@Order(5)
	public void addTimesheetTest() throws ParseException {
		LOG.info("Method ajouterTimesheetTest");
		assertTrue(iEmployeService.addOrUpdateEmploye(this.employe) != null );
		assertTrue(iEmployeService.addOrUpdateEmploye(this.chefDepartement) != null);
		iEmployeService.affecterEmployeADepartement(this.chefDepartement.getId(), this.departement.getId());
		LOG.info("Test Affect Employee to Departement" + this.mission);
		LOG.info(this.mission);
		Date dateDebut = new Date();
		Date dateFin = new Date();
		iEmployeService.addOrUpdateEmploye(employe);
		int idEMp = iEmployeService.getAllEmployes().get(0).getId();
		employe.setId(idEMp);
		int idMis = itimesheetservice.ajouterMission(mission);
		mission.setId(idMis);
		itimesheetservice.ajouterTimesheet(this.mission.getId(), idEMp, dateDebut, dateFin);
		LOG.info("Add TimeSheet Sucessfuly");
		Iterable<Timesheet> timesheets = timesheetRepository.findAll();
		for (Timesheet ts : timesheets) {
			if ((ts.getMission().getId() == this.mission.getId())
					&& (ts.getEmploye().getId() == this.employe.getId())) {
				List<Timesheet> timesheet = new ArrayList();
				timesheet.add(ts);
				this.mission.setTimesheets(timesheet);
			}
		}
		assertNotNull(this.mission.getTimesheets().get(0).getTimesheetPK());
		LOG.info(this.mission.getTimesheets().get(0));
	}

	@Test
	@Order(6)
	public void validerTimesheetTest() throws ParseException {
		LOG.info("Method validerTimesheetTest ");
		assertTrue(iEmployeService.addOrUpdateEmploye(this.chefDepartement) != null);
		Date dateDebut = new Date();
		Date dateFin = new Date();
		itimesheetservice.validerTimesheet(this.mission.getId(), this.employe.getId(), dateDebut, dateFin,
				this.chefDepartement.getId());
		Iterable<Timesheet> timesheets = timesheetRepository.findAll();
		for (Timesheet ts : timesheets) {
			if ((ts.getMission().getId() == this.mission.getId())
					&& (ts.getEmploye().getId() == this.employe.getId())) {
				List<Timesheet> timesheet = new ArrayList();
				ts.isValide();
				timesheet.add(ts);
				this.mission.setTimesheets(timesheet);
			}
		}
		this.mission.getTimesheets().get(0).isValide();
		assertFalse(this.mission.getTimesheets().get(0).isValide());
		LOG.info(this.mission.getTimesheets().get(0));
		itimesheetservice.deleteVolume();
	}

	@Test
	@Order(2)
	public void findAllMissionByEmployeJPQLTest() {
		LOG.info("Method findAllMissionByEmployeJPQLTest ");
		iEmployeService.addOrUpdateEmploye(employe);
		
		int idEMp = iEmployeService.getAllEmployes().get(0).getId();
		List<Mission> missions = itimesheetservice.findAllMissionByEmployeJPQL(idEMp);
		int missionZie = missions.size();
		assertFalse(missionZie > 0);

	}

	@Test
	@Order(4)
	public void getAllEmployeByMissionTest() {
		LOG.info("Method getAllEmployeByMissionTest ");
		itimesheetservice.affecterMissionADepartement(mission.getId(), departement.getId());

		List<Employe> employes = itimesheetservice.getAllEmployeByMission(this.mission.getId());
		LOG.info(employes);
		int employesSize = employes.size();
		assertFalse(employesSize > 0);
	}
}
