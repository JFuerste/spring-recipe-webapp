package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.NotesCommand;
import com.jfuerste.recipewebapp.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {
    @Override
    @Nullable
    @Synchronized
    public NotesCommand convert(Notes source) {
        if (source == null){
            return null;
        }
        final NotesCommand command = new NotesCommand();
        command.setRecipeNotes(source.getRecipeNotes());
        command.setId(source.getId());
        return command;
    }
}
