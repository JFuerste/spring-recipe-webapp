package com.jfuerste.recipewebapp.repositories;

import com.jfuerste.recipewebapp.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CategoryRepositoryIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void findCategoryGerman(){
        Optional<Category> german = categoryRepository.findByDescription("Deutsch");
        assertEquals("Deutsch", german.get().getDescription());
    }

}