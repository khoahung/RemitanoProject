package com.khoahung.test.controller;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.khoahung.controller.LoginController;
import com.khoahung.model.Account;
import com.khoahung.model.Movies;
import com.khoahung.test.config.TestBeanConfig;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
public class UserControllerTest {
	
	@Autowired
    private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Autowired
	private LoginController loginController;

	@Before
    public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
 
    }
	
	@Test
	public void test_loginPage() throws Exception{
		Account a = new Account();
		a.setUsername("khoahung");
		a.setPassword("khoahung");
		ResultMatcher ok = MockMvcResultMatchers.status()
                .isOk();
		ResultMatcher doneView = MockMvcResultMatchers.view()
                .name("welcomePage");
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/register")
				.param("username", "khoahung")
				.param("password", "khoahung");
		this.mockMvc.perform(builder).andExpect(ok).andExpect(doneView);
	}
	
	@Test
	public void test_getMovies() {
		List<Movies> result =  loginController.movieRender();
		assertNotNull(result);
		assertTrue(result.size()>0);
	}
	@Test
	public void test_WelcomePage() throws Exception{
		ResultMatcher ok = MockMvcResultMatchers.status()
                .isOk();
		ResultMatcher registerView = MockMvcResultMatchers.view()
                .name("welcomePage");
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
		this.mockMvc.perform(builder).andExpect(ok).andExpect(registerView);
	}
	
	@Test
	public void test_HomePage() {
		ModelAndView result =  loginController.homePage();
		assertNotNull(result);
		assertTrue(result==null);
	}
}