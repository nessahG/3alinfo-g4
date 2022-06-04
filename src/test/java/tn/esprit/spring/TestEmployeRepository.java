package tn.esprit.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.services.IEmployeService;

@SpringBootTest
@TestMethodOrder(value = OrderAnnotation.class)
@DisplayName("Test Employee repository class")
public class TestEmployeRepository {
	private static final Logger log = LogManager.getLogger(TestEmployeRepository.class);

	private static Employe employe;

	private static Contrat contrat;

	@Autowired
	private IEmployeService employeService;

	@BeforeAll
	public static void init() {
		employe = new Employe();
		contrat = new Contrat();
	}

	@Test
	@DisplayName("Test insert methode")
	@Order(1)
	public void testInsert() {
		employe = employeService.addOrUpdateEmploye(employe);

		log.info("Test add");
		assertTrue(employeService.getAllEmployes().stream().filter(e -> e.getId() == employe.getId()).findFirst()
				.isPresent());
	}

	@Test
	@DisplayName("test select")
	@Order(2)
	public void testSelect() {
		log.info("test select method");
		assertTrue(employeService.getAllEmployes().size() > 0);
	}

	@Test
	@DisplayName("Test update methode")
	@Order(3)
	public void testUpdate() {
		log.info("Test update");
		employe.setPrenom("yassine");
		employeService.addOrUpdateEmploye(employe);
		log.info("update employee prenom");
		assertEquals("yassine", employeService.getEmployePrenomById(employe.getId()));
	}

	@Test
	@DisplayName("test affecterContratAEmploye")
	@Order(4)
	public void testAffecterContratAEmploye() {

		log.info("Start Method affecterContratAEmploye");

		employe = employeService.addOrUpdateEmploye(employe);
		contrat = employeService.addContrat(contrat);
		log.info(this.contrat);
		log.info(this.employe);
		employeService.affecterContratAEmploye(this.contrat.getReference(), this.employe.getId());
		this.employe = employeService.getAllEmployes().stream().filter(x -> x.getId() == this.employe.getId())
				.findFirst().get();
		assertEquals(this.employe.getContrat().getReference(), this.contrat.getReference());

		log.info("End Method affecterContratAEmploye");
	}

	@Test
	@DisplayName("test remove")
	@Order(5)
	public void testRemove() {
		log.info("test remove");
		employeService.deleteContratById(employe.getContrat().getReference());
		employeService.deleteEmployeById(employe.getId());
		assertFalse(employeService.getAllEmployes().contains(employe));

	}

}
