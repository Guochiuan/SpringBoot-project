package edu.gatech.cs6310.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// reference to  security data source
	@Autowired
	@Qualifier("securityDataSource")
	private DataSource securityDataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		//jdbc authentication
		auth.jdbcAuthentication().dataSource(securityDataSource);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()

				.antMatchers("/drones/*").hasAnyRole("ADMIN", "MANAGER")
				.antMatchers("/customers/*").hasAnyRole("ADMIN")
				.antMatchers("/items/*").hasAnyRole("ADMIN", "MANAGER")
				.antMatchers("/lines/*").hasAnyRole("ADMIN")
				.antMatchers("/managers/*").hasAnyRole("ADMIN")
				.antMatchers("/stores/*").hasAnyRole("ADMIN", "MANAGER")
				.antMatchers("/pilots/*").hasAnyRole("ADMIN", "PILOT")
				.antMatchers("/orders/**").hasAnyRole("ADMIN", "CUSTOMER")
				.antMatchers("/stores/store_list").hasAnyRole("ADMIN", "MANAGER")
				.antMatchers("/stores/*").hasAnyRole("ADMIN", "MANAGER")
				.antMatchers("/stores/**").hasAnyRole("ADMIN", "MANAGER")
				.antMatchers("/stores").hasAnyRole("ADMIN", "MANAGER")
				.antMatchers("/stores/list").hasAnyRole("ADMIN", "MANAGER")
				.antMatchers("/test").permitAll()
				.antMatchers("/customers/**").hasAnyRole("CUSTOMER", "ADMIN")
				.antMatchers("/admin/**").hasAnyRole("ADMIN")
				.antMatchers("/pilots/**").hasAnyRole("PILOT")
				.antMatchers("/managers/**").hasAnyRole("ADMIN")
				.antMatchers("/admin/**").hasAnyRole("ADMIN")
				.antMatchers("/store_manager/**").hasAnyRole("MANAGER", "ADMIN")
//				.antMatchers("/homepage").hasAnyRole("MANAGER", "ADMIN", "PILOT", "CUSTOMER")
				.and()
				.formLogin()
				.loginPage("/showMyLoginPage")
				.loginProcessingUrl("/authenticateTheUser")
				.permitAll()
				.and()
				.logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/access-denied");

	}

}






