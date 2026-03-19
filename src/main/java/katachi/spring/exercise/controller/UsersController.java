package katachi.spring.exercise.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import katachi.spring.exercise.domain.LoginUser;
import katachi.spring.exercise.domain.Orders;
import katachi.spring.exercise.domain.UserInfos;
import katachi.spring.exercise.domain.Users;
import katachi.spring.exercise.form.EditLoginUserInfoForm;
import katachi.spring.exercise.form.LoginUsersForm;
import katachi.spring.exercise.form.RegistUsersForm;
import katachi.spring.exercise.form.ShippingInfoForm;
import katachi.spring.exercise.service.OrdersService;
import katachi.spring.exercise.service.UserInfosService;
import katachi.spring.exercise.service.UsersService;

@Controller
public class UsersController {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UserInfosService userInfosService;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdersService ordersService;
	
	@ModelAttribute("loginUserName")
	public String addLoginUserName(@AuthenticationPrincipal LoginUser loginUser) {
		if (loginUser != null) {
			return loginUser.getName();
		}
		return null;
	}

	//ユーザー新規登録
	@GetMapping("/register")
	public String getRegister(Model model, RegistUsersForm form) { 

		return "user/register";
	}

	//ユーザー新規登録処理
	@PostMapping("/register")
	public String postRegister(Model model, @Validated RegistUsersForm form, BindingResult result) {

		// アプリケーションレベルのバリデーションも含めて常にチェック
		boolean hasAppValidationErrors = false;

		// メールアドレス一致チェック
		if (!form.getEmail().equals(form.getConfirmEmail())) {
			result.rejectValue("confirmEmail", "emailMismatch", "メールアドレスが一致しません。");
			hasAppValidationErrors = true;
		}

		// パスワード一致チェック
		if (!form.getPassword().equals(form.getConfirmPassword())) {
			result.rejectValue("confirmPassword", "passwordMismatch", "パスワードが一致しません。");
			hasAppValidationErrors = true;
		}

		// メールアドレスの重複チェック（既に登録されている場合）
		if (usersService.countEmail(form.getEmail()) >= 1) {
			model.addAttribute("existEmail", "入力したEmailは既に登録されています。");
			hasAppValidationErrors = true;
		}

		// バリデーション or アプリケーションレベルチェックでエラーがあれば戻す
		if (result.hasErrors() || hasAppValidationErrors) {
			return "user/register";
		}
		
		Users user = modelMapper.map(form, Users.class);
		
		usersService.registUser(user); 

		return "topPage";
	}
	
	//ログイン画面に遷移
	@GetMapping("/login")
	public String getLogin(Model model, LoginUsersForm form) {	

		return "user/login";
	}
	
	//マイページ画面に遷移
	@GetMapping("/myPage")
	public String getMyPage(Model model, @AuthenticationPrincipal LoginUser loginUser,@RequestParam(value = "page", defaultValue = "1") int page) {
		
		final int size = 10;
		
		int offset = (page - 1) * size;
		int totalNumber = ordersService.countOrderHistory(loginUser.getUserId());
		
		//レコード数をsizeで割って、合計ページを計算する
		int totalPages = (int) Math.ceil((double) totalNumber / size);
		
		if (totalPages == 0) {
			
			totalPages = 1;
		}
	
		if (page > totalPages || page <= 0) {
			
			return "redirect:/items";
		}
		
		
		List<Orders>orderHistoryList = ordersService.getOrderHistory(loginUser.getUserId(), offset);
		
		model.addAttribute("orderHistoryList", orderHistoryList);
		model.addAttribute("loginInfo", usersService.getUser(loginUser.getUsername()));
		model.addAttribute("shippingInfo", userInfosService.getInfo(loginUser.getUserId()));
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);

		return "user/myPage";
	}
	
	//ログインユーザー情報の変更画面に遷移
	@GetMapping("/editLoginUserInfo")
	public String getEditLoginUserInfo(Model model, EditLoginUserInfoForm form, @AuthenticationPrincipal LoginUser loginUser) {
		
		Users users = usersService.getUserById(loginUser.getUserId());
		form.setName(users.getName());
		form.setEmail(users.getEmail());
		
		model.addAttribute("editLoginUserInfoForm", form);

		return "user/editLoginUserInfo";
	}
	
	//ログインユーザー情報の変更
	@PostMapping("/editLoginUserInfo")
	public String postEditLoginUserInfo(Model model, @Validated EditLoginUserInfoForm form, BindingResult result, @AuthenticationPrincipal LoginUser loginUser) {
		
		if (result.hasErrors()) {
			return "user/editLoginUserInfo";
		}
		
		if (!form.getPassword().equals(form.getConfirmPassword())) {
			
			result.rejectValue("confirmPassword", "passwordMismatch");
			return "user/editLoginUserInfo";
		}
		
		if (!form.getEmail().equals(form.getConfirmEmail())) {
			
			result.rejectValue("confirmEmail", "emailMismatch");
			return "user/editLoginUserInfo";
		}
		
		form.setId(loginUser.getUserId());
		Users user = modelMapper.map(form, Users.class);
		
		usersService.edit(user); 

		return "redirect:/myPage";
	}
	
	//お届け先登録/変更画面に遷移
	@GetMapping("/shippingInfo")
	public String getShippingInfo(Model model, ShippingInfoForm form, @AuthenticationPrincipal LoginUser loginUser) {
		
		if (userInfosService.getInfo(loginUser.getUserId()) == null) {
			
			model.addAttribute("shippingInfoForm", new ShippingInfoForm());
		} else {
			model.addAttribute("shippingInfoForm", userInfosService.getInfo(loginUser.getUserId()));
		}
		
		return "user/shippingInfo";
	}
	
	//お届け先登録/変更変更
	@PostMapping("/shippingInfo")
	public String postShippingInfo(Model model, @Validated ShippingInfoForm form, BindingResult result, @AuthenticationPrincipal LoginUser loginUser) {
		
		if (result.hasErrors()) {
			return "user/shippingInfo";
		}

		
		UserInfos userInfos = modelMapper.map(form, UserInfos.class);
		userInfos.setUserId(loginUser.getUserId());
		
		if (userInfosService.getInfo(loginUser.getUserId()) == null) {
			
			userInfosService.regist(userInfos);
		} else {
			userInfosService.update(userInfos);
		}

		return "redirect:/myPage";
	}


}
