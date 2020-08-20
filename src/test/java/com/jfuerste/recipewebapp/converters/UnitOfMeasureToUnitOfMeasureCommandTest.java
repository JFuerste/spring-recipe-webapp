package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.UnitOfMeasureCommand;
import com.jfuerste.recipewebapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    private UnitOfMeasureToUnitOfMeasureCommand converter;

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "description";

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullParam(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }


    @Test
    void convert() {
        UnitOfMeasure source = new UnitOfMeasure();
        source.setId(ID);
        source.setDescription(DESCRIPTION);

        UnitOfMeasureCommand command = converter.convert(source);

        assertNotNull(command);
        assertEquals(ID, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}