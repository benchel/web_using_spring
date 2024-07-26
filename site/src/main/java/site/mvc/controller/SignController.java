package site.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site/sign/*")
public class SignController {

	@GetMapping("/in")
	public String sign_in() throws Exception {
		return "site/sign/in";
	}
	
	@GetMapping("/up")
	public String sign_up() throws Exception {
		return "site/sign/in";
	}	
}
