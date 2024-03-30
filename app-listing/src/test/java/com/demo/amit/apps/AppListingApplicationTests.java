package com.demo.amit.apps;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.amit.apps.dto.Tool;
import com.demo.amit.apps.exception.DuplicateToolException;
import com.demo.amit.apps.model.Tools;
import com.demo.amit.apps.service.AppListingService;

@RunWith(SpringRunner.class)
@SpringBootTest
class AppListingApplicationTests {

	
	
	@Autowired
    private AppListingService appListingService;

	
	@Test
	void deleteAll() {
		appListingService.removeAll();
	}
	
	@Test
	void addAppCodeJAKR() throws DuplicateToolException {
		
		Tool tool = Tool.builder()
				.brand("RidGid")
				.toolCode("JAKR")
				.toolName("JAKR")
				.toolType("Jackhammer")
				.freeOnWeekendsOrHolidays(true)
				.rentperDay(new BigDecimal("10"))
		.build();
		
		Tools tools = appListingService.createToolEntry(tool);  
        assertNotNull(tools);
        assertNotNull(tools.getToolId());
	}

	@Test
	void addAppCodeLADW() throws DuplicateToolException {
		
		Tool tool = Tool.builder()
				.brand("Wener")
				.toolCode("LADW")
				.toolName("LADW")
				.toolType("Ladder")
				.freeOnWeekendsOrHolidays(false)
				.rentperDay(new BigDecimal("20"))
		.build();
		
		Tools tools = appListingService.createToolEntry(tool);  
        assertNotNull(tools);
        assertNotNull(tools.getToolId());
	}

	@Test
	void addAppCodeJAKD() throws DuplicateToolException {
		
		Tool tool = Tool.builder()
				.brand("DeWalt")
				.toolCode("JAKD")
				.toolName("JAKD")
				.toolType("Jackhammer")
				.freeOnWeekendsOrHolidays(true)
				.rentperDay(new BigDecimal("15"))
		.build();
		
		Tools tools = appListingService.createToolEntry(tool);  
        assertNotNull(tools);
        assertNotNull(tools.getToolId());
	}

	@Test
	void addAppCodeCHNS() throws DuplicateToolException {
		
		Tool tool = Tool.builder()
				.brand("Sithl")
				.toolCode("CHNS")
				.toolName("CHNS")
				.toolType("Chainsaw")
				.freeOnWeekendsOrHolidays(true)
				.rentperDay(new BigDecimal("25"))
		.build();
		
		Tools tools = appListingService.createToolEntry(tool);  
        assertNotNull(tools);
        assertNotNull(tools.getToolId());
	}

	
	@Test
	void testDuplicateToolExceptionnIsThrown() {
	    assertThrows(DuplicateToolException.class, () -> {
	    	Tool tool = Tool.builder()
					.brand("RidGid")
					.toolCode("JAKR")
					.toolName("JAKR")
					.toolType("Jackhammer")
					.freeOnWeekendsOrHolidays(true)
					.rentperDay(new BigDecimal("10"))
			.build();
			
			appListingService.createToolEntry(tool);  
	       
	    });
	}
	
}
