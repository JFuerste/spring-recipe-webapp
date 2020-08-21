package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.commands.IngredientCommand;
import com.jfuerste.recipewebapp.converters.IngredientToIngredientCommand;
import com.jfuerste.recipewebapp.domain.Recipe;
import com.jfuerste.recipewebapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findCommandByRecipeIdAndId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        if (recipeOpt.isEmpty()){
            log.error("Recipe id not found. Id: " +recipeId);
        }
        Recipe recipe = recipeOpt.get();
        Optional<IngredientCommand> ingredientCommand = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();





        if (ingredientCommand.isEmpty()){
            log.error("Ingredient id not found. Id: " + ingredientId );
        }

        return ingredientCommand.get();

    }
}
