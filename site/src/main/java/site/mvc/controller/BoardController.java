package site.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.mvc.controller.service.BoardService;
import site.mvc.dto.BoardDTO;

@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("/list")
	public String listHTML() throws Exception {
		return "site/board/list";
	}
	
	@GetMapping("/view")
	public String viewHTML() throws Exception {
		return "site/board/view";
	}
	
	@GetMapping("/reg")
	public String regHTML() throws Exception {
		return "site/board/reg";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<?> add(@RequestBody BoardDTO board) throws Exception {		
		return new ResponseEntity<>(boardService.registry(board), HttpStatus.OK);
	}
}
