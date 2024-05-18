package com.project.fin.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.fin.dto.ProductDTO;
import com.project.fin.model.Category;
import com.project.fin.model.Product;
import com.project.fin.service.CategoryService;
import com.project.fin.service.ProductService;
import com.project.fin.service.UserService;

@Controller
public class AdminController {
	public static String uploadDir= System.getProperty("user.dir")+"/src/main/resources/static/productImages";   //System.getProperty("user.dir"): This will display the path for the root folder(src).
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;

	@GetMapping("/admin")
	public String adminHome() {
		return "adminHome";
	}

	@GetMapping("/admin/categories")
	public String getCat(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}

	@GetMapping("/admin/categories/add") // get requests from categories.html
	public String getCatAdd(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}

	@PostMapping("/admin/categories/add") // get requests from categories.html
	public String postCatAdd(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model model) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if (category.isPresent()) {
			model.addAttribute("category", category.get()); 					// To extract data from optional, use - .get()
			return "categoriesAdd";
		} else {
			return "404";
		}
	}
	
	// Product section
	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("products",productService.getAllProduct());	
		return "products";
	}
	
	// Opens add product
	@GetMapping("/admin/products/add")
	public String productAddGet(Model model) {
		model.addAttribute("productDTO",new ProductDTO(null, null, 0, 0, 0, null, null));              
		model.addAttribute("categories", categoryService.getAllCategory());	    // we return here empty productDTO, and list of categories
	return "productsAdd";
	}
	
	// To add products
	@PostMapping("/admin/products/add")
	public String productAddPost(@ModelAttribute("productDTO") ProductDTO productDTO,
								@RequestParam("productImage") MultipartFile file,
								@RequestParam ("imgName") String imgName) throws IOException {
		
		
		Product product=new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		
		
		String imageUUID;
		if(!file.isEmpty()) {
			imageUUID =file.getOriginalFilename();        // to get the file name
			Path fileNameAndPath =Paths.get(uploadDir, imageUUID);      // gives name and path of file
			Files.write(fileNameAndPath, file.getBytes());              // Files.write = to save the file, file name and path
		}else{                                       // file empty:product will be updated and not added
	
			imageUUID=imgName;
		}
		product.setImageName(imageUUID);   // to save the product
		productService.addProduct(product);    // saves the product and redirects it
		return "redirect:/admin/products";
	}
	
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable int id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
		public String updateProductGet(@PathVariable long id, Model model) {
		Product product =productService.getProductById(id).get();
		
		ProductDTO productDTO =new ProductDTO(id, null, 0, id, id, null, null);
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId((product.getCategory().getId()));
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight((product.getWeight()));
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("productDTO", productDTO);    // when we click on update all, the inserted information is shown on  the front-end
		
		return "productsAdd";
		
	}

	
	// USER SECTION: list of Users
		@GetMapping("/admin/users")
		public String users(Model model) {
			model.addAttribute("users",userService.getAllUser());	
			return "users";
		}
		
		@GetMapping("/admin/user/delete/{id}")    
		public String deleteUser(@PathVariable int id) {
			userService.removeUserById(id);
			return "redirect:/admin/users";
		}
}
