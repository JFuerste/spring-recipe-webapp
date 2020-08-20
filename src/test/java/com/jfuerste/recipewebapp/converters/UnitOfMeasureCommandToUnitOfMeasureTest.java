package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.UnitOfMeasureCommand;
import com.jfuerste.recipewebapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "description";

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp(){
        converter = new UnitOfMeasureCommandToUnitOfMeasure();

    }

    @Test
    public void testNullParam(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }


    @Test
    void convert() {
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setDescription(DESCRIPTION);
        command.setId(ID);

        UnitOfMeasure unitOfMeasure = converter.convert(command);
        assertNotNull(unitOfMeasure);
        assertEquals(ID, unitOfMeasure.getId());
        assertEquals(DESCRIPTION, unitOfMeasure.getDescription());

    }
}