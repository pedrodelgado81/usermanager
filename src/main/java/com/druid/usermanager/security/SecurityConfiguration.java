package com.druid.usermanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserAuthenticationService autenticacionUsuariosService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.autenticacionUsuariosService).passwordEncoder(bCryptPasswordEncoder);
	}

	// Autenticacion básica con usuario autogenerado
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/*.xhtml","/*.html","/error","/javax**/**", "/css/**", "/h2/**").permitAll() // Permito sin login las URL que cumplen
																				// el patron anterior
				.anyRequest().authenticated().and().formLogin().loginPage("/index.xhtml").defaultSuccessUrl("/private/home.xhtml").permitAll().and() // Para el resto de peticiones pido login
				.logout().logoutUrl("/logout.xhtml").logoutSuccessUrl("/"); // Añado soporte a logout en la url /logout y que
																		// redirija a /		
		//Deshabilitamos el por defecto de Spring porque JSF ya tiene su propio mecanismo de proteccion
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

}
