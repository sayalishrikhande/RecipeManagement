package com.recipes.springboot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import com.recipes.springboot.controllers.RecipeManagementController;
import com.recipes.springboot.filters.RecipeManagementFilter;
import com.recipes.springboot.models.Recipe;
import com.recipes.springboot.models.RecipeActionResult;
import com.recipes.springboot.models.RecipeResult;
import com.recipes.springboot.models.RecipeSearch;
import com.recipes.springboot.models.RecipeSearchResult;
import com.recipes.springboot.services.RecipeService;
import com.recipes.springboot.services.serviceImpl.MyUserDetailsServiceImpl;
import com.recipes.springboot.services.serviceImpl.RecipeServiceImpl;
import com.recipes.springboot.util.DateUtil;

@RunWith(SpringRunner.class)
public class RecipeManagementUnitTest {

	RecipeManagementController recipeMngController;
	Recipe recipe;

	@BeforeEach
	public void setUp() {
		RecipeService recipeService = Mockito.mock(RecipeServiceImpl.class);
		this.recipeMngController = new RecipeManagementController();

		recipe = new Recipe();
		recipe.setName("Coffee");
		recipe.setIngredients("Water, Milk, Coffee, Sugar");
		recipe.setInstructions("Boil the ingredients together");
		recipe.setServings(2);
		recipe.setUserID(1);
		recipe.setVeg(true);
	}

	@Test
	void testGetRecipes() {
		ResponseEntity<RecipeResult> recipeResult = recipeMngController.getRecipes();

		assertNotNull(recipeResult);
		assertNotNull(recipeResult.getStatusCode());
	}

	@Test
	void testGetRecipe() {
		ResponseEntity<RecipeActionResult> recipeResult = recipeMngController.getRecipe(1);

		assertNotNull(recipeResult);
		assertNotNull(recipeResult.getStatusCode());
	}

	@Test
	void testCreateRecipe() {
		ResponseEntity<RecipeActionResult> recipeResult = recipeMngController.createRecipe(recipe);

		assertNotNull(recipeResult);
		assertNotNull(recipeResult.getStatusCode());
	}

	@Test
	void testUpdateRecipe() {
		ResponseEntity<RecipeActionResult> recipeResult = recipeMngController.updateRecipe(recipe);

		assertNotNull(recipeResult);
		assertNotNull(recipeResult.getStatusCode());
	}

	@Test
	void testDeleteRecipe() {
		ResponseEntity<RecipeActionResult> recipeResult = recipeMngController.updateRecipe(recipe);

		assertNotNull(recipeResult);
		assertNotNull(recipeResult.getStatusCode());
	}

	@Test
	void testSearchRecipe() {
		ResponseEntity<RecipeSearchResult> recipeResult = recipeMngController
				.searchRecipe(new RecipeSearch(1, 1, 1, "Tea", "Boil", "Milk", "Coffee", true));

		assertNotNull(recipeResult.getBody().getStatus());
		assertNotNull(recipeResult.getBody().getMessage());
	}

	@Test
	void testDateUtil() {
		assertNotNull(DateUtil.getCurrentDateTime());
	}
	
	@Test
	void testFiltes() throws Exception{
		RecipeManagementFilter testFilter = new RecipeManagementFilter();
		MockFilterChain mockChain = new MockFilterChain();
	    MockHttpServletRequest req = new MockHttpServletRequest();
	    MockHttpServletResponse rsp = new MockHttpServletResponse();
		testFilter.doFilter(req, rsp, mockChain);
		assertEquals("", rsp.getContentAsString());
	}
	
	@Test
	void testUserDetailsService() throws Exception{
		MyUserDetailsServiceImpl myUser = new MyUserDetailsServiceImpl();
		assertNotNull(myUser.loadUserByUsername("myUser"));
	}

}
