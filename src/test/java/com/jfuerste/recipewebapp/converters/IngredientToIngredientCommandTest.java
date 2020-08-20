package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.IngredientCommand;
import com.jfuerste.recipewebapp.domain.Ingredient;
import com.jfuerste.recipewebapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

    IngredientToIngredientCommand converter;

    public static final BigDecimal AMOUNT = new BigDecimal(1);
    public static final String DESCRIPTION = "description";
    public static final Long ID = 1L;
    public static final Long UOM_ID = 2L;

    @BeforeEach
    void setUp() {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }


    @Test
    public void testNullParam(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){
        assertNotNull(converter.convert(new Ingredient()));
    }


    @Test
    void convert() {

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);

        Ingredient ingredient = Ingredient.builder()
                .amount(AMOUNT)
                .id(ID)
                .description(DESCRIPTION)
                .uom(unitOfMeasure)
                .build();

        IngredientCommand command = converter.convert(ingredient);

        assertNotNull(command);
        assertNotNull(command.getUom());
        assertEquals(UOM_ID, command.getUom().getId());
        assertEquals(ID, command.getId());
        assertEquals(AMOUNT, command.getAmount());
        assertEquals(DESCRIPTION, command.getDescription());
    }

    @Test
    void convertWithNullUOM(){
        Ingredient ingredient = Ingredient.builder()
                .amount(AMOUNT)
                .id(ID)
                .description(DESCRIPTION)
                .build();

        IngredientCommand command = converter.convert(ingredient);

        assertNotNull(command);
        assertNull(command.getUom());
        assertEquals(ID, command.getId());
        assertEquals(AMOUNT, command.getAmount());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}