package site.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site/*")
public class LoginController {

	@GetMapping("/login")
	public String login() throws Exception {
		return "site/sign/in";
	}
}
