package katachi.spring.exercise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.Users;
import katachi.spring.exercise.repository.UsersMapper;
import katachi.spring.exercise.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {
	
	@Autowired
	private UsersMapper usersMapper;	

	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public void registUser(Users user) {
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		usersMapper.registUser(user);
	}
	
	@Override
	public Users getUser(String email) {
		return usersMapper.getUser(email);
	}
	
	@Override
	public void edit(Users user) {
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		usersMapper.edit(user);
	}
	
	@Override
	public Users getUserById(int id) {
		return usersMapper.getUserById(id);
	}
	
	@Override
	public int countEmail(String email) {
		return usersMapper.countEmail(email);
	}
}