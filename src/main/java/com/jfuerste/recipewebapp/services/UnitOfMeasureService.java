package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> getAllUoM();
}
