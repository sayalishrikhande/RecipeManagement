package com.recipes.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.recipes.springboot.constants.Messages;
import com.recipes.springboot.models.AthenticationResponse;
import com.recipes.springboot.models.AuthenticationRequest;
import com.recipes.springboot.models.Recipe;
import com.recipes.springboot.models.RecipeActionResult;
import com.recipes.springboot.models.RecipeResult;
import com.recipes.springboot.models.RecipeSearch;
import com.recipes.springboot.models.RecipeSearchResult;
import com.recipes.springboot.repositories.RecipeRepository;
import com.recipes.springboot.services.RecipeService;
import com.recipes.springboot.services.serviceImpl.MyUserDetailsServiceImpl;
import com.recipes.springboot.util.RecipeManagementUtil;
import com.recipes.springboot.util.ValidateInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/recipes/v1")
public class RecipeManagementController {

	@Autowired
	RecipeRepository repo;

	@Autowired
	private RecipeService recipeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeManagementController.class);

	/**
	 * Returns all the saved recipes 
	 * 
	 * @param getRecipes
	 * @return ResponseEntity<RecipeResult>
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<RecipeResult> getRecipes() {

		List<Recipe> recipes = null;
		ResponseEntity<RecipeResult> recipeResult = new ResponseEntity<RecipeResult>(new RecipeResult(1, null),
				HttpStatus.OK);
		try {
			recipes = recipeService.getRecipes();
			recipeResult = new ResponseEntity<RecipeResult>(
					new RecipeResult(200, Messages.SUCCESSFUL.getMessage(), recipes), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Here in catch " + e.getMessage());
			recipeResult = new ResponseEntity<RecipeResult>(
					new RecipeResult(1, Messages.UNSUCCESSFUL.getMessage(), null), HttpStatus.OK);
		}
		return recipeResult;
	}

	/**
	 * Returns the specific recipe for the given ID 
	 * 
	 * @param id
	 * @return ResponseEntity<RecipeActionResult>
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<RecipeActionResult> getRecipe(@PathVariable("id") int id) {

		Recipe recipe = null;
		ResponseEntity<RecipeActionResult> recipeResult = new ResponseEntity<RecipeActionResult>(
				new RecipeActionResult(1, null), HttpStatus.OK);
		try {
			recipe = recipeService.getRecipe(id).orElse(null);
			if (recipe != null) {
				recipeResult = new ResponseEntity<RecipeActionResult>(
						new RecipeActionResult(HttpStatus.OK.value(), Messages.SUCCESSFUL.getMessage(), recipe),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("In catch" + e.getMessage());
			recipeResult = new ResponseEntity<RecipeActionResult>(
					new RecipeActionResult(1, Messages.UNSUCCESSFUL.getMessage(), recipe), HttpStatus.OK);
		}
		return recipeResult;
	}

	/**
	 * Creates a recipe and returns the created recipe 
	 * 
	 * @param recipe
	 * @return ResponseEntity<RecipeActionResult>
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<RecipeActionResult> createRecipe(@RequestBody Recipe recipe) {

		Recipe createdRecipe = new Recipe();
		ResponseEntity<RecipeActionResult> recipeResult = new ResponseEntity<RecipeActionResult>(
				new RecipeActionResult(1, null), HttpStatus.OK);
		try {
			createdRecipe = recipeService.createRecipe(recipe);
			recipeResult = new ResponseEntity<RecipeActionResult>(
					new RecipeActionResult(HttpStatus.OK.value(), Messages.SUCCESSFUL.getMessage(), createdRecipe),
					HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("In Catch" + e.getMessage());
			recipeResult = new ResponseEntity<RecipeActionResult>(
					new RecipeActionResult(1, Messages.UNSUCCESSFUL.getMessage(), createdRecipe), HttpStatus.OK);
		}
		return recipeResult;
	}

	/**
	 * Updates the specific recipe for the given ID and returns the updated recipe 
	 * 
	 * @param recipe
	 * @return ResponseEntity<RecipeActionResult>
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<RecipeActionResult> updateRecipe(@RequestBody Recipe recipe) {

		Recipe updateRecipe = null;
		ResponseEntity<RecipeActionResult> recipeResult = new ResponseEntity<RecipeActionResult>(
				new RecipeActionResult(1, null), HttpStatus.OK);
		try {
			updateRecipe = recipeService.updateRecipe(recipe);
			recipeResult = new ResponseEntity<RecipeActionResult>(
					new RecipeActionResult(HttpStatus.OK.value(), Messages.SUCCESSFUL.getMessage(), updateRecipe),
					HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("In catch" + e.getMessage());
			recipeResult = new ResponseEntity<RecipeActionResult>(
					new RecipeActionResult(1, Messages.UNSUCCESSFUL.getMessage(), updateRecipe), HttpStatus.OK);
		}
		return recipeResult;
	}

	/**
	 * Deletes the specific recipe for the given ID 
	 * 
	 * @param id
	 * @return ResponseEntity<RecipeActionResult>
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<RecipeActionResult> deleteRecipe(@PathVariable("id") String id) {

		String deleteMessage = null;
		ResponseEntity<RecipeActionResult> recipeResult = new ResponseEntity<RecipeActionResult>(
				new RecipeActionResult(1, null), HttpStatus.OK);
		try {
			deleteMessage = recipeService.deleteRecipe(Integer.valueOf(id));
			if (null != deleteMessage) {

				recipeResult = new ResponseEntity<RecipeActionResult>(
						new RecipeActionResult(HttpStatus.OK.value(), Messages.SUCCESSFUL.getMessage()), HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("In catch " + e.getMessage());
			recipeResult = new ResponseEntity<RecipeActionResult>(
					new RecipeActionResult(HttpStatus.OK.value(), Messages.UNSUCCESSFUL.getMessage()), HttpStatus.OK);
		}
		return recipeResult;
	}

	/**
	 * Returns the list of recipes which match the criteira 
	 * 
	 * @param recipe
	 * @return ResponseEntity<RecipeSearchResult>
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<RecipeSearchResult> searchRecipe(@RequestBody RecipeSearch recipe) {

		LOGGER.debug("Here in search");
		ResponseEntity<RecipeSearchResult> searchResult = new ResponseEntity<RecipeSearchResult>(
				new RecipeSearchResult(1, null, null), HttpStatus.OK);
		try {
			List<Recipe> recipes = recipeService.searchRecipe(recipe);
			searchResult = new ResponseEntity<RecipeSearchResult>(
					new RecipeSearchResult(HttpStatus.OK.value(), Messages.SUCCESSFUL.getMessage(), recipes),
					HttpStatus.OK);
		} catch (Exception ex) {
			searchResult = new ResponseEntity<RecipeSearchResult>(
					new RecipeSearchResult(1, Messages.UNSUCCESSFUL.getMessage(), null), HttpStatus.OK);
		}
		return searchResult;
	}
}
