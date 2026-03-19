package katachi.spring.exercise.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import katachi.spring.exercise.domain.TeamNews;

@Mapper
public interface TeamNewsMapper {
	
	public int countAll();
	
	public List<TeamNews>getAll(int offset);
	
	public TeamNews getOne(int id);
}
