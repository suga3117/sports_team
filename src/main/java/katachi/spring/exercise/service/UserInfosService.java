package katachi.spring.exercise.service;

import katachi.spring.exercise.domain.UserInfos;

public interface UserInfosService {
	
	public void regist(UserInfos userInfos);
	
	public UserInfos getInfo(int userId);
	
	public void update(UserInfos userInfos);
	
	public int count(int userId);

}
