package site.mngr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.mvc.dto.NoticeDTO;
import site.mvc.service.NoticeService;

@Controller
@RequestMapping("/mngr/notice/*")
@RequiredArgsConstructor
public class MngrNoticeController {

	private final NoticeService noticeService;
	
	@GetMapping("/list")
	public String listHTML() throws Exception {
		return "mngr/notice/list";
	}
	
	@PostMapping("/list")
	@ResponseBody
	public ResponseEntity<?> list(@RequestBody NoticeDTO noticeDTO) throws Exception {
		return new ResponseEntity<>(noticeService.list(noticeDTO), HttpStatus.OK);
	}
	
	@GetMapping("/reg")
	public String regHTML() throws Exception {
		return "mngr/notice/reg";
	}
	
	@PostMapping("/reg")
	@ResponseBody
	public ResponseEntity<?> reg(@RequestBody NoticeDTO noticeDTO) throws Exception {
		return new ResponseEntity<>(noticeService.registry(noticeDTO), HttpStatus.OK);
	}	
}
