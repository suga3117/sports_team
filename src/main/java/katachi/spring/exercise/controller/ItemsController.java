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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import katachi.spring.exercise.domain.Carts;
import katachi.spring.exercise.domain.Items;
import katachi.spring.exercise.domain.LoginUser;
import katachi.spring.exercise.form.CartsForm;
import katachi.spring.exercise.form.GroupOrder;
import katachi.spring.exercise.service.CartsService;
import katachi.spring.exercise.service.ItemsService;

@Controller
public class ItemsController {

	@Autowired
	private ItemsService itemsService;

	@Autowired
	private CartsService cartsService;

	@Autowired
	private ModelMapper modelMapper;
	
	@ModelAttribute("loginUserName")
	public String addLoginUserName(@AuthenticationPrincipal LoginUser loginUser) {
		if (loginUser != null) {
			return loginUser.getName();
		}
		return null;
	}

	//グッズ一覧に遷移
	@GetMapping("/items")
	public String itemsMain(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {

		final int size = 8;

		int offset = (page - 1) * size;
		int totalNumber = itemsService.countAll();

		//レコード数をsizeで割って、合計ページを計算する
		int totalPages = (int) Math.ceil((double) totalNumber / size);

		if (totalPages == 0) {

			totalPages = 1;
		}

		if (page > totalPages || page <= 0) {

			return "redirect:/items";
		}

		List<Items> itemList = itemsService.getAll(offset);

		model.addAttribute("itemList", itemList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);

		return "items/items";
	}
	
	//グッズ詳細に遷移
	@GetMapping("/itemsDetail/{id}")
	public String getItemsDetail(Model model, @PathVariable("id") Integer id, CartsForm form, @AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes) {
		
		if (itemsService.getOne(id) == null) {

			redirectAttributes.addFlashAttribute("nonExsistItem", "nonExsistItem");
			return "redirect:/items";
		}

		model.addAttribute("item", itemsService.getOne(id));
		
		if (loginUser != null) {
			
			model.addAttribute("user", loginUser.getUserId());
		} else {
			
			model.addAttribute("user", 0);
			
		}

		return "items/itemsDetail";
	}

	//グッズを買い物カゴに追加する処理
	@PostMapping("/addToCarts")
	public String postAddToCart(Model model, @Validated(GroupOrder.class) CartsForm form, BindingResult result, @AuthenticationPrincipal LoginUser loginUser) {
		
		if (result.hasErrors()) {

			return getItemsDetail(model, form.getItemId(), form, loginUser, null);
		}
		Items items = itemsService.getOne(form.getItemId());
		if (items.getSizeCategory() == 0) {
		// S/M/L の商品
			if (!"S".equals(form.getSize()) && !"M".equals(form.getSize()) && !"L".equals(form.getSize())) {
				
				return getItemsDetail(model, form.getItemId(), form, loginUser, null);
			}
		} else if (items.getSizeCategory() == 1) {
			// F のみ
			if (!"F".equals(form.getSize())) {
				
				return getItemsDetail(model, form.getItemId(), form, loginUser, null);
			}
		}

		Carts findItem = cartsService.find(form.getItemId(), form.getSize(), loginUser.getUserId());

		if (findItem == null) {
			
			Carts carts = modelMapper.map(form, Carts.class);
			carts.setUserId(loginUser.getUserId());
			cartsService.add(carts);
		} else if (findItem != null) {
			
			cartsService.update(findItem.getId(), form.getNum() + findItem.getNum(), loginUser.getUserId(), findItem.getSize());
		}

		return "redirect:/items";
	}

}
