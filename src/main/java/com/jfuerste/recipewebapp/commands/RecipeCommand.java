package com.jfuerste.recipewebapp.commands;

import com.jfuerste.recipewebapp.domain.Difficulty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private String source;
    private String url;
    private String directions;
    private Integer servings;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();
    private NotesCommand notes;
    private Difficulty difficulty;
    private Byte[] image;

    public void addCategory(CategoryCommand category){
        categories.add(category);
    }

    public void addIngredient(IngredientCommand ingredient){
        ingredient.setRecipe(this);
        ingredients.add(ingredient);
    }
}
