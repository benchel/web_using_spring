package site.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site/mypage")
public class MyPageController {

	@GetMapping("/info")
	public String Information() {
		return "site/mypage/info";
	}
}
