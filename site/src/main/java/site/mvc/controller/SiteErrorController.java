package site.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site/error/*")
public class SiteErrorController {

	@GetMapping("/un-authen")
	public String unAuthentication() {
		return "site/error/index";
	}
	
	@PostMapping("/un-authen")
	public ResponseEntity<?> unAuthentication_POST() {
		String responseBody = "인증된 사용자만이 접근 가능한 페이지입니다. 로그인하여 주십시오.";
		Map<String, Object> rs = new HashMap<>();
		rs.put("msg", responseBody);
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	
	@GetMapping("/deny")
	public String deny() {
		return "site/error/index";
	}
	
	@PostMapping("/deny")
	public ResponseEntity<?> deny_POST() {
		String responseBody = "지정된 권한을 가진 사용자만이 접근할 수 없는 페이지입니다.";
		Map<String, Object> rs = new HashMap<>();
		rs.put("msg", responseBody);
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
}
