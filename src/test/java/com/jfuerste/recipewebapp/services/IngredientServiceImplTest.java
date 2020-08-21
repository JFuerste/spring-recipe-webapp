package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.commands.IngredientCommand;
import com.jfuerste.recipewebapp.converters.IngredientToIngredientCommand;
import com.jfuerste.recipewebapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.jfuerste.recipewebapp.domain.Ingredient;
import com.jfuerste.recipewebapp.domain.Recipe;
import com.jfuerste.recipewebapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()), recipeRepository);
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
}