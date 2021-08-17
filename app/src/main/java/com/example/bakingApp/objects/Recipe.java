package com.example.bakingApp.objects;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    private int Id;
    private String Name;
    private List<Ingredient> IngredientsList;
    private List<Step> StepsList;
    private int Servings;
    private String Image;

    public Recipe(int id, String name, List<Ingredient> ingredientsList,
                  List<Step> stepsList, int servings, String image) {
        Id = id;
        Name = name;
        IngredientsList = ingredientsList;
        StepsList = stepsList;
        Servings = servings;
        Image = image;
    }

    public int getId() {return Id;}
    public String getName() {return Name;}
    public List<Ingredient> getIngredientsList() {return IngredientsList;}
    public List<Step> getStepsList() {return StepsList;}
    public int getServings() {return Servings;}
    public String getImage() {return Image;}
}
