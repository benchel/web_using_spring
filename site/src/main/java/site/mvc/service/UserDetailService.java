package site.mvc.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.mvc.mapper.UserMapper;
import site.mvc.vo.UserVO;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
	
	private final UserMapper userMapper;
	
	@Override
	public UserVO loadUserByUsername(String id) throws UsernameNotFoundException {
		try {
			UserVO user = userMapper.searchUserById(id);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
