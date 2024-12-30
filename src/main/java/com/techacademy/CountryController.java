package com.techacademy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("country")
public class CountryController {

	private final CountryService service;

	public CountryController(CountryService service) {
		this.service = service;
	}

	//-----------一覧画面--------------
	@GetMapping("/list")
	public String getList(Model model) {
		//全件検索結果をModelに登録
		model.addAttribute("countrylist", service.getCountryList());
		//country/list.htmlに画面遷移
		return "country/list";
	}

	//------詳細画面-------
	@GetMapping("/detail/{code}")
	public String getCountry(@PathVariable(name = "code", required = false) String code, Model model) {
		//codeが指定されていたら検索結果、なければ空のクラスを設定
		Country country = service.getCountry(code);//code != null ? service.getCountry(code) : new Country();
		//Modelに登録
		model.addAttribute("country", country);
		//country/detail.htmlに画面遷移
		return "country/detail";
	}

	//-----更新（追加）-----
	@PostMapping("/detail")
	public String postCountry(@RequestParam("code") String code, @RequestParam("name") String name, @RequestParam("population") int population, Model model) {
		//更新（追加）
		service.updateCountry(code, name, population);

		//一覧画面にリダイレクト
		return "redirect:/country/list";
	}

	//----削除画面----
	@GetMapping("/delete/{code}")
	public String deleteCountryForm(@PathVariable("code") String code, Model model) {
		model.addAttribute("code", code);
		//country/delete.htmlに画面遷移
		return "country/delete";
	}

	//----削除----
	@PostMapping("/delete/{code}")
	public String deleteCountry(@PathVariable("code") String code, Model model) {
		//削除
		service.deleteCountry(code);
		//一覧画面にリダイレクト
		return "redirect:/country/list";
	}

	//---追加：ここまで------
}
