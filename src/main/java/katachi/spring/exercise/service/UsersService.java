package katachi.spring.exercise.service;

import katachi.spring.exercise.domain.Users;

public interface UsersService {

	public void registUser(Users user);

	public Users getUser(String email);
	
	public void edit(Users user);
	
	public Users getUserById(int id);
	
	public int countEmail(String email);

}
