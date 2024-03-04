# SpringBoot-
Complete spring boot tutorials will be uploaded




#Spring Security Example

First Create the Controller Package and Configuration package then create classes respectively in it.


UserController.java

```package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserController {
	
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	

}
```


And add code in the donfig file 

securityConfig.java
```
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
```

Add login file and home file in the templets folder
login.html
```

 
 <!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sign In Form</title>
  <link rel="stylesheet" th:href="@{../css/login.css}">
  <style>
    body {
      font-family: 'Arial', sans-serif;
      margin: 0;
      padding: 0;
      background: url('https://wallpapers.com/images/featured/zoro-pictures-8nv0c0nb7rijf6z2.jpg') no-repeat center center fixed;
      background-size: cover;
      height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .container {
      background-color: rgba(255, 255, 255, 0.8);
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      padding: 20px;
      width: 300px;
      text-align: center;
    }

    h2 {
      color: #333;
    }

    form {
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    label {
      margin-bottom: 8px;
      color: #555;
    }

    input {
      width: 100%;
      padding: 10px;
      margin-bottom: 16px;
      box-sizing: border-box;
      border: 1px solid #ccc;
      border-radius: 4px;
    }

    button {
      background-color: #4CAF50;
      color: white;
      padding: 10px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 16px;
    }

    button:hover {
      background-color: #45a049;
    }

    .github-button {
      margin-top: 16px;
    }

    .github-button a {
      display: inline-block;
      background-color: #24292e;
      color: white;
      text-decoration: none;
      padding: 10px;
      border-radius: 4px;
    }

    .github-button a:hover {
      background-color: #1c2023;
    }

    .message {
      margin: 10px 0;
      padding: 10px;
      background-color: #ff6961;
      color: #fff;
      border-radius: 4px;
    }
  </style>
</head>
<body>

  <div class="container">
    <div class="message" th:if="${param.error}">Incorrect Email or password</div>
    <div class="message" th:if="${param.logout}">Logout Successfully</div>
    <h2>Sign In</h2>
    <form th:action="@{/login}" method="post">
      
      <input type="text" id="username" name="username" placeholder="UserName" required>

      
      <input type="password" id="password" name="password" placeholder="Password" required>

      <button type="submit">Sign In</button>
    </form>

    <div class="github-button">
      <p>or sign in with GitHub:</p>
      <a href="http://localhost:8080/oauth2/authorization/github">Sign in with GitHub</a>
    </div>
  </div>
</body>
</html>

 ```

home.html
```
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
  <style>
    body {
      font-family: 'Arial', sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f4f4f4;
    }

    .dashboard {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-around;
      padding: 20px;
    }

    .card {
      background-color: #fff;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      margin: 15px;
      padding: 20px;
      width: 300px;
      text-align: center;
    }

    .card i {
      font-size: 48px;
      color: #3498db;
    }

    .card h2 {
      color: #333;
      margin-top: 10px;
    }

    .card p {
      color: #777;
      margin-top: 10px;
    }

    @media (max-width: 600px) {
      .card {
        width: 100%;
      }
    }
  </style>
</head>
<body>

  <div class="dashboard">
    <div class="card">
      <i class="fas fa-chart-bar"></i>
      <h2>Analytics</h2>
      <p>Track your website performance</p>
    </div>

    <div class="card">
      <i class="fas fa-users"></i>
      <h2>Users</h2>
      <p>Manage user accounts</p>
    </div>

    <div class="card">
      <i class="fas fa-cog"></i>
      <h2>Settings</h2>
      <p>Configure your application</p>
    </div>

    <!-- Add more cards as needed -->

  </div>

</body>
</html>
```
This is the folder strcture.
![image](https://github.com/danu20002/SpringBoot-/assets/99582894/f34be6ce-84f8-413f-b819-57e13e8c5117)

This is Login Page
![image](https://github.com/danu20002/SpringBoot-/assets/99582894/b6d93c02-bc14-4481-a918-d4bd0ee80710)

This is the Home Page
![image](https://github.com/danu20002/SpringBoot-/assets/99582894/48b4c442-6a37-4d4d-901f-b3a1b6ec39dc)

