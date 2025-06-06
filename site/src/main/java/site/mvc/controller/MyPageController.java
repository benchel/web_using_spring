package site.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import site.mvc.controller.service.UserDetailService;
import site.mvc.controller.service.UserService;
import site.mvc.dto.UserDTO;
import site.mvc.vo.UserVO;

@Controller
@RequestMapping("/site/mypage/*")
@RequiredArgsConstructor
public class MyPageController {

	private final UserService userService;
	private final UserDetailService userDetailsService;
	
	@GetMapping("/info")
	public String information(ModelMap modelMap, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("principal");
		user = userDetailsService.loadUserByUsername(user.getId());
		modelMap.addAttribute("user", user);
		return "site/mypage/info";
	}
	
	@PostMapping("/modif/info")
	@ResponseBody
	public ResponseEntity<?> modify_info(@RequestBody UserDTO userDTO) throws Exception {
		return new ResponseEntity<>(userService.modify(userDTO), HttpStatus.OK);
	}
	
}
