package mx.gob.imss.cit.pmc.validaciondupsus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import mx.gob.imss.cit.pmc.validaciondupsus.security.JWTAuthorizationFilter;
import mx.gob.imss.cit.pmc.validaciondupsus.security.service.TokenValidateService;

@SpringBootApplication
public class MspmcValidaciondupsusApplication {

	public static void main(String[] args) {
		SpringApplication.run(MspmcValidaciondupsusApplication.class, args);
	}
	
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Bean
		public TokenValidateService tokenPmcValidateService() {
			return new TokenValidateService();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(tokenPmcValidateService()), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/msvalidaciondupsus/v1/validar**").permitAll()
				.antMatchers(HttpMethod.POST, "/msvalidaciondupsus/v1/validarSusceptibles**").permitAll()
				.anyRequest().authenticated();
		}
		
		@Override
		public void configure(WebSecurity webSecurity) {
			webSecurity.ignoring().antMatchers(
					"/swagger-resources/**",
					"/swagger-ui.html",
					"/v2/api-docs",
					"/webjars/**"
			);
		}
	}
}
