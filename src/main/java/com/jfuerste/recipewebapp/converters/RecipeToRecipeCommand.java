package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.RecipeCommand;
import com.jfuerste.recipewebapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand notesConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final CategoryToCategoryCommand categoryConverter;

    public RecipeToRecipeCommand(NotesToNotesCommand notesConverter, IngredientToIngredientCommand ingredientConverter, CategoryToCategoryCommand categoryConverter) {
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
        this.categoryConverter = categoryConverter;
    }


    @Override
    @Synchronized
    @Nullable
    public RecipeCommand convert(Recipe source) {
        if (source == null){
            return null;
        }

        final RecipeCommand command = RecipeCommand.builder()
                .id(source.getId())
                .cookTime(source.getCookTime())
                .categories(new HashSet<>())
                .description(source.getDescription())
                .difficulty(source.getDifficulty())
                .directions(source.getDirections())
                .ingredients(new HashSet<>())
                .notes(notesConverter.convert(source.getNotes()))
                .prepTime(source.getPrepTime())
                .servings(source.getServings())
                .source(source.getSource())
                .url(source.getUrl())
                .build();

        source.getIngredients().forEach(ingredient ->
                command.addIngredient(ingredientConverter.convert(ingredient)));
        source.getCategories().forEach(category ->
                command.addCategory(categoryConverter.convert(category)));

        return command;
    }
}
