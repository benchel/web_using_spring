package site.bean;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MngrAuthenticationManager implements AuthenticationManager {

	private final MngrAuthenticationProvider mngrAuthProvider;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return mngrAuthProvider.authenticate(authentication);
	}

}
