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

@Component
@RequiredArgsConstructor
@Slf4j
public class MngrAuthenticationProvider implements AuthenticationProvider {
	
	//private final UserDetailService userDetailService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String name = (String) authentication.getName();
		String id = (String) authentication.getPrincipal();
		String pwd = (String) authentication.getCredentials();

		log.info("manger logging try....");
		log.info("input name : " + name);
		log.info("input id : " + id);
		log.info("input pwd : " + pwd);
		
		try {

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
