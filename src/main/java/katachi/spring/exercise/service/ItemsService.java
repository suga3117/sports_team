package katachi.spring.exercise.service;

import java.util.List;

import katachi.spring.exercise.domain.Items;

public interface ItemsService {
	
	public int countAll();
	
	public List<Items>getAll(int offset);

	public Items getOne(int id);
}
