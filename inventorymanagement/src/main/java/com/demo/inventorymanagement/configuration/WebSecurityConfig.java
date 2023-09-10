package com.demo.inventorymanagement.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Value("${spring.h2.console.enabled}")
    private boolean h2ConsoleEnabled;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
	{
	    http
	    .cors()
	    .and()
	    .authorizeHttpRequests(authorizeRequests ->
        		authorizeRequests
        			.antMatchers("/h2-console/**").permitAll()
        			.anyRequest().authenticated())
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	    
	    if (h2ConsoleEnabled) {
            http
                .csrf().disable()
                .headers().frameOptions().disable(); // Disable CSRF and frameOptions for H2 Console
        }
	    
		return http.build();
	}

	@Bean
	public WebMvcConfigurer corsMappingConfigurer() 
	{
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") //
				.allowedOrigins("http://localhost:3000") //
				.allowedMethods("GET", "POST", "PUT", "DELETE") //
				.allowedHeaders("*");
			}
		};
	}
}
