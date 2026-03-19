package katachi.spring.exercise.repository;

import org.apache.ibatis.annotations.Mapper;

import katachi.spring.exercise.domain.UserInfos;

@Mapper
public interface UserInfosMapper {
	
	public void regist(UserInfos userInfos);
	
	public UserInfos getInfo(int userId);
	
	public void update(UserInfos userInfos);
	
	public int count(int userId);

}
