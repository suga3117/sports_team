package katachi.spring.exercise.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import katachi.spring.exercise.domain.Orders;

@Mapper
public interface OrdersMapper {
	
	public int countOrderHistory(int userId);

	public List<Orders>getOrderHistory(int id, int offset);
	
	public void entry(Orders orders);
}
