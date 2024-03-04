package com.example.demo.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;


@Configuration
public class securityConfig {
	
	
	
	
	
	@Bean
	public ClientRegistrationRepository registrationRepository() {
		
		return new InMemoryClientRegistrationRepository(this.clientRegistration());
	}
	
	private ClientRegistration clientRegistration() {
		
		return ClientRegistration.withRegistrationId("github")
				.clientId("712c87694df47ffeae0a")
				.clientSecret("2ecc9e0b96e8a5c9353eac072b9b3fbdf690cdee")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.scope("read")
				.authorizationUri("https://github.com/login/oauth/authorize")
				.tokenUri("https://github.com/login/oauth/access_token")
				.userInfoUri("https://api.github.com/user")
				.userNameAttributeName("id")
				.clientName("CodeMaker")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
				.build();
	}
	
	
	
@Bean
public UserDetailsService useerDetailsService() {
	UserDetails user=User.withUsername("Danesh")
			.password("123")
			.authorities("read").build();
	
	return new InMemoryUserDetailsManager(user);
}



@Bean
public PasswordEncoder passwordEncoder() {
	
	return NoOpPasswordEncoder.getInstance();
}

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	  http.csrf(c->c.disable() ).authorizeHttpRequests( request -> request.requestMatchers("/css/**","/oauth2/**").permitAll().anyRequest().authenticated())
	  .formLogin(form ->form.loginPage("/login").permitAll().loginProcessingUrl("/login")
			  .defaultSuccessUrl("/")).oauth2Login(
					  form ->form.loginPage("/login")
					  .permitAll()
					  .defaultSuccessUrl("/")
					  
					  )
	      
	  
	  
	  .logout(
					  form ->form.invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					  .logoutSuccessUrl("/login?logout").permitAll()
					  );
	  return http.build();
  }

}
