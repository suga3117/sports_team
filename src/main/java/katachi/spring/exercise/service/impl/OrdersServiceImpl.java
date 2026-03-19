package katachi.spring.exercise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.Orders;
import katachi.spring.exercise.repository.OrdersMapper;
import katachi.spring.exercise.service.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService{

	@Autowired
	private OrdersMapper ordersMapper;
	
	
	@Override
	public int countOrderHistory(int userId) {
		return ordersMapper.countOrderHistory(userId);
	}
	
	@Override
	public List<Orders>getOrderHistory(int id, int offset) {
		return ordersMapper.getOrderHistory(id, offset);
	}
	
	@Override
	public void entry(Orders orders) {
		ordersMapper.entry(orders); 
	}
}
