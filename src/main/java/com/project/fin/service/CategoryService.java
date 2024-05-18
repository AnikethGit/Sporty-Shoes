package com.project.fin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.fin.model.Category;
import com.project.fin.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	
	public List<Category> getAllCategory(){return categoryRepository.findAll();}
	public void addCategory(Category category) {categoryRepository.save(category);	}
	public void removeCategoryById(int id) { categoryRepository.deleteById(id);  }
	public Optional<Category> getCategoryById(int id) {return categoryRepository.findById(id);}
}



// we return optional of category, despite there's a category is present or not