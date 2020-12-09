package com.springboot.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;




@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 //Create User

	//Create Login form
	@Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
            throws Exception {
		auth.inMemoryAuthentication()
    		.withUser("admin")
    		.password("{noop}password")
            .roles("USER", "ADMIN");
    }
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests().antMatchers("/login", "/h2-console/**")
	    		.permitAll()
	            .antMatchers("/", "/*todo*/**")
	            .hasRole("USER")
	            .and()
	            .formLogin();
	    
	    http.csrf().disable();
	    http.headers().frameOptions().disable();
	}
	
}
