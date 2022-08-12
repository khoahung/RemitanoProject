package com.khoahung.controller;

package com.khoahung.test.controller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.khoahung.controller.LoginController;
import com.khoahung.test.config.TestBeanConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
public class UserControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private LoginController userController;

	@Test
	public void validateUser_Test_Positive() {
		Map params = new HashMap();
		params.put("email", "admin@admin.com");
		params.put("password","admin");
	}
	@Test
    public void controllerTest() throws Exception {
	}
}