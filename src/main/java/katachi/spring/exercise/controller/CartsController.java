package katachi.spring.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import katachi.spring.exercise.domain.Carts;
import katachi.spring.exercise.domain.Items;
import katachi.spring.exercise.domain.LoginUser;
import katachi.spring.exercise.form.CartsForm;
import katachi.spring.exercise.form.GroupOrder;
import katachi.spring.exercise.service.CartsService;
import katachi.spring.exercise.service.ItemsService;

@Controller
public class CartsController {

	@Autowired
	private ItemsService itemsService;
	
	@Autowired
	private CartsService cartsService;
	
	@ModelAttribute("loginUserName")
	public String addLoginUserName(@AuthenticationPrincipal LoginUser loginUser) {
		if (loginUser != null) {
			return loginUser.getName();
		}
		return null;
	}
	
	//買い物カゴ一覧に遷移
	@GetMapping("/basket")
	public String getBasket(Model model, @Validated CartsForm form, BindingResult result, @AuthenticationPrincipal LoginUser loginUser) {
		
		List<Carts> cartsList = cartsService.cartList(loginUser.getUserId());

		// 合計金額を計算
		int sumPrice = cartsList.stream()
				.mapToInt(item -> item.getNum() * item.getItems().getPrice())
				.sum();

		model.addAttribute("sumPrice", sumPrice);
		model.addAttribute("cartsList", cartsList);
		
		return "items/carts";
	}
	
	//買い物カゴのグッズを変更の変更画面
	@GetMapping("/edit/{id}/{itemId}")
	public String getEdit(Model model, @PathVariable("id") Integer id, @PathVariable("itemId") Integer itemId, CartsForm form) {
		
		model.addAttribute("item", itemsService.getOne(itemId));
		model.addAttribute("id", id);
		return "items/cartsEdit";
	}
	
	//グッズ変更処理
	@PostMapping("/cartsEdit")
	public String postCartsEdit(Model model, @Validated(GroupOrder.class) CartsForm form, BindingResult result, @AuthenticationPrincipal LoginUser loginUser) {
		
		if (result.hasErrors()) {
			
			return getEdit(model, form.getId(), form.getItemId(), form);
		}
		
		Items items = itemsService.getOne(form.getItemId());
		
		if (items.getSizeCategory() == 0) {
		// S/M/L の商品
			if (!"S".equals(form.getSize()) && !"M".equals(form.getSize()) && !"L".equals(form.getSize())) {
				
				return getEdit(model, form.getId(), form.getItemId(), form);
			}
		} else if (items.getSizeCategory() == 1) {
			// F のみ
			if (!"F".equals(form.getSize())) {
				
				return getEdit(model, form.getId(), form.getItemId(), form);
			}
		}
		
		Carts findItem = cartsService.find(form.getItemId(), form.getSize(), loginUser.getUserId());

		if (findItem != null) {
			cartsService.update(findItem.getId(), form.getNum(), loginUser.getUserId(), form.getSize());
			
			if (!findItem.getId().equals(form.getId())) {
				cartsService.deleteOne(form.getId(), loginUser.getUserId());
			}
		} else {
			
			cartsService.update(form.getId(), form.getNum(), loginUser.getUserId(), form.getSize());	
			
		}
		
		return "redirect:/items";
	}
	
	//買い物カゴのグッズ削除処理
	@GetMapping("/delete/{id}")
	public String getDelete(Model model, @PathVariable("id") Integer id, CartsForm form, @AuthenticationPrincipal LoginUser loginUser) {
		
		cartsService.deleteOne(id, loginUser.getUserId());
		
		return getBasket(model, form, null, loginUser);
	}
	

}
