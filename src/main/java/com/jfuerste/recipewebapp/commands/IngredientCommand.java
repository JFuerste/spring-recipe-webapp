package com.jfuerste.recipewebapp.commands;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
    private RecipeCommand recipe;

    @Override
    public String toString() {
        return amount.stripTrailingZeros().toPlainString() + " " + uom + " " + description;
    }
}
