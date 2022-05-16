package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.security.services.CustomUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Lazy
	@Autowired
	public CustomUserService customUserDetailService;

	// invalidateHttpSession allows the session to be set up so that it's not
	// invalidated when logout occurs (it's true by default).

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/registration**", "/js/**", "/css/**").permitAll()
			.antMatchers("/resources/**").permitAll().anyRequest()
			.authenticated()
			.and()
			.formLogin().loginPage("/login").defaultSuccessUrl("/", true).permitAll()
			.and()
			.logout().invalidateHttpSession(true).clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").permitAll();
	}

	/*
	 * SecurityBuilder used to create an AuthenticationManager. Allows for easily
	 * building in memory authentication, LDAP authentication, JDBC based
	 * authentication, adding UserDetailsService, and adding
	 * AuthenticationProvider's.
	 */
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	// Used for Encoding Password
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * public class DaoAuthenticationProvider extends
	 * AbstractUserDetailsAuthenticationProvider An AuthenticationProvider
	 * implementation that retrieves user details from a UserDetailsService.
	 */
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(customUserDetailService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
}

/*
 * loginPage() – the custom login page loginProcessingUrl() – the URL to submit
 * the username and password to defaultSuccessUrl() – the landing page after a
 * successful login failureUrl() – the landing page after an unsuccessful login
 * logoutUrl() – the custom logout
 */