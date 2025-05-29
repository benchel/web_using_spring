package site.mvc.controller.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.mvc.mapper.UserMapper;
import site.mvc.vo.UserVO;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailService implements UserDetailsService {

	private final UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			UserVO user = userMapper.searchUserById(username);
			return user;
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
}
