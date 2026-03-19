package katachi.spring.exercise.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShippingInfoForm {
	
	private Integer id;
		
	private Integer userId;
		
	@NotBlank
	@Length(max = 50, message="{fullName.Length}")
	private String fullName;
	
	@NotBlank
	@Length(max = 20, message="{postCode.Length}")
	private String zipCode;
	
	@NotBlank
	@Length(max = 20, message="{country.Length}")
	private String country;
	
	@NotBlank
	@Length(max = 20, message="{pref.Length}")
	private String pref;
		
	@NotBlank
	@Length(max = 20, message="{city.Length}")
	private String city;
		
	@Length(max = 30, message="{town.Length}")
	private String town;
		
	@NotBlank
	@Length(max = 20, message="{tel.Length}")
	private String tel;
}
