package com.jfuerste.recipewebapp.controllers;

import com.jfuerste.recipewebapp.commands.IngredientCommand;
import com.jfuerste.recipewebapp.commands.UnitOfMeasureCommand;
import com.jfuerste.recipewebapp.domain.Recipe;
import com.jfuerste.recipewebapp.services.IngredientService;
import com.jfuerste.recipewebapp.services.RecipeService;
import com.jfuerste.recipewebapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@Slf4j
public class IngredientController {


    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;


    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }



    @RequestMapping("recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model){
        log.debug("Getting List of ingredients for recipe " + id);
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/ingredient/list";
    }

    @RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable Long recipeId,
                                 @PathVariable Long id, Model model){
        model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdAndId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipe/ingredient/show";
    }

    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdAndId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uomSet", unitOfMeasureService.getAllUoM());
        return "recipe/ingredient/ingredientform";
    }

    @RequestMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){
        recipeService.findCommandById(Long.valueOf(recipeId));

        Recipe recipe = recipeService.findById(Long.valueOf(recipeId));

        if (recipe == null){
            recipe = new Recipe();
        }
        Optional<Long> maxID = recipe.getIngredients().stream()
                .map(ingredient -> ingredient.getId())
                .reduce(Long::max);

        Long newId;
        if (maxID.isPresent()){
            newId = maxID.get() + 1;
        } else {
            newId = 1L;
        }

        IngredientCommand command = new IngredientCommand();
        command.setRecipeId(Long.valueOf(recipeId));
        command.setId(newId);
        command.setUom(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", command);
        model.addAttribute("uomSet", unitOfMeasureService.getAllUoM());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        log.debug("saved recipe id: " + command.getRecipeId());
        log.debug("saved ingredient id: " + command.getId());
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredients/";
    }

    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId){
        ingredientService.deleteByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        log.debug("Removed Ingredient Id: " + ingredientId + " from recipe Id: " + recipeId);

        return "redirect:/recipe/" + recipeId + "/ingredients/";
    };
}
