package katachi.spring.exercise.service;

import java.util.List;

import katachi.spring.exercise.domain.Orders;

public interface OrdersService {
	
	public int countOrderHistory(int userId);
	
	public List<Orders>getOrderHistory(int id, int offset);
	
	public void entry(Orders orders);
}
