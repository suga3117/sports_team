package katachi.spring.exercise.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import katachi.spring.exercise.domain.OrderDetails;

@Mapper
public interface  OrderDetailsMapper {
	
	public void entry(List<OrderDetails> orderDetails);

}
