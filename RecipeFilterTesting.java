package com.example.recipe_suggestion;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.CheckBox;

public class RecipeFilterTesting extends Application {

    public static void noFilter() {

        List<CheckBox> mealTypeCheckBoxesList = new ArrayList<>();
        List<CheckBox> dietaryCheckBoxesList = new ArrayList<>();
        List<CheckBox> proteinCheckBoxesList = new ArrayList<>();
        List<CheckBox> cookingTimeCheckBoxesList = new ArrayList<>();

        CheckBox mealTypeCheckBox = new CheckBox("Any");
        mealTypeCheckBox.setSelected(false);

        CheckBox dietaryCheckBox = new CheckBox("None");
        dietaryCheckBox.setSelected(false);

        CheckBox proteinCheckBox = new CheckBox("Any");
        proteinCheckBox.setSelected(false);

        CheckBox cookingTimeCheckBox = new CheckBox("Any");
        cookingTimeCheckBox.setSelected(false);

        mealTypeCheckBoxesList.add(mealTypeCheckBox);
        dietaryCheckBoxesList.add(dietaryCheckBox);
        proteinCheckBoxesList.add(proteinCheckBox);
        cookingTimeCheckBoxesList.add(cookingTimeCheckBox);

        String sql = RecipeFilterApp.buildSQLQuery(
                mealTypeCheckBoxesList,
                dietaryCheckBoxesList,
                proteinCheckBoxesList,
                cookingTimeCheckBoxesList
        );

        if (sql.equals("SELECT recipe_id FROM Recipes WHERE 1=1;")) {
            System.out.println("Success");
        } else {
            System.out.println("Defeat");
        }
    }

    public static void oneMeal() {
        List<CheckBox> mealTypeCheckBoxesList = new ArrayList<>();
        List<CheckBox> dietaryCheckBoxesList = new ArrayList<>();
        List<CheckBox> proteinCheckBoxesList = new ArrayList<>();
        List<CheckBox> cookingTimeCheckBoxesList = new ArrayList<>();

        CheckBox mealTypeCheckBox = new CheckBox("Lunch");
        mealTypeCheckBox.setSelected(true); 

        CheckBox dietaryCheckBox = new CheckBox("None");
        dietaryCheckBox.setSelected(false);

        CheckBox proteinCheckBox = new CheckBox("Any");
        proteinCheckBox.setSelected(false);

        CheckBox cookingTimeCheckBox = new CheckBox("Any");
        cookingTimeCheckBox.setSelected(false);

        mealTypeCheckBoxesList.add(mealTypeCheckBox);
        dietaryCheckBoxesList.add(dietaryCheckBox);
        proteinCheckBoxesList.add(proteinCheckBox);
        cookingTimeCheckBoxesList.add(cookingTimeCheckBox);

        String sql = RecipeFilterApp.buildSQLQuery(mealTypeCheckBoxesList, dietaryCheckBoxesList, proteinCheckBoxesList, cookingTimeCheckBoxesList);

        if (sql.equals("SELECT recipe_id FROM Recipes WHERE 1=1 AND meal_type IN ('Lunch');")) {
            System.out.println("Success");
        } else {
            System.out.println("Defeat");
        }
    }

    public static void dietary() {

        List<CheckBox> mealTypeCheckBoxesList = new ArrayList<>();
        List<CheckBox> dietaryCheckBoxesList = new ArrayList<>();
        List<CheckBox> proteinCheckBoxesList = new ArrayList<>();
        List<CheckBox> cookingTimeCheckBoxesList = new ArrayList<>();

        CheckBox mealTypeCheckBox = new CheckBox("Any");
        mealTypeCheckBox.setSelected(false); 

        CheckBox dietaryCheckBox = new CheckBox("Vegan");
        dietaryCheckBox.setSelected(true);

        CheckBox proteinCheckBox = new CheckBox("Any");
        proteinCheckBox.setSelected(false);

        CheckBox cookingTimeCheckBox = new CheckBox("Any");
        cookingTimeCheckBox.setSelected(false);

        mealTypeCheckBoxesList.add(mealTypeCheckBox);
        dietaryCheckBoxesList.add(dietaryCheckBox);
        proteinCheckBoxesList.add(proteinCheckBox);
        cookingTimeCheckBoxesList.add(cookingTimeCheckBox);

        String sql = RecipeFilterApp.buildSQLQuery(
                mealTypeCheckBoxesList,
                dietaryCheckBoxesList,
                proteinCheckBoxesList,
                cookingTimeCheckBoxesList
        );

        if (sql.equals("SELECT recipe_id FROM Recipes WHERE 1=1 AND recipe_id IN (" +
                        "SELECT recipe_id FROM Dietary_suitability WHERE dietary_id IN (" +
                        "SELECT dietary_id FROM Dietary_requirement WHERE name IN ('Vegan')));")) {
            System.out.println("Success");
        } else {
            System.out.println("Defeat");
        }
    }

    public static void oneProtein() {

        List<CheckBox> mealTypeCheckBoxesList = new ArrayList<>();
        List<CheckBox> dietaryCheckBoxesList = new ArrayList<>();
        List<CheckBox> proteinCheckBoxesList = new ArrayList<>();
        List<CheckBox> cookingTimeCheckBoxesList = new ArrayList<>();

        CheckBox mealTypeCheckBox = new CheckBox("Any");
        mealTypeCheckBox.setSelected(false); 

        CheckBox dietaryCheckBox = new CheckBox("None");
        dietaryCheckBox.setSelected(false);

        CheckBox proteinCheckBox = new CheckBox("Chicken");
        proteinCheckBox.setSelected(true);

        CheckBox cookingTimeCheckBox = new CheckBox("Any");
        cookingTimeCheckBox.setSelected(false);

        mealTypeCheckBoxesList.add(mealTypeCheckBox);
        dietaryCheckBoxesList.add(dietaryCheckBox);
        proteinCheckBoxesList.add(proteinCheckBox);
        cookingTimeCheckBoxesList.add(cookingTimeCheckBox);

        String sql = RecipeFilterApp.buildSQLQuery(
                mealTypeCheckBoxesList,
                dietaryCheckBoxesList,
                proteinCheckBoxesList,
                cookingTimeCheckBoxesList
        );

        if (sql.equals("SELECT recipe_id FROM Recipes WHERE 1=1 AND protein IN ('Chicken');")) {
            System.out.println("Success");
        } else {
            System.out.println("Defeat");
        }
    }

    public static void Cookingtime() {

        List<CheckBox> mealTypeCheckBoxesList = new ArrayList<>();
        List<CheckBox> dietaryCheckBoxesList = new ArrayList<>();
        List<CheckBox> proteinCheckBoxesList = new ArrayList<>();
        List<CheckBox> cookingTimeCheckBoxesList = new ArrayList<>();

        CheckBox mealTypeCheckBox = new CheckBox("Any");
        mealTypeCheckBox.setSelected(false);

        CheckBox dietaryCheckBox = new CheckBox("None");
        dietaryCheckBox.setSelected(false);

        CheckBox proteinCheckBox = new CheckBox("Any");
        proteinCheckBox.setSelected(false);

        CheckBox cookingTimeCheckBox = new CheckBox("10-20");
        cookingTimeCheckBox.setSelected(true);

        mealTypeCheckBoxesList.add(mealTypeCheckBox);
        dietaryCheckBoxesList.add(dietaryCheckBox);
        proteinCheckBoxesList.add(proteinCheckBox);
        cookingTimeCheckBoxesList.add(cookingTimeCheckBox);

        String sql = RecipeFilterApp.buildSQLQuery(
                mealTypeCheckBoxesList,
                dietaryCheckBoxesList,
                proteinCheckBoxesList,
                cookingTimeCheckBoxesList
        );

        if (sql.equals("SELECT recipe_id FROM Recipes WHERE 1=1 AND (cooking_time BETWEEN 10 AND 20);")) {
            System.out.println("Success");
        } else {
            System.out.println("Defeat");
        }
    }

    @Override
    public void start(Stage primaryStage) {
        noFilter();
        oneMeal();
        dietary();
        oneProtein();
        Cookingtime();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
