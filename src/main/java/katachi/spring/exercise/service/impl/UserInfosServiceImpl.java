package katachi.spring.exercise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.UserInfos;
import katachi.spring.exercise.repository.UserInfosMapper;
import katachi.spring.exercise.service.UserInfosService;

@Service
public class UserInfosServiceImpl implements UserInfosService{
	
	@Autowired
	private UserInfosMapper userInfosMapper;
	
	@Override
	public void regist(UserInfos userInfos){
		userInfosMapper.regist(userInfos);
	}
	
	@Override
	public UserInfos getInfo(int userId){
		return userInfosMapper.getInfo(userId);
	}
	
	@Override
	public void update(UserInfos userInfos) {
		userInfosMapper.update(userInfos);
	}
	
	@Override
	public int count(int userId) {
		return userInfosMapper.count(userId);
	}

}
