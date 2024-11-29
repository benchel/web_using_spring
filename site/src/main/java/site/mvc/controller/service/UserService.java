package site.mvc.controller.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.mvc.dto.UserDTO;
import site.mvc.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	
	/**
	 * 아이디 중복 확인
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> checkOverlap(UserDTO userDTO) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		
		int count = userMapper.checkOverlap(userDTO);
		if(count > 0) {
			rs.put("msg", "해당 아이디는 중복되는 아이디로 사용하실 수 없습니다.");
			rs.put("result", false);
		} else {
			rs.put("msg", "사용할 수 있는 아이디입니다.");
			rs.put("result", true);
		}
		
		return rs;
	}
	
	/**
	 * 회원 생성
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Map<String, Object> create_user(UserDTO userDTO) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		
		int is_success = userMapper.insert(userDTO);
		if(is_success == 0) rs.put("result", false);
		else rs.put("result", true);
		
		return rs;
	}
	
}
