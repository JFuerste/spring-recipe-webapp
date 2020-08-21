package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.commands.RecipeCommand;
import com.jfuerste.recipewebapp.converters.RecipeCommandToRecipe;
import com.jfuerste.recipewebapp.converters.RecipeToRecipeCommand;
import com.jfuerste.recipewebapp.domain.Recipe;
import com.jfuerste.recipewebapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        log.debug("Got Recipes");
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isEmpty()){
            throw new RuntimeException("Recipe does not exist!");
        }
        return optionalRecipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId: " + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }
}
