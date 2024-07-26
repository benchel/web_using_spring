package site.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mngr/notice/*")
public class MngrNoticeController {

	@GetMapping("/list")
	public String listHTML() throws Exception {
		return "mngr/notice/list";
	}
}
