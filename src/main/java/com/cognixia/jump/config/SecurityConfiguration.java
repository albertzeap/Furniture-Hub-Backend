package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filter.JwtRequestFilter;

@Configuration
public class SecurityConfiguration {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Bean
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		http.cors().and().csrf().disable()
		.authorizeRequests()
		
		// Authentication and User controller 
		.antMatchers("/authenticate").permitAll()
		.antMatchers(HttpMethod.POST, "/api/user").permitAll()
		.antMatchers(HttpMethod.GET, "/api/user/*").hasAnyRole("ADMIN","USER")
		.antMatchers(HttpMethod.DELETE, "/api/user/*").hasRole("ADMIN")
		
		// Order controller config
		.antMatchers(HttpMethod.GET, "/api/order").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/api/order/*").hasAnyRole("ADMIN", "USER")
		.antMatchers(HttpMethod.GET, "/api/order/user/*").hasAnyRole("ADMIN", "USER")
		.antMatchers(HttpMethod.POST, "/api/order").hasAnyRole("ADMIN", "USER")
		.antMatchers(HttpMethod.DELETE, "/api/order/*").hasRole("ADMIN")
		
		// Product controller config
		.antMatchers(HttpMethod.GET, "/api/product").permitAll()
		.antMatchers(HttpMethod.GET, "/api/product/*").permitAll()
		.antMatchers(HttpMethod.POST, "/api/product").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "/api/product").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/api/product/*").hasRole("ADMIN")
		
		.anyRequest().authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
    protected PasswordEncoder encoder() {

        // plain text coder -> won't do any encoding
        return NoOpPasswordEncoder.getInstance();

        // bcrypt encoder -> do actual encoding, popular algorithm but there are other encoders you can use
        // return new BCryptPasswordEncoder();
    }
	
	@Bean
    protected DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder( encoder() );
		
		return authProvider;
	}

    @Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
}
