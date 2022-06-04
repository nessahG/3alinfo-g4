/**
 * 
 */
package tn.esprit.spring;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.services.IContratService;
import tn.esprit.spring.services.IEmployeService;


@SpringBootTest
@Slf4j
@TestMethodOrder(value = OrderAnnotation.class)
@DisplayName("Test contract repository class")
class TestContractRepository {
	private static final Logger log = LogManager.getLogger(TestContractRepository.class);
	private static Contrat contrat;
	@Autowired
	private IContratService contratService ;

	@BeforeAll
	public static void init() {
		contrat = new Contrat();
	}

	@Test
	@DisplayName("Test insert methode")
	@Order(1)
	public void testInsert() {
		
	contrat=contratService.addOrUpdateContrat(contrat);
		
	
		log.info("Test add");
	
		assertTrue(contratService.getAllContrats().stream().anyMatch(e -> e.getReference() == contrat.getReference()));
		

		assertTrue(contratService.getAllContrats().contains(contrat));
	}

	
	@Test
	@DisplayName("test select")
	@Order(2)
	public void testSelect() {
		//log.info("test select method");
		assertTrue(contratService.getAllContrats().size() > 0);
	}
	
	@Test
	@DisplayName("Test update methode")
	@Order(3)
	public void testUpdate() {
	log.info("Test update");
		contrat.setTypeContrat("CDI");
		contratService.addOrUpdateContrat(contrat);
	log.info("update contrat typeContrat");
		assertEquals("CDI", contratService.getTypeContratById(contrat.getReference()));
	}
	
	
	@Test
	@DisplayName("test remove")
	@Order(4)
	public void testRemove() {
	log.info("test remove");
		contratService.deleteContratById(contrat.getReference());
		assertFalse(contratService.getAllContrats().contains(contrat));
	}

}
