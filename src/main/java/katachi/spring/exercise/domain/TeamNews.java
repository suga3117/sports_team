package katachi.spring.exercise.domain;

import java.util.Date;

import lombok.Data;

@Data
public class TeamNews {
	
	private Integer id;
	
	private Date postingDate;
	
	private String title;
	
	private String article;

}
