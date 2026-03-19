package katachi.spring.exercise.domain;

import lombok.Data;

@Data
public class Carts {
	
	private Integer id;
	
	private Integer userId;
	
	private Integer itemId;
	
	private String size;
	
	private Integer num;
	
	private Items items;

}
