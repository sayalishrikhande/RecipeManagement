package com.recipes.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipes.springboot.models.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

}
