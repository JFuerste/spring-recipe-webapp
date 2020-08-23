package com.jfuerste.recipewebapp.commands;

import com.jfuerste.recipewebapp.domain.Difficulty;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RecipeCommand {
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;
    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;

    @Min(1)
    @Max(100)
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
