package katachi.spring.exercise.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EditLoginUserInfoForm {
	
	private Integer id;
	
	@NotBlank
	@Length(max = 50, message="{name.Length}")
	private String name;
	
	@NotBlank
	@Length(max = 256, message="{email.Length}")
	private String email;
	
	@NotBlank
	@Length(max = 256, message="{confirmEmail.Length}")
	private String confirmEmail;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message="{password.Pattern}")
	private String password;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message="{confirmPassword.Pattern}")
	private String confirmPassword;

}
