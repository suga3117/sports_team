package katachi.spring.exercise.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.LoginUser;
import katachi.spring.exercise.domain.Users;
import katachi.spring.exercise.service.UsersService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsersService usersservice;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

		Users users = usersservice.getUser(username);//ユーザー取得

		if(users == null) {
			throw new UsernameNotFoundException("user not found");
			//例外発生
		}

		//権限リスト作成
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		List<GrantedAuthority>authorities = new ArrayList<>();
		authorities.add(authority);		
		
		return new LoginUser(
				users.getEmail(),
				users.getPassword(),
				authorities,
				users.getId(),
				users.getName()
			);
	
	}

}