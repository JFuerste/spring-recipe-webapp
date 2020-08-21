package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findCommandByRecipeIdAndId(Long recipeId, Long ingredientId);

}
