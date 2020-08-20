package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.NotesCommand;
import com.jfuerste.recipewebapp.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    public static final Long ID = 1L;
    public static final String NOTES = "notes";

    NotesCommandToNotes converter;


    @BeforeEach
    void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void testNullParam(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpty(){
        assertNotNull(converter.convert(new NotesCommand()));
    }


    @Test
    void convert() {
        NotesCommand command = new NotesCommand();
        command.setId(ID);
        command.setRecipeNotes(NOTES);

        Notes notes = converter.convert(command);
        assertNotNull(notes);
        assertEquals(ID, notes.getId());
        assertEquals(NOTES, notes.getRecipeNotes());
    }
}