package katachi.spring.exercise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.OrderDetails;
import katachi.spring.exercise.repository.OrderDetailsMapper;
import katachi.spring.exercise.service.OrderDetailsService;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService{
	
	@Autowired
	private OrderDetailsMapper orderDetailsMapper;
	
	public void entry(List<OrderDetails> orderDetails) {
		orderDetailsMapper.entry(orderDetails);
	}

}
