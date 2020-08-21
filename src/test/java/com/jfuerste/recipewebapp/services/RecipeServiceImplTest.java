package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.converters.*;
import com.jfuerste.recipewebapp.domain.Recipe;
import com.jfuerste.recipewebapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository,
                new RecipeToRecipeCommand(new NotesToNotesCommand(),
                        new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                        new CategoryToCategoryCommand()),
                new RecipeCommandToRecipe(new NotesCommandToNotes(),
                        new CategoryCommandToCategory(),
                        new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure())));
    }

    @Test
    public void getRecipes(){
        Recipe recipe = new Recipe();
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeSet);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById() {
        Recipe recipe = Recipe.builder().id(1l).build();
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        Recipe recipeReturned = recipeService.findById(1l);

        assertNotNull(recipeReturned);
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }
}