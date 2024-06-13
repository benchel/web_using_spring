package site.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice/*")
public class NoticeController {

	@GetMapping("/list")
	public String listHTML() throws Exception {
		return "site/notice/list";
	}
	
	@GetMapping("/view")
	public String viewHTML() throws Exception {
		return "site/notice/view";
	}
}
