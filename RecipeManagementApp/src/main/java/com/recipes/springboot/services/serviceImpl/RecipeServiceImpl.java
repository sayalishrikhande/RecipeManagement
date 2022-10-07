package com.recipes.springboot.services.serviceImpl;

import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipes.springboot.constants.Messages;
import com.recipes.springboot.exception.ResourceNotFoundException;
import com.recipes.springboot.models.Recipe;
import com.recipes.springboot.models.RecipeSearch;
import com.recipes.springboot.repositories.RecipeRepository;
import com.recipes.springboot.services.RecipeService;
import com.recipes.springboot.util.DateUtil;

@Service
public class RecipeServiceImpl implements RecipeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeServiceImpl.class);

	@Autowired
	RecipeRepository repo;

	@Override
	public List<Recipe> getRecipes() {
		List<Recipe> myList = repo.findAll();
		LOGGER.info("Here   " + myList.size());
		return myList;
	}

	@Override
	public Optional<Recipe> getRecipe(int id) {
		Assert.notNull(id, "ID is null");
		Optional<Recipe> recipe = repo.findById(id);
		return recipe;
	}

	@Override
	public Recipe createRecipe(Recipe recipe) throws NullPointerException {
		Assert.notNull(recipe, "Input is null");
		if(null == recipe.getIngredients()) {
			throw new NullPointerException("Ingredients must be present");
		}
		if(null == recipe.getInstructions()) {
			throw new NullPointerException("Instructions must be present");
		}			
		recipe.setCreated_Date(DateUtil.getCurrentDateTime());
		recipe.setUpdated_Date(DateUtil.getCurrentDateTime());
		Recipe savedRecipe = repo.save(recipe);
		return savedRecipe;
	}

	@Override
	public Recipe updateRecipe(Recipe recipe) throws NumberFormatException{
		Assert.notNull(recipe, "Request is null");
		Assert.notNull(recipe.getId(), "ID is null");
		if(0 < recipe.getId()) {
			throw new NumberFormatException("ID required");
		}
		recipe.setUpdated_Date(DateUtil.getCurrentDateTime());
		LOGGER.info("Here in update");

		Recipe updateRecipe = repo.findById(recipe.getId())
				.orElseThrow(() -> new ResourceNotFoundException(Messages.NOT_FOUND.getMessage()));

		if (updateRecipe != null) {
			LOGGER.info("Found by id " + updateRecipe.getId());
			updateRecipe = repo.save(recipe);
		}
		return updateRecipe;
	}

	@Override
	public String deleteRecipe(int id) {
		Assert.notNull(id, "ID is null");
		Recipe deleteRecipe = repo.findById(id).orElse(null);

		if (null != deleteRecipe) {
			repo.deleteById(id);
			return "Deleted ID is " + id;
		} else {
			return "Not found ID " + id;
		}
	}

	@Override
	public List<Recipe> searchRecipe(RecipeSearch recipe) {
		List<Recipe> recipes = repo.findAll();
		LOGGER.info("Total Recipes " + recipes.size());
		List<Recipe> recipes1 = null;
		try {
			if (recipe.getName() != null) {
				LOGGER.info("Name is " + recipe.getName());
				recipes1 = recipes.stream()
						.filter(x -> x.getName().toLowerCase().contains(recipe.getName().toLowerCase()))
						.collect(Collectors.toList());
				recipes = recipes1;
			}

			if (recipe.getIncludeIngredients() != null) {
				recipes1 = recipes.stream()
						.filter(x -> x.getIngredients().toLowerCase().contains(recipe.getIncludeIngredients()))
						.collect(Collectors.toList());
				recipes = recipes1;
			}

			if (recipe.getServings() > 0) {
				recipes1 = recipes.stream().filter(x -> x.getServings() == recipe.getServings())
						.collect(Collectors.toList());
				recipes = recipes1;
			}

			if (recipe.getExcludeIngredients() != null) {
				LOGGER.info("Here excludeIngredients");
				recipes1 = recipes.stream().filter(
						x -> !(x.getIngredients().toLowerCase().contains(recipe.getExcludeIngredients().toLowerCase())))
						.collect(Collectors.toList());
				recipes = recipes1;
			}

			if (recipe.getInstructions() != null) {
				LOGGER.info("Here instructions");
				recipes1 = recipes.stream()
						.filter(x -> x.getInstructions().toLowerCase().contains(recipe.getInstructions().toLowerCase()))
						.collect(Collectors.toList());
				recipes = recipes1;
			}

			if (recipe.isVeg() != null) {
				LOGGER.info("Here  in Veg");
				recipes1 = recipes.stream().filter(x -> x.isVeg() == recipe.isVeg()).collect(Collectors.toList());
			}
			
			if (recipes1 != null) {
				LOGGER.info("Recipes found for criteria " + recipes1.size());
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return recipes1;
	}

}
