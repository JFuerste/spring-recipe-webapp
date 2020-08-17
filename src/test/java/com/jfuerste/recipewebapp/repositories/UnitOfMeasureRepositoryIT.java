package com.jfuerste.recipewebapp.repositories;

import com.jfuerste.recipewebapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;


    @Test
    void findByDescriptionTeaspoon() {
        Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", uom.get().getDescription());
    }

    @Test
    void findByDescriptionG() {
        Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByDescription("g");

        assertEquals("g", uom.get().getDescription());
    }
}