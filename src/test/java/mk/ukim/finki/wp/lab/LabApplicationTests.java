package mk.ukim.finki.wp.lab;

import mk.ukim.finki.wp.lab.model.Manufacturer;
import mk.ukim.finki.wp.lab.model.enumerations.Role;
import mk.ukim.finki.wp.lab.service.BalloonService;
import mk.ukim.finki.wp.lab.service.ManufacturerService;
import mk.ukim.finki.wp.lab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LabApplicationTests {

	MockMvc mockMvc;
	@Autowired
	UserService userService;
	@Autowired
	ManufacturerService manufacturerService;
	@Autowired
	BalloonService balloonService;
	private static Manufacturer manufacturer;
	private static boolean dataInitialized = false;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext) {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		initData();
	}

	private void initData() {
		if (!dataInitialized) {
			manufacturer = manufacturerService.save("Manufacturer 1", "Address 1", "Country 1");
			manufacturerService.save("Manufacturer 2", "Address 2", "Country 2");

			String user = "user";
			String admin = "admin";

			userService.register(user, user, user, user, user, LocalDate.now(), Role.ROLE_USER);
			userService.register(admin, admin, admin, admin, admin, LocalDate.now(), Role.ROLE_ADMIN);

			dataInitialized = true;
		}
	}

	@Test
	public void testGetBalloons() throws Exception {
		MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/balloons");
		mockMvc.perform(productRequest)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("balloons"))
				.andExpect(MockMvcResultMatchers.view().name("listBalloons"));
	}

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
	public void testGetAddBalloonPage() throws Exception {
		MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/balloons/add-form");
		mockMvc.perform(productRequest)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("manufacturers"))
				.andExpect(MockMvcResultMatchers.view().name("add-balloon"));
	}

}
