package katachi.spring.exercise.domain;

import lombok.Data;

@Data
public class OrderDetails {
	
	private Integer id;
	
	private Integer orderId;
	
	private Integer itemId;
	
	private Integer num;
	
	private String size;
	
	private Items items;

}
