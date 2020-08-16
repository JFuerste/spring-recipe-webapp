package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
