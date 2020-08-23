package com.jfuerste.recipewebapp.bootstrap;

import com.jfuerste.recipewebapp.domain.*;
import com.jfuerste.recipewebapp.repositories.CategoryRepository;
import com.jfuerste.recipewebapp.repositories.RecipeRepository;
import com.jfuerste.recipewebapp.repositories.UnitOfMeasureRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class RecipeLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public RecipeLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }


    public List<Recipe> getRecipes() throws IOException {

        Optional<UnitOfMeasure> gOpptional = unitOfMeasureRepository.findByDescription("g");
        Optional<UnitOfMeasure> teaspoonOptional = unitOfMeasureRepository.findByDescription("Teelöffel");
        Optional<UnitOfMeasure> tablespoonOptional = unitOfMeasureRepository.findByDescription("Esslöffel");

        if (gOpptional.isEmpty()|| teaspoonOptional.isEmpty() || tablespoonOptional.isEmpty()){
            throw new RuntimeException("One of the Units not found");
        }

        UnitOfMeasure g = gOpptional.get();
        UnitOfMeasure teaspoon = teaspoonOptional.get();
        UnitOfMeasure tablespoon = tablespoonOptional.get();

        Optional<Category> turkishOptional = categoryRepository.findByDescription("Türkisch");
        if (turkishOptional.isEmpty()) {
            throw new RuntimeException("Category not found");
        }
        Optional<Category> germanOptional = categoryRepository.findByDescription("Deutsch");

        Category turkish = turkishOptional.get();
        Category german = germanOptional.get();

        Recipe pilav = new Recipe();
        pilav.addCategory(turkish);
        pilav.addCategory(german);
        pilav.setPrepTime(30);
        pilav.setCookTime(30);
        pilav.setDifficulty(Difficulty.EINFACH);
        pilav.setDirections("In einem großen Topf Butter erhitzen und Nudeln gut braun rösten. Den Reis dazu geben und glasig dünsten." +
                " Mit 500-550 ml Wasser aufgießen und zum Kochen bringen. Salzen und bei geringer Hitze etwa 10-15 Minuten köcheln lassen bis die Flüssigkeit aufgesogen ist.\n" +
                "Zwischen Topf und Deckel drei Lagen Papier-Küchentücher klemmen und Topfdeckel gut eindrücken." +
                " Herd ausschalten und Reis auf der warmen Herdplatte stehen lassen und mindestens 10 Minuten bis zu zwei Stunden ruhen lassen.\n" +
                "Reis vor dem Servieren auflockern und warm servieren");
        Notes pilavNotes = new Notes();
        pilavNotes.setRecipeNotes("Für Nudeln Şehriye oder auch Orzo Nudeln verwenden.");
        pilav.setNotes(pilavNotes);

        pilav.addIngredient(new Ingredient("Reis", new BigDecimal(250), g))
                .addIngredient(new Ingredient("Butter", new BigDecimal(25), g))
                .addIngredient(new Ingredient("Nudeln", new BigDecimal(2), tablespoon))
                .addIngredient(new Ingredient("Salz", new BigDecimal(1), teaspoon));

        pilav.setDescription("Türkischer Reis");
        pilav.setServings(4);
        pilav.setSource("Blog");
        pilav.setUrl("de.semilicious.com/2016/02/08/geschichte-tuerkischerreis-1/");

        ClassPathResource imageResource = new ClassPathResource("static/images/pilav.jpg");
        byte[] bytes = imageResource.getInputStream().readAllBytes();
        int i = 0;
        Byte[] wrappedBytes = new Byte[bytes.length];

        for (byte b : bytes){
            wrappedBytes[i] = b;
            i++;
        }
        pilav.setImage(wrappedBytes);

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(pilav);
        return recipes;
    }

    @SneakyThrows
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
        log.debug("Added Recipes to Repository");
    }
}
