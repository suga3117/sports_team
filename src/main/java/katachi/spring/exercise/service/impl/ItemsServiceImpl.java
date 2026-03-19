package katachi.spring.exercise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.Items;
import katachi.spring.exercise.repository.ItemsMapper;
import katachi.spring.exercise.service.ItemsService;

@Service
public class ItemsServiceImpl implements ItemsService{
	
	@Autowired
	private ItemsMapper itemsMapper;
	
	
	@Override
	public int countAll() {
		return itemsMapper.countAll();
	}
	
	@Override
	public List<Items>getAll(int offset){
		return itemsMapper.getAll(offset);
	}
	
	@Override
	public Items getOne(int id) {
		return itemsMapper.getOne(id);
	}

}
