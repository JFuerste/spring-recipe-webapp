package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.commands.IngredientCommand;
import com.jfuerste.recipewebapp.converters.IngredientCommandToIngredient;
import com.jfuerste.recipewebapp.converters.IngredientToIngredientCommand;
import com.jfuerste.recipewebapp.domain.Ingredient;
import com.jfuerste.recipewebapp.domain.Recipe;
import com.jfuerste.recipewebapp.exceptions.NotFoundException;
import com.jfuerste.recipewebapp.repositories.RecipeRepository;
import com.jfuerste.recipewebapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findCommandByRecipeIdAndId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        if (recipeOpt.isEmpty()){
            log.error("Recipe id not found. Id: " +recipeId);
            throw new NotFoundException("Recipe ID not found. ID: " + recipeId);

        }
        Recipe recipe = recipeOpt.get();
        Optional<IngredientCommand> ingredientCommand = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (ingredientCommand.isEmpty()){
            log.error("Ingredient id not found. Id: " + ingredientId );
            throw new NotFoundException("Ingredient id not found. Id: " + ingredientId);

        }

        return ingredientCommand.get();

    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(command.getRecipeId());
        if (recipeOpt.isEmpty()){
            log.error("Recipe ID not found. ID: " + command.getId());
            throw new NotFoundException("Recipe ID not found. ID: " + command.getId());
        }
        Recipe recipe = recipeOpt.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(i -> command.getId().equals(i.getId()))
                .findFirst();

        if (ingredientOptional.isPresent()){
            Ingredient ingredient = ingredientOptional.get();
            ingredient.setDescription(command.getDescription());
            ingredient.setAmount(command.getAmount());
            ingredient.setUom(unitOfMeasureRepository.findById(command.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
        } else {
            Ingredient convert = ingredientCommandToIngredient.convert(command);
            convert.setRecipe(recipe);
            recipe.addIngredient(convert);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                .findFirst()
                .get());


    }

    @Override
    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        if (recipeOpt.isEmpty()){
            log.error("Recipe id not found. Id: " + recipeId);
            throw new NotFoundException("Recipe ID not found. ID: " + recipeId);

        }
        Recipe recipe = recipeOpt.get();
        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(Long.valueOf(ingredientId))).findFirst();

        if (ingredientOptional.isPresent()){
            Ingredient ingredient = ingredientOptional.get();
            recipe.getIngredients().remove(ingredient);
            ingredient.setRecipe(null);
            recipeRepository.save(recipe);
        }
    }
}
