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
