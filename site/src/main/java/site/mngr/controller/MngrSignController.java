package site.mngr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mngr/sign/*")
public class MngrSignController {

	@GetMapping("/in")
	public String sign_in() throws Exception {
		return "mngr/sign/in";
	}
}
