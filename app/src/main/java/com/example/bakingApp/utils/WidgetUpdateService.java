package com.example.bakingApp.utils;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.bakingApp.objects.Recipe;

public class WidgetUpdateService extends IntentService {

    public static final String ACTION_UPDATE_WIDGET = "com.example.android.bakingApp" +
            ".action.update_widget";
    public static final String WIDGET_RECIPE_ID = "widget_recipe_id";

    public WidgetUpdateService() {super("utils.WidgetUpdateService");}

    public static void startActionWidgetUpdate(Context context, Recipe recipe) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.putExtra(WIDGET_RECIPE_ID, recipe);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    public static void stopActionWidgetUpdate(Context context, Recipe recipe) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.putExtra(WIDGET_RECIPE_ID, recipe);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.stopService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            final String action = intent.getAction();
            final Recipe recipeInWidget = (Recipe) intent.getSerializableExtra(WIDGET_RECIPE_ID);
            if (ACTION_UPDATE_WIDGET.equals(action)) {
                handleActionWidgetUpdate(recipeInWidget);
            }
        }
    }

    private void handleActionWidgetUpdate(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
        BakingAppWidget.setWidgetRecipe(recipe);
        BakingAppWidget.updateAppWidgets(this, appWidgetManager, recipe, appWidgetIds);
    }
}
