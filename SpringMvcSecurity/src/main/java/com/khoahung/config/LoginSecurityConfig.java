package com.khoahung.config;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.userDetailsService(username -> {
            try {
                String pathToFile = "c:/data/account.txt";
                List<String> users = Files.readAllLines(Paths.get(pathToFile));
                for (String user : users) {
                    String[] parts = user.split(";", 2);
                    String theUsername = parts[0];
                    String password = parts[1];

                    if (username.equals(theUsername))
                        return new User(theUsername, password, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
                }
                throw new UsernameNotFoundException("Invalid username");
            } catch (Exception e) {
                throw new UsernameNotFoundException("Invalid username");
            }
    });
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/homePage").access("hasRole('ROLE_USER')")
			.and().csrf().csrfTokenRepository(csrfTokenRepository())
			
			.and()
				.formLogin().loginPage("/loginPage")
				.defaultSuccessUrl("/homePage")
				.failureUrl("/loginPage?error")
				.usernameParameter("username").passwordParameter("password")				
			.and()
				.logout().logoutSuccessUrl("/loginPage?logout")
			.and().addFilterAfter(csrfHeaderFilter(), SessionManagementFilter.class); 
		
	}
	
	private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {

                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null || token != null
                            && !token.equals(cookie.getValue())) {

                        // Token is being added to the XSRF-TOKEN cookie.
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        //repository.setSessionAttributeName(("X-XSRF-TOKEN"));
        return repository;
    }
}