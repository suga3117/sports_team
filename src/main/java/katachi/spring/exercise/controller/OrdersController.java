package katachi.spring.exercise.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import katachi.spring.exercise.domain.Carts;
import katachi.spring.exercise.domain.LoginUser;
import katachi.spring.exercise.domain.OrderDetails;
import katachi.spring.exercise.domain.Orders;
import katachi.spring.exercise.form.CartsForm;
import katachi.spring.exercise.service.CartsService;
import katachi.spring.exercise.service.OrderDetailsService;
import katachi.spring.exercise.service.OrdersService;
import katachi.spring.exercise.service.UserInfosService;

@Controller
public class OrdersController {
	
	@Autowired
	private UserInfosService userInfosService;
	
	@Autowired
	private CartsService cartsService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private	OrderDetailsService orderDetailsService;
	
	@ModelAttribute("loginUserName")
	public String addLoginUserName(@AuthenticationPrincipal LoginUser loginUser) {
		if (loginUser != null) {
			return loginUser.getName();
		}
		return null;
	}
	
	//注文確認画面に遷移
	@PostMapping("/checkOrder")
	public String postCheckOrder(Model model, CartsForm form, @AuthenticationPrincipal LoginUser loginUser) {
		
		List<Carts> cartsList = cartsService.cartList(loginUser.getUserId());

		// 合計金額を計算
		int sumPrice = cartsList.stream()
				.mapToInt(item -> item.getNum() * item.getItems().getPrice())
				.sum();

		model.addAttribute("sumPrice", sumPrice);
		model.addAttribute("cartsList", cartsList);
		model.addAttribute("shippingInfo", userInfosService.getInfo(loginUser.getUserId()));
		
		return "orders/checkOrder";
	}
	
	//注文確定処理
	@PostMapping("/completedOrder")
	public String postCompletedOrder(Model model, CartsForm form, @AuthenticationPrincipal LoginUser loginUser) {
		
		if (userInfosService.count(loginUser.getUserId()) == 0) {

			model.addAttribute("creatAddress", "（発送先を入力してください）");
			return postCheckOrder(model, form, loginUser);
		}
		
		List<Carts> cartsList = cartsService.cartList(loginUser.getUserId());

		int sumPrice = cartsList.stream()
				.mapToInt(item -> item.getNum() * item.getItems().getPrice())
				.sum();

		Orders orders = new Orders();
		orders.setUserId(loginUser.getUserId());
		orders.setSumPrice(sumPrice);

		ordersService.entry(orders);
		
		
		List<OrderDetails> orderDetails = new ArrayList<>();
		for (Carts cart : cartsList) {
			OrderDetails detail = new OrderDetails();
			detail.setOrderId(orders.getId()); // 自動採番された注文ID
			detail.setItemId(cart.getItemId());
			detail.setNum(cart.getNum());
			detail.setSize(cart.getSize());
			orderDetails.add(detail);
		}
		orderDetailsService.entry(orderDetails);

		cartsService.deleteAll(loginUser.getUserId());
		
	
		return "orders/completedOrder";
	}

}
