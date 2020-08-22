package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.commands.IngredientCommand;
import com.jfuerste.recipewebapp.converters.IngredientCommandToIngredient;
import com.jfuerste.recipewebapp.converters.IngredientToIngredientCommand;
import com.jfuerste.recipewebapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.jfuerste.recipewebapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.jfuerste.recipewebapp.domain.Ingredient;
import com.jfuerste.recipewebapp.domain.Recipe;
import com.jfuerste.recipewebapp.repositories.RecipeRepository;
import com.jfuerste.recipewebapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()), recipeRepository, unitOfMeasureRepository);
    }

    @Test
    void findCommandByRecipeIdAndId() {
        Recipe recipe = Recipe.builder().id(1L).build();
        Ingredient ingredient1 = Ingredient.builder().id(1L).build();
        Ingredient ingredient2 = Ingredient.builder().id(2L).build();
        Ingredient ingredient3 = Ingredient.builder().id(3L).build();

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeIdAndId(1L, 3L);

        assertEquals(3L, ingredientCommand.getId());
        assertEquals(1L, ingredientCommand.getRecipeId());
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    public void saveRecipe(){
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOpt = Optional.of(new Recipe());
        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOpt);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        assertEquals(3L, savedCommand.getId());
        verify(recipeRepository).save(any());
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    void deleteByRecipeIdAndIngredientId() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        Recipe recipe = new Recipe();
        recipe.setId(2L);
        ingredient.setRecipe(recipe);
        recipe.addIngredient(ingredient);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        ingredientService.deleteByRecipeIdAndIngredientId(2L, 1L);

        assertEquals(0, recipe.getIngredients().size());
        assertNull(ingredient.getRecipe());
        verify(recipeRepository).findById(anyLong());
    }
}