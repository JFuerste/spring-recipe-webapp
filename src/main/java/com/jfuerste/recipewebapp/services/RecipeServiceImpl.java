package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.domain.Recipe;
import com.jfuerste.recipewebapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
}
