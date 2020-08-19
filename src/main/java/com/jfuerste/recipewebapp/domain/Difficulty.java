package com.jfuerste.recipewebapp.domain;

public enum Difficulty {
    EINFACH, FORTGESCHRITTEN, SCHWER;


    @Override
    public String toString() {
        String output = super.toString().toLowerCase();
        return output.substring(0, 1).toUpperCase() + output.substring(1);
    }
}
