package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.IngredientCommand;
import com.jfuerste.recipewebapp.commands.UnitOfMeasureCommand;
import com.jfuerste.recipewebapp.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    public static final BigDecimal AMOUNT = new BigDecimal(1);
    public static final String DESCRIPTION = "description";
    public static final Long ID = 1L;
    public static final Long UOM_ID = 2L;

    IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }


    @Test
    public void testNullParam(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void convert() {

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        IngredientCommand command = IngredientCommand.builder()
                .id(ID)
                .amount(AMOUNT)
                .description(DESCRIPTION)
                .uom(unitOfMeasureCommand)
                .build();

        Ingredient ingredient = converter.convert(command);

        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID, ingredient.getId());
        assertEquals(UOM_ID, ingredient.getUom().getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
    }

    @Test
    public void convertWithNullUOM(){
        IngredientCommand command = IngredientCommand.builder()
                .id(ID)
                .amount(AMOUNT)
                .description(DESCRIPTION)
                .build();

        Ingredient ingredient = converter.convert(command);

        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
    }
}