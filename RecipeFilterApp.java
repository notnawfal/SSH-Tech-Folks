package com.example.recipe_suggestion;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class RecipeFilterApp extends Application {

    private static List<CheckBox> mealTypeCheckBoxes = new ArrayList<>();
    private static List<CheckBox> dietaryCheckBoxes = new ArrayList<>();
    private static List<CheckBox> proteinCheckBoxes = new ArrayList<>();
    private static List<CheckBox> cookingTimeCheckBoxes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Recipe Filter System");

        // Filter categories
        VBox Layout = new VBox(5);
        Layout.setPadding(new Insets(10));

        Layout.getChildren().addAll(
                Filter_UI("Type of Meal", new String[]{"Any", "Breakfast", "Lunch", "Dinner", "Snack", "Dessert", "Smoothie"}, mealTypeCheckBoxes),
                Filter_UI("Dietary Restrictions", new String[]{"Vegetarian", "Vegan", "Dairy-Free", "Gluten-Free", "Nut-Free", "Paleo", "None"}, dietaryCheckBoxes),
                Filter_UI("Type of Protein", new String[]{"Any", "Chicken", "Eggs", "Beef", "Pork", "Lamb", "Fish & Seafood", "Plant-Based Meats", "None"}, proteinCheckBoxes),
                Filter_UI("Cooking Time", new String[]{"Any", "0-10", "10-20", "20-30", "30-45", "45+"}, cookingTimeCheckBoxes)
        );  //sets up our 4 filters

        Button generate = new Button("Generate Recipes");//button used to begin recipe generation

        RecipeSelectionUI recipeselect = new RecipeSelectionUI();

        generate.setOnAction(e -> {
            Stage selectionstage = new Stage();
            recipeselect.start(selectionstage);
        });

        Layout.getChildren().add(generate);

        Scene scene = new Scene(Layout, 1000, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox Filter_UI(String title, String[] options, List<CheckBox> checkBoxList) {

        HBox section = new HBox(10);
        section.setPadding(new Insets(10));
        section.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-padding: 10;");

        Label label = new Label(title);
        section.getChildren().add(label);

        for (String option : options) {
            CheckBox checkBox = new CheckBox(option);
            checkBoxList.add(checkBox);
            section.getChildren().add(checkBox);
        }

        return section;
    }


    public static String buildSQLQuery(List<CheckBox> mealType,List<CheckBox> dietarycheck, List<CheckBox> protein, List<CheckBox> cookingtime) {
        StringBuilder query = new StringBuilder("SELECT recipe_id FROM Recipes WHERE 1=1");

        // Meal type filter
        List<String> selectedMealTypes = getSelectedFilters(mealType);
        if ((!selectedMealTypes.contains("Any")) && !selectedMealTypes.isEmpty()) {
            query.append(" AND meal_type IN (").append(formatForSQL(selectedMealTypes)).append(")");
        }

        // Dietary restrictions filter
        List<String> selectedDietary = getSelectedFilters(dietarycheck);
        if ((!selectedDietary.contains("None")) && !selectedDietary.isEmpty()) {
            query.append(" AND recipe_id IN (")
                    .append("SELECT recipe_id FROM Dietary_suitability WHERE dietary_id IN (")
                    .append("SELECT dietary_id FROM Dietary_requirement WHERE name IN (")
                    .append(formatForSQL(selectedDietary))
                    .append(")))");
        }

        // Protein filter
        List<String> selectedProteins = getSelectedFilters(protein);
        if ((!selectedProteins.contains("Any")) && !selectedProteins.isEmpty()) {
            query.append(" AND protein IN (").append(formatForSQL(selectedProteins)).append(")");
        }

        // Cooking time filter
        List<String> selectedTimes = getSelectedFilters(cookingtime);
        if ((!selectedTimes.contains("Any"))&& !selectedTimes.isEmpty()) {
            query.append(" AND (");
            boolean firstCondition = true;

            for (String timeRange : selectedTimes) {
                // Handle "45+" case
                if (timeRange.equals("45+")) {
                    if (!firstCondition) {
                        query.append(" OR ");
                    }
                    query.append("cooking_time >= 45");
                    firstCondition = false;
                } else {
                    // Handle ranges like "10-20"
                    String[] parts = timeRange.split("-");
                    if (parts.length == 2) {
                        try {
                            int LB = Integer.parseInt(parts[0].trim());
                            int UB = Integer.parseInt(parts[1].trim());

                            if (!firstCondition) {
                                query.append(" OR ");
                            }
                            query.append("cooking_time BETWEEN ").append(LB).append(" AND ").append(UB);
                            firstCondition = false;
                        } catch (NumberFormatException e) {
                            // Log invalid ranges for debugging
                            System.err.println("Invalid time range: " + timeRange);
                        }
                    } else {
                        System.err.println("Invalid format for time range: " + timeRange);
                    }
                }
            }

            query.append(")");
        }
        query.append(";");
        return query.toString();
    }

    private List<String> getSelectedFilters(List<CheckBox> checkBoxes) {
        List<String> selected = new ArrayList<>();
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                selected.add(checkBox.getText());
            }
        }
        return selected;
    }

    private String formatForSQL(List<String> items) {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            formatted.append("'").append(items.get(i)).append("'");
            if (i < items.size() - 1) {
                formatted.append(", ");
            }
        }
        return formatted.toString();
    }

    public static List<Integer> validRecipes() {

        List<Integer> validRecipeIDs = new ArrayList<>();

        String sqlQuery = "WITH IngredientAvailability AS (\n" +
                "        SELECT Recipe_ingredients.recipe_id, Recipe_ingredients.ingredient_id, Recipe_ingredients.quantity AS required_quantity, Fridge_contents.quantity AS fridge_quantity\n" +
                "        FROM Recipe_ingredients\n" +
                "        LEFT JOIN Fridge_contents ON Recipe_ingredients.ingredient_id = Fridge_contents.ingredient_id),\n" +
                "        MissingIngredients AS (\n" +
                "        SELECT recipe_id,\n" +
                "        COUNT(*) AS missing_count\n" +
                "        FROM IngredientAvailability\n" +
                "        WHERE fridge_quantity IS NULL OR fridge_quantity < required_quantity\n" +
                "        GROUP BY recipe_id)\n" +
                "        SELECT ri.recipe_id\n" +
                "        FROM Recipe_ingredients ri \n" +
                "        JOIN MissingIngredients mi ON ri.recipe_id = mi.recipe_id\n" +
                "        WHERE mi.missing_count <= 2\n" +
                "        GROUP BY ri.recipe_id;";

        try {
            Connection conn = DriverManager.getConnection(Credentials.url, Credentials.user, Credentials.password);
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                validRecipeIDs.add(rs.getInt("recipe_id"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return validRecipeIDs;
    }

    public static List<Integer> getRecords() {

        List<Integer> recipeIDs = new ArrayList<>();
        String sql = buildSQLQuery(mealTypeCheckBoxes,dietaryCheckBoxes,proteinCheckBoxes,cookingTimeCheckBoxes);

        try {
            Connection con = DriverManager.getConnection(Credentials.url,Credentials.user,Credentials.password);

            PreparedStatement pstatemnt = con.prepareStatement(sql);

            ResultSet rs = pstatemnt.executeQuery();

            while (rs.next()) {
                recipeIDs.add(rs.getInt("recipe_id"));

            }

            rs.close();
            pstatemnt.close();
            con.close();

        } catch (Exception e)
        { System.out.println(e); }

        return recipeIDs;
    }
    
    public static List<Integer> filteredvalidRecipes() {

        List<Integer> validRecipeIDs = new ArrayList<>();
        validRecipeIDs.addAll(validRecipes());

        List<Integer> allRecipeIDs = new ArrayList<>();   
        allRecipeIDs.addAll(getRecords());

        List<Integer> selectedRecipeIDs = new ArrayList<>();

        for(int all:allRecipeIDs){
            for(int valid:validRecipeIDs){
                if(all==valid){
                    selectedRecipeIDs.add(valid);
                }
            }
        }
        return selectedRecipeIDs;
    }
    
    public List<Record> getNeededRecipes() {
        
        List<Record> neededrecipes = new ArrayList();
        String sql = "SELECT recipe_id, dish_name, meal_type, protein, cooking_time, img_url FROM Recipes WHERE recipe_id = ?";

        try {
            Connection con = DriverManager.getConnection(Credentials.url,Credentials.user,Credentials.password);

            PreparedStatement pstatemnt = con.prepareStatement(sql);

            for (Integer recipeId : filteredvalidRecipes()) {
                pstatemnt.clearParameters();
                pstatemnt.setInt(1, recipeId);

                ResultSet rs = pstatemnt.executeQuery();

                while (rs.next()) {
                    int recipe_id = rs.getInt("recipe_id");
                    String dish_name = rs.getString("dish_name");
                    String meal_type = rs.getString("meal_type");
                    String protein = rs.getString("protein");
                    int cooking_time = rs.getInt("cooking_time");
                    String img_url = rs.getString("img_url");

                    Record record = new Record(recipe_id, dish_name, meal_type, protein, cooking_time, img_url);
                    System.out.println("all recipes" + record.getDish_name() + record.getrecipe_id());
                    neededrecipes.add(record);
                }

                rs.close();
            }
            pstatemnt.close();
            con.close();


        } catch (Exception e)
        { System.out.println(e); }

        return neededrecipes;
    }
    
    public class Record {
        private int recipe_id;
        private String dish_name;
        private String meal_type;
        private String protein;
        private int cooking_time;
        private String img_url;

        public Record(int recipe_id,String dish_name, String meal_type, String protein, int cooking_time, String img_url){
            this.recipe_id = recipe_id;
            this.dish_name = dish_name;
            this.meal_type = meal_type;
            this.protein = protein;
            this.cooking_time = cooking_time;
            this.img_url = img_url;
        }

        // Getters
        public int getrecipe_id() {
            return recipe_id;
        }

        public String getDish_name() {
            return dish_name;
        }

        public String getMeal_type() {
            return meal_type;
        }

        public String getProtein () {
            return protein;
        }

        public int getCooking_time() {
            return cooking_time;
        }

        public String  getImg_url() {
            return img_url;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
