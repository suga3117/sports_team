package katachi.spring.exercise.domain;

import lombok.Data;

@Data
public class Items {
	
	private Integer id;
	
	private String itemName;
	
	private Integer price;
	
	private String image;
	
	private Integer sizeCategory;

}
