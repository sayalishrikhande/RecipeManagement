package com.recipes.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.recipes.springboot.models.Recipe;
import com.recipes.springboot.models.RecipeSearch;

@Service
public interface RecipeService {
	
	public List<Recipe> getRecipes();
	
	public Optional<Recipe> getRecipe(int id);
	
	public Recipe createRecipe(Recipe recipe);
	
	public Recipe updateRecipe(Recipe recipe);
	
	public String deleteRecipe(int id);
	
	public List<Recipe> searchRecipe(RecipeSearch recipe);

}
