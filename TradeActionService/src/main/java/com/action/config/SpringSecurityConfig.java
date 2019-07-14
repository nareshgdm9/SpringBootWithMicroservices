package com.action.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private static Logger logger = LogManager.getLogger(SpringSecurityConfig.class);

	@Override
	protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
		logger.info("inside configure() method");
		managerBuilder.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("USER").and()
				.withUser("admin").password("{noop}1234").roles("ADMIN").and().withUser("review").password("{noop}1234")
				.roles("REVIEWER");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		logger.info("inside configure(HttpSecurity httpSecurity) method");
		httpSecurity.httpBasic().and().authorizeRequests()
				.antMatchers("/v1/action/approve/trades").hasRole("USER")
				.antMatchers("/v1/action/review/trades").hasRole("REVIEWER")
				.antMatchers("/v1/action/reject/trades").hasRole("ADMIN")
				.antMatchers("/v1/action/cancel/trades").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and().csrf().disable()
				.formLogin()
				.disable();
		//h2 database showing purpose
		httpSecurity.headers().frameOptions().disable();
	}

}
