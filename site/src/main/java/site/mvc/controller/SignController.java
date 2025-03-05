package site.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import site.mvc.controller.service.UserService;
import site.mvc.dto.UserDTO;

@Controller
@RequestMapping("/site/sign/*")
@RequiredArgsConstructor
public class SignController {
	
	private final UserService userService;

	@GetMapping("/in")
	public String sign_in() throws Exception {
		return "site/sign/in";
	}
	
	@GetMapping("/up")
	public String sign_up() throws Exception {
		return "site/sign/up";
	}
	
	/**
	 * 아이디 중복 확인
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/check/overlap")
	public ResponseEntity<?> check_overlap(@RequestBody UserDTO userDTO) throws Exception {
		return new ResponseEntity<>(userService.checkOverlap(userDTO), HttpStatus.OK);
	}
	
	/**
	 * 회원 생성
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/create/user")
	public ResponseEntity<?> create_user(@RequestBody UserDTO userDTO) throws Exception {
		return new ResponseEntity<>(userService.create_user(userDTO), HttpStatus.OK);
	}
	
	/**
	 * 인증번호 발송(with 이메일)
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/send/mail")
	public ResponseEntity<?> send_mail(@RequestBody UserDTO userDTO) throws Exception {
		return new ResponseEntity<>(userService.send_auth_num(userDTO), HttpStatus.OK);
	}
	
}
