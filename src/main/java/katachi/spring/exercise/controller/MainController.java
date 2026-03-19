package katachi.spring.exercise.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import katachi.spring.exercise.domain.LoginUser;

@Controller
public class MainController {
	

	@ModelAttribute("loginUserName")
	public String addLoginUserName(@AuthenticationPrincipal LoginUser loginUser) {
		if (loginUser != null) {
			return loginUser.getName();
		}
		return null;
	}
	
	//トップページに遷移
	@GetMapping("/")
	public String getTopPage(Model model) {

		return "topPage";
	}

}
