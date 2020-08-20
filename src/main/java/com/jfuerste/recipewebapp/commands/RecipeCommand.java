package com.jfuerste.recipewebapp.commands;

import com.jfuerste.recipewebapp.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredients;
    private Set<CategoryCommand> categories;
    private NoteCommand notes;
    private Difficulty difficulty;
}
