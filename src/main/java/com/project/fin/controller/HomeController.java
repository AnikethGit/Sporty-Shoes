package com.project.fin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.fin.global.GlobalData;
import com.project.fin.service.CategoryService;
import com.project.fin.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	
	@GetMapping({"/" , "/home"})
	public String home(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "index";
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "shop";
	}
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model,@PathVariable int id) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		return "shop";  // we have to create a method to get product by category in order to use it on the basis of category
	}
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model,@PathVariable int id) {
		model.addAttribute("product", productService.getProductById((long) id).get()); // .get() is used because we've used optional
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "viewProduct";  
	}
}
