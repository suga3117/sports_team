package katachi.spring.exercise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.Carts;
import katachi.spring.exercise.repository.CartsMapper;
import katachi.spring.exercise.service.CartsService;

@Service
public class CartsServiceImpl implements CartsService{
	
	@Autowired
	private CartsMapper cartsMapper;
	
	@Override
	public void add(Carts carts) {
		cartsMapper.add(carts);
	}
	
	@Override
	public void update(int id, int num, int userId, String size) {
		cartsMapper.update(id, num, userId, size);
	}
	
	@Override
	public Carts find(int itemId, String size, int userId) {
		return cartsMapper.find(itemId, size, userId);
	}
	
	@Override
	public List<Carts>cartList(int userId){
		return cartsMapper.cartList(userId);
	}
	
	@Override
	public void deleteOne(int id, int userId) {
		cartsMapper.deleteOne(id, userId);
	}
	
	@Override
	public void deleteAll(int id) {
		cartsMapper.deleteAll(id);
	}

}
