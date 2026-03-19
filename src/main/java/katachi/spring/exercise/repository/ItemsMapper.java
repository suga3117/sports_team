package katachi.spring.exercise.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import katachi.spring.exercise.domain.Items;

@Mapper
public interface ItemsMapper {
	
	public int countAll();
	
	public List<Items>getAll(int offset);

	public Items getOne(int id);
}
