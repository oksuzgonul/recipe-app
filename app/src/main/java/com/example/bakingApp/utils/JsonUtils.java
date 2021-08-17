package com.example.bakingApp.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.bakingApp.objects.Ingredient;
import com.example.bakingApp.objects.Recipe;
import com.example.bakingApp.objects.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String LOG_TAG = JsonUtils.class.getName();
    private JsonUtils() {}

    public static List<Recipe> fetchRecipeData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with HTTP request.", e);
        }
        return extractContentFromJson(jsonResponse);
    }

    private static URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed URL.", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error with response code: " +
                        urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Could not retrieve the movie JSON response.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String  line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Recipe> extractContentFromJson(String contentJson) {
        if (TextUtils.isEmpty(contentJson)) {
            return null;
        }

        List<Recipe> recipes = new ArrayList<>();

        try {
            JSONArray recipeArray = new JSONArray(contentJson);
            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject recipeObject = recipeArray.getJSONObject(i);
                JSONArray ingredientArray = recipeObject.getJSONArray("ingredients");
                JSONArray stepArray = recipeObject.getJSONArray("steps");

                List<Ingredient> ingredients = new ArrayList<>();
                for (int k = 0; k < ingredientArray.length(); k++) {
                    JSONObject ingredientObject = ingredientArray.getJSONObject(k);
                    Ingredient ingredient = new Ingredient(
                            ingredientObject.getInt("quantity"),
                            ingredientObject.getString("measure"),
                            ingredientObject.getString("ingredient"));
                    ingredients.add(ingredient);
                }

                List<Step> steps = new ArrayList<>();
                for (int l = 0; l < stepArray.length(); l++) {
                    JSONObject stepObject = stepArray.getJSONObject(l);
                    Step step = new Step(
                            stepObject.getInt("id"),
                            stepObject.getString("shortDescription"),
                            stepObject.getString("description"),
                            stepObject.getString("videoURL"),
                            stepObject.getString("thumbnailURL"));
                    steps.add(step);
                }

                Recipe recipe = new Recipe(
                        recipeObject.getInt("id"),
                        recipeObject.getString("name"),
                        ingredients,
                        steps,
                        recipeObject.getInt("servings"),
                        recipeObject.getString("image"));
                recipes.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
