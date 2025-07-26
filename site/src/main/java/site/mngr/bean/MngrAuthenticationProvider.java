package site.mngr.bean;

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
import site.mvc.service.ManagerService;
import site.mvc.vo.ManagerVO;

@Component
@RequiredArgsConstructor
@Slf4j
public class MngrAuthenticationProvider implements AuthenticationProvider {
	
	private final ManagerService managerService;
	
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
			ManagerVO manager = managerService.findManager(id);
			
			if(!pwd.equals(manager.getPwd())) {
				authentication.setAuthenticated(false);
			} else {
				ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(manager.getAuthority()));
				new UsernamePasswordAuthenticationToken(manager, pwd, authorities).setDetails(manager);
				authentication = new UsernamePasswordAuthenticationToken(manager, pwd, authorities);
			}
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
