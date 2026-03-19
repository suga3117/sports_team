package katachi.spring.exercise.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Orders {
	
	private Integer id;
	
	private Integer userId;
	
	private Date orderDate;
	
	private Integer sumPrice;

	private Items items;
	
	private OrderDetails orderDetails;
	
	private List<OrderDetails> orderDetailsList; 
}
