package site.common.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 스프링 시큐리티 설정
 * https://docs.spring.io/spring-security/reference/servlet/getting-started.html
 * 
 */
public class SpringSecurityConfig {

	// Spring Security에서 제외할 웹 리소스 패스
	public static final String[] SECURITY_EXCLUDE_PATTERN_ARR = {
		"/", "/css/**", "/js/**", "/img/**"
	};
	
	// 웹사이트 사용자의 접근과 권한 정의
	public static class UserSecurityConfing {
		
		// 인증(authentication)이 필요한 url과 인증이 불필요한 url 설정
		// 권한이 필요한 url 설정
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {				
			return null;
		}
	}
	
	// 웹사이트 관리자의 접근과 권한 정의
	public static class MngrSecurityConfig {
		
		// 인증(authentication)이 필요한 url과 인증이 불필요한 url 설정
		// 권한이 필요한 url 설정 
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			return null;
		}		
	}
	
}
