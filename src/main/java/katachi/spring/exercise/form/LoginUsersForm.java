package katachi.spring.exercise.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUsersForm {
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;

}
