package com.jfuerste.recipewebapp.repositories;

import com.jfuerste.recipewebapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
