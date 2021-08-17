package com.example.bakingApp.objects;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private int Quantity;
    private String Measure;
    private String Ingredient;

    public Ingredient(int quantity, String measure, String ingredient) {
        Quantity = quantity;
        Measure = measure;
        Ingredient = ingredient;
    }

    public int getQuantity() {return Quantity;}
    public String getMeasure() {return Measure;}
    public String getIngredient() {return Ingredient;}
}
