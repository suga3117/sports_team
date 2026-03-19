package katachi.spring.exercise.repository;

import org.apache.ibatis.annotations.Mapper;

import katachi.spring.exercise.domain.Users;

@Mapper
public interface UsersMapper {

	public void registUser(Users user);

	public Users getUser(String email);
	
	public void edit(Users user);

	public Users getUserById(int id);
	
	public int countEmail(String email);
}
