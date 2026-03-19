package katachi.spring.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import katachi.spring.exercise.domain.LoginUser;
import katachi.spring.exercise.domain.TeamNews;
import katachi.spring.exercise.service.TeamNewsService;


@Controller
public class teamNewsController {
	
	@Autowired
	private TeamNewsService teamNewsService;
	
	@ModelAttribute("loginUserName")
	public String addLoginUserName(@AuthenticationPrincipal LoginUser loginUser) {
		if (loginUser != null) {
			return loginUser.getName();
		}
		return null;
	}
	
	//チーム情報一覧に遷移
	@GetMapping("/teamNews")
	public String getTeamNews(Model model, @AuthenticationPrincipal LoginUser loginUser, @RequestParam(value = "page", defaultValue = "1") int page, RedirectAttributes redirectAttributes) {
		
		final int size = 10;
		
		int offset = (page - 1) * size;
		int totalNumber = teamNewsService.countAll();
		
		//レコード数をsizeで割って、合計ページを計算する
		int totalPages = (int) Math.ceil((double) totalNumber / size);
		
		if (totalPages == 0) {
			
			totalPages = 1;
		}
	
		if (page > totalPages || page <= 0) {
			
			return "redirect:/teamNews";
		}
		
		List<TeamNews> teamNewsList = teamNewsService.getAll(offset);
		
		model.addAttribute("teamNewsList", teamNewsList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);

		return "teamNews/teamNews";
	}
	
	//チーム情報詳細に遷移
	@GetMapping("/teamNews/{id}")
	public String getTeamNewsDetail(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		
		if (teamNewsService.getOne(id) == null) {

			redirectAttributes.addFlashAttribute("unexsist", "指定されたページは存在していません。");
			return "redirect:/teamNews";
	}
		model.addAttribute("teamNewsDetail", teamNewsService.getOne(id));
		
		return "teamNews/teamNewsDetail";
	}

}
