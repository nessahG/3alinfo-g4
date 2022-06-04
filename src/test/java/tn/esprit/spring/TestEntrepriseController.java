package tn.esprit.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

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

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEntrepriseService;

@SpringBootTest
@Slf4j
@TestMethodOrder(value = OrderAnnotation.class)
@DisplayName("Test Entreprise repository class")
public class TestEntrepriseController {
	private static final Logger log = LogManager.getLogger(TestEmployeRepository.class);

	private static Entreprise entreprise;
	@Autowired
	private IEntrepriseService entrepriseService;

	@BeforeAll
	public static void init() {
		
		entreprise = new Entreprise();
	}

	@Test
	@DisplayName("Test insert methode")
	@Order(1)
	public void testInsert() {
		entrepriseService.ajouterEntreprise(entreprise);
		log.info("Test add entreprisee");
		assertTrue( entrepriseService.getEntrepriseById(entreprise.getId()) != null );
	}

	@Test 
	@DisplayName("Test update entreprisee")
	@Order(2)
	public void testUpdate() {
		log.info("Test update");
		entreprise.setName("vermeg");
		entrepriseService.ajouterEntreprise(entreprise);
		log.info("update entreprisee name");
		assertEquals("vermeg", entrepriseService.getEntrepriseById(entreprise.getId()).getName());
	}

	@Test
	@DisplayName("test remove")
	@Order(3)
	public void testRemove() {
		log.info("test remove entreprisee");
		entrepriseService.deleteEntrepriseById(entreprise.getId());
		assertThrows(NoSuchElementException.class , () -> entrepriseService.getEntrepriseById(entreprise.getId()));
	}

}
