package katachi.spring.exercise.service;

import java.util.List;

import katachi.spring.exercise.domain.Carts;

public interface CartsService {

	public void add(Carts carts);
	
	public void update(int id, int num, int userId, String size);
	
	public Carts find(int itemId, String size, int userId);
	
	public List<Carts>cartList(int userId);
	
	public void deleteOne(int id, int userId);
	
	public void deleteAll(int id);
}
