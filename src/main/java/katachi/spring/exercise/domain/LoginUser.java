package katachi.spring.exercise.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User {

	private final Integer userId;
	private final String name;

	public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer userId, String name) {
		
		super(username, password, authorities);
		this.userId = userId;
		this.name = name;
	}

	public Integer getUserId() {
		
		return userId;
	}

	public String getName() {
		
		return name;
	}
}