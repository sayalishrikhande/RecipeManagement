package com.recipes.springboot.models;

import java.util.List;

public class RecipeResult extends RecipeStatusResponse {

	public RecipeResult(Integer status, String message) {
		super(status, message);
	}

	private List<Recipe> recipes = null;

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public RecipeResult(Integer status, String message, List<Recipe> recipes) {
		super(status, message);
		this.setRecipes(recipes);
	}
}
