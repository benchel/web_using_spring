package site.mvc.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

/**
 * Spring Security Session에 저장될 오브젝트 타입 Authentication 
 * Authentication은 UserDetails 구현체만 가능.
 */
@Data
public class UserVO implements UserDetails {
	
	// 직렬화 ID (Serializable ID) 해당 객체의 버전을 명시하는 데 사용	
	private static final long serialVersionUID = 1L;
	// 권한
	private static final String authority = "USER";
	
	private String id;
	private String pwd;
	private String name;
	private String email;
	private String contact;
	private String jDate;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(this.authority));
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return this.pwd;
	}
	
	@Override
	public String getUsername() {
		return this.name;
	}

	/**
	 * 사용자의 계정이 만료되었는지 아닌지를 알린다. 
	 * 유효 기간이 만료된 계정은 계정이 진정한 것인지 입증할 수 없다.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/**
	 * 잠금 상태인 유저인지 아닌지를 알린다. 잠금된 유저는 인증할 수 없다. 
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 사용자의 자격이 유효한지 유효하지 않은 지를 알린다. 유효하지 않은 자격은 인증을 막는다.
	 * 만약 사용자의 자격 증명이 유효한 경우 true, 더 이상 유효하지 않은 경우 false.
	 */		
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * 활성화된 유저인지 비활성화된 유저인지 알린다. 비활성화된 유저는 인증할 수 없다.
	 * 활성화된 유저의 경우 true, 다른 기타의 경우 false
	 */	
	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getjDate() {
		return jDate;
	}

	public void setjDate(String jDate) {
		this.jDate = jDate;
	}

	public static String getAuthority() {
		return authority;
	}
}
