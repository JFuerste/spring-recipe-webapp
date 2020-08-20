package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.RecipeCommand;
import com.jfuerste.recipewebapp.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

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

    RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeCommand(new NotesToNotesCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand());
    }


    @Test
    public void testNullParam(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convert() {
        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        Category cat1 = new Category();
        cat1.setId(CAT_ID1);
        Category cat2 = new Category();
        cat2.setId(CAT_ID2);
        Ingredient ing1 = new Ingredient();
        ing1.setId(ING_ID1);
        Ingredient ing2 = new Ingredient();
        ing2.setId(ING_ID2);
        Set<Category> catSet = new HashSet<>();
        catSet.add(cat1);
        catSet.add(cat2);
        Set<Ingredient> ingSet = new HashSet<>();
        ingSet.add(ing1);
        ingSet.add(ing2);

        Recipe recipe = Recipe.builder()
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

        RecipeCommand command = converter.convert(recipe);
        assertNotNull(command);
        assertEquals(ID, command.getId());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(NOTES_ID, command.getNotes().getId());
        assertEquals(2, command.getCategories().size());
        assertEquals(2, command.getIngredients().size());



    }
}