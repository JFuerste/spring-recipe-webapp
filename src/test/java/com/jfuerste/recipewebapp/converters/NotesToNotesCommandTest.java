package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.NotesCommand;
import com.jfuerste.recipewebapp.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    public static final Long ID = 1L;
    public static final String NOTES = "notes";

    NotesToNotesCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void testNullParam(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){
        assertNotNull(converter.convert(new Notes()));
    }


    @Test
    void convert() {
        Notes notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(NOTES);
        NotesCommand command = converter.convert(notes);

        assertNotNull(command);
        assertEquals(ID, command.getId());
        assertEquals(NOTES, command.getRecipeNotes());



    }
}