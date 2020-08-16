package com.jfuerste.recipewebapp.bootstrap;

import com.jfuerste.recipewebapp.domain.*;
import com.jfuerste.recipewebapp.repositories.CategoryRepository;
import com.jfuerste.recipewebapp.repositories.RecipeRepository;
import com.jfuerste.recipewebapp.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public RecipeLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }


    public List<Recipe> getRecipes() {

        Optional<UnitOfMeasure> gOpptional = unitOfMeasureRepository.findByDescription("g");
        Optional<UnitOfMeasure> teaspoonOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> tablespoonOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        if (gOpptional.isEmpty()|| teaspoonOptional.isEmpty() || tablespoonOptional.isEmpty()){
            throw new RuntimeException("One of the Units not found");
        }

        UnitOfMeasure g = gOpptional.get();
        UnitOfMeasure teaspoon = teaspoonOptional.get();
        UnitOfMeasure tablespoon = tablespoonOptional.get();

        Optional<Category> turkishOptional = categoryRepository.findByDescription("Turkish");
        if (turkishOptional.isEmpty()) {
            throw new RuntimeException("Category not found");
        }

        Category turkish = turkishOptional.get();

        Recipe pilav = new Recipe();
        pilav.addCategory(turkish);
        pilav.setPrepTime(30);
        pilav.setCookTime(30);
        pilav.setDifficulty(Difficulty.EASY);
        pilav.setDirections("Heat your butter in a deep saucepan over a low heat. Now add your şehriye (orzo) to the pan and stir around for a few minutes until you see it start to change colour. \n" +
                "As soon as you notice the orzo start to go brown, add your cup of rice and continue to stir. Remember to keep the pan over a low heat and keep stirring, otherwise your rice and orzo will burn. \n" +
                "After 3-4 minutes, add the water or stock. \n" +
                "There will be a big sizzle and the liquid will bubble up. Turn up the heat and bring the rice to the boil, fully. \n" +
                "Once the rice is boiling, give it a couple of stirs around, put a lid on the pan, leaving a small gap, and reduce the heat to medium-low. \n" +
                "Leave your rice to simmer for 8-10 minutes until the water or stock has absorbed. \n" +
                "Now remove from the heat, put the lid firmly on the pan and leave your Turkish rice to stand for 5 minutes. \n" +
                "After 5 minutes, remove the lid and fork through your rice.");
        Notes pilavNotes = new Notes();
        pilavNotes.setRecipeNotes("Heat your butter in a deep saucepan over a low heat. Now add your şehriye (orzo) to the pan and stir around for a few minutes until you see it start to change colour. " +
                "As soon as you notice the orzo start to go brown, add your cup of rice and continue to stir. Remember to keep the pan over a low heat and keep stirring, otherwise your rice and orzo will burn. " +
                "After 3-4 minutes, add the water or stock. " +
                "There will be a big sizzle and the liquid will bubble up. Turn up the heat and bring the rice to the boil, fully. " +
                "Once the rice is boiling, give it a couple of stirs around, put a lid on the pan, leaving a small gap, and reduce the heat to medium-low. " +
                "Leave your rice to simmer for 8-10 minutes until the water or stock has absorbed. " +
                "Now remove from the heat, put the lid firmly on the pan and leave your Turkish rice to stand for 5 minutes. " +
                "After 5 minutes, remove the lid and fork through your rice.");
        pilav.setNotes(pilavNotes);

        pilav.addIngredient(new Ingredient("Rice", new BigDecimal(250), g))
                .addIngredient(new Ingredient("Butter", new BigDecimal(25), g))
                .addIngredient(new Ingredient("Noodles", new BigDecimal(2), tablespoon))
                .addIngredient(new Ingredient("Salt", new BigDecimal(1), teaspoon));

        pilav.setDescription("Turkish Pilav Rice");
        pilav.setServings(4);
        pilav.setSource("Turkish Blog");
        pilav.setUrl("http://www.turkeysforlife.com/2010/12/turkish-food-turkish-rice-recipe.html");

        //recipeRepository.save(pilav);
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(pilav);
        return recipes;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }
}
