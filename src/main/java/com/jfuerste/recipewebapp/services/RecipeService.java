package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.commands.RecipeCommand;
import com.jfuerste.recipewebapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
