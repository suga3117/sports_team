package katachi.spring.exercise.service;

import java.util.List;

import katachi.spring.exercise.domain.TeamNews;

public interface TeamNewsService {

	public int countAll();
	
	public List<TeamNews>getAll(int offset);
	
	public TeamNews getOne(int id);
}
