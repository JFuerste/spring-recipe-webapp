package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.CategoryCommand;
import com.jfuerste.recipewebapp.commands.IngredientCommand;
import com.jfuerste.recipewebapp.commands.NotesCommand;
import com.jfuerste.recipewebapp.commands.RecipeCommand;
import com.jfuerste.recipewebapp.domain.Difficulty;
import com.jfuerste.recipewebapp.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    public static final String DESCRIPTION = "description";
    public static final Long ID = 1L;
    public static final Integer PREP_TIME = 30;
    public static final Integer COOK_TIME = 30;
    public static final Integer SERVINGS = 4;
    public static final String SOURCE = "source";
    public static final String URL = "url";
    public static final String DIRECTIONS = "directions";
    public static final Long CAT_ID1 = 2L;
    public static final Long CAT_ID2 = 3L;
    public static final Long ING_ID1 = 4L;
    public static final Long ING_ID2 = 5L;
    public static final Long NOTES_ID = 6L;
    public static final Difficulty DIFFICULTY = Difficulty.EINFACH;

    RecipeCommandToRecipe converter;



    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(new NotesCommandToNotes(), new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));

    }


    @Test
    public void testNullParam(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){
        assertNotNull(converter.convert(new RecipeCommand()));
    }


    @Test
    void convert() {

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);
        CategoryCommand cat1 = new CategoryCommand();
        cat1.setId(CAT_ID1);
        CategoryCommand cat2 = new CategoryCommand();
        cat2.setId(CAT_ID2);
        IngredientCommand ing1 = new IngredientCommand();
        ing1.setId(ING_ID1);
        IngredientCommand ing2 = new IngredientCommand();
        ing2.setId(ING_ID2);
        Set<CategoryCommand> catSet = new HashSet<>();
        catSet.add(cat1);
        catSet.add(cat2);
        Set<IngredientCommand> ingSet = new HashSet<>();
        ingSet.add(ing1);
        ingSet.add(ing2);

        RecipeCommand command = RecipeCommand.builder()
                .id(ID)
                .cookTime(COOK_TIME)
                .prepTime(PREP_TIME)
                .description(DESCRIPTION)
                .difficulty(DIFFICULTY)
                .directions(DIRECTIONS)
                .servings(SERVINGS)
                .source(SOURCE)
                .url(URL)
                .ingredients(ingSet)
                .categories(catSet)
                .notes(notes)
                .build();

        Recipe recipe = converter.convert(command);
        assertNotNull(recipe);
        assertEquals(ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());



    }
}