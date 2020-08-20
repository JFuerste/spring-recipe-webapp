package com.jfuerste.recipewebapp.converters;

import com.jfuerste.recipewebapp.commands.CategoryCommand;
import com.jfuerste.recipewebapp.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Override
    @Nullable
    public CategoryCommand convert(Category category) {
        if (category == null){
            return null;
        }

        final CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setDescription(category.getDescription());
        categoryCommand.setId(category.getId());

        return categoryCommand;
    }
}
