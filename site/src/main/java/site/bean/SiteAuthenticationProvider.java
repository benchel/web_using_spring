package site.bean;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.mvc.controller.service.UserDetailService;
import site.mvc.vo.UserVO;

@Component
@RequiredArgsConstructor
@Slf4j
public class SiteAuthenticationProvider implements AuthenticationProvider {
	
	private final UserDetailService userDetailService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String name = (String) authentication.getName();
		String id = (String) authentication.getPrincipal();
		String pwd = (String) authentication.getCredentials();

		log.info("input name : " + name);
		log.info("input id : " + id);
		log.info("input pwd : " + pwd);
		
		try {
			UserVO user = userDetailService.loadUserByUsername(id);
			
			if(!pwd.equals(user.getPwd())) {
				authentication.setAuthenticated(false);
			} else {
				ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(user.getAuthority()));
				new UsernamePasswordAuthenticationToken(user, pwd, authorities).setDetails(user);
				authentication = new UsernamePasswordAuthenticationToken(user, pwd, authorities);
			}
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
