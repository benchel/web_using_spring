package site.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.mvc.controller.service.AttachedFileService;
import site.mvc.controller.service.BoardService;
import site.mvc.dto.BoardDTO;

@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final AttachedFileService fileService;
	
	@GetMapping("/list")
	public String listHTML() throws Exception {
		return "site/board/list";
	}
	
	@PostMapping("/list")
	@ResponseBody
	public ResponseEntity<?> list(@RequestBody BoardDTO boardDTO) throws Exception {
		return new ResponseEntity<>(boardService.list(boardDTO), HttpStatus.OK);
	}

	@GetMapping("/view")
	public String viewHTML(ModelMap modelMap, BoardDTO boardDTO) throws Exception {
		modelMap.addAttribute("board", boardService.getBoard(boardDTO));
		modelMap.addAttribute("files", fileService.getList(boardDTO.getIdx(), "board"));
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
