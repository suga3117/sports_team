package katachi.spring.exercise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.TeamNews;
import katachi.spring.exercise.repository.TeamNewsMapper;
import katachi.spring.exercise.service.TeamNewsService;


@Service
public class TeamNewsServiceImpl implements TeamNewsService{
	
	@Autowired
	private TeamNewsMapper teamNewsMapper;
	
	
	@Override
	public int countAll() {
		return teamNewsMapper.countAll();
	}
	
	@Override
	public List<TeamNews>getAll(int offset){
		return teamNewsMapper.getAll(offset);
	}
	
	@Override
	public TeamNews getOne(int id){
		return teamNewsMapper.getOne(id);
	}

}
