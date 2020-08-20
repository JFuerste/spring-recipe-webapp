package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.UnitOfMeasureCommand;
import com.jfuerste.recipewebapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {
    @Nullable
    @Synchronized
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null){
            return null;
        }
        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(source.getDescription());
        uom.setId(source.getId());
        return uom;
    }
}
