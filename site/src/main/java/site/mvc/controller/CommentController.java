package site.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import site.mvc.dto.CommentDTO;
import site.mvc.service.CommentService;

@Controller
@RequestMapping("/comment/*")
@AllArgsConstructor
public class CommentController {

	private final CommentService commentService;
	
	@PostMapping("/reg")
	@ResponseBody
	public ResponseEntity<?> registry(@RequestBody CommentDTO commentDTO) throws Exception {
		return new ResponseEntity<>(commentService.registry(commentDTO), HttpStatus.OK);
	}
	
	@PostMapping("/list")
	@ResponseBody
	public ResponseEntity<?> list(@RequestBody CommentDTO commentDTO) throws Exception {
		return new ResponseEntity<>(commentService.list(commentDTO), HttpStatus.OK);
	}
	
}
