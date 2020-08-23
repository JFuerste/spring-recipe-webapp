package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.RecipeCommand;
import com.jfuerste.recipewebapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes notesConverter;
    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;

    public RecipeCommandToRecipe(NotesCommandToNotes notesConverter, CategoryCommandToCategory categoryConverter, IngredientCommandToIngredient ingredientConverter) {
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Override
    @Synchronized
    @Nullable
    public Recipe convert(RecipeCommand source) {
        if (source == null){
            return null;
        }

        final Recipe recipe = Recipe.builder()
                .id(source.getId())
                .cookTime(source.getCookTime())
                .description(source.getDescription())
                .difficulty(source.getDifficulty())
                .directions(source.getDirections())
                .notes(notesConverter.convert(source.getNotes()))
                .prepTime(source.getPrepTime())
                .servings(source.getServings())
                .source(source.getSource())
                .url(source.getUrl())
                .categories(new HashSet<>())
                .ingredients(new HashSet<>())
                .image(source.getImage())
                .build();

        source.getCategories().forEach(categoryCommand ->
                recipe.addCategory(categoryConverter.convert(categoryCommand)));
        source.getIngredients().forEach(ingredientCommand ->
                recipe.addIngredient(ingredientConverter.convert(ingredientCommand)));

        return recipe;
    }
}
