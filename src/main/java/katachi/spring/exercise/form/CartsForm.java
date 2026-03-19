package katachi.spring.exercise.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CartsForm {
	
	private Integer id;
	
	private Integer userId;
	
	private Integer itemId;
	
	@NotNull(groups=ValidGroup1.class)
	@NotBlank(groups=ValidGroup1.class)
	@Pattern(regexp = "S|M|L|F", message = "サイズが正しくありません。", groups=ValidGroup2.class)
	private String size;
	
	@NotNull(groups=ValidGroup1.class)
	@Min(value = 1, groups=ValidGroup1.class)
	@Max(value = 99, groups=ValidGroup1.class)
	
	private Integer num;

}
