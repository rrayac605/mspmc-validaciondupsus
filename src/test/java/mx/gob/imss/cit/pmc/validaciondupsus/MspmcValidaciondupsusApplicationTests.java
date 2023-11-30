package mx.gob.imss.cit.pmc.validaciondupsus;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mx.gob.imss.cit.pmc.validaciondupsus.service.PmcValidaSusceptibles;

@SpringBootTest
class MspmcValidaciondupsusApplicationTests {

	@Autowired
	PmcValidaSusceptibles susc;
	
	@Test
	void contextLoads() {
		assertNotNull(susc);
	}

}
