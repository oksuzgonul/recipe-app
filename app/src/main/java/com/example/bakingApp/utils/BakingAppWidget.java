package com.example.bakingApp.utils;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.example.bakingApp.R;
import com.example.bakingApp.objects.Ingredient;
import com.example.bakingApp.objects.Recipe;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static Recipe recipeOnWidget;

    static void setWidgetRecipe(Recipe recipe) {recipeOnWidget = recipe;}

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Recipe recipe, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        StringBuilder ingredients = new StringBuilder();
        if (recipe != null) {
            List<Ingredient> ingredientList = recipe.getIngredientsList();
            for (int i =0; i < ingredientList.size(); i++) {
                ingredients.append(ingredientList.get(i).getIngredient()).append("\n");
            }
        } else {
            ingredients.append(context.getString(R.string.empty_widget_text));
        }
       //
        views.setTextViewText(R.id.widget_recipe, ingredients.toString());
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                 Recipe recipe, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetUpdateService.startActionWidgetUpdate(context, recipeOnWidget);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

