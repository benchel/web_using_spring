package site.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import site.mvc.controller.service.NoticeService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice/*")
public class NoticeController {
	
	private final NoticeService noticeService;
	
	@GetMapping("/list")
	public String listHTML() throws Exception {
		return "site/notice/list";
	}
	
	@PostMapping("/list")
	public ResponseEntity<?> list() throws Exception {
		return new ResponseEntity<>(noticeService.list(), HttpStatus.OK);
	}
	
	@GetMapping("/view")
	public String viewHTML() throws Exception {
		return "site/notice/view";
	}
}
