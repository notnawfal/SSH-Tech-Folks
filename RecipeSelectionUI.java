package com.example.recipe_suggestion;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.sql.*;

public class RecipeSelectionUI extends Application {

    private TableView<RecipeFilterApp.Record> recipes_table;
    private ObservableList<RecipeFilterApp.Record> data;

    public static int wantedrecipeID;

    @Override
    public void start(Stage stage) {

        recipes_table = new TableView<>();
        data = FXCollections.observableArrayList();

        // columns
        TableColumn<RecipeFilterApp.Record, String> dishColumn = new TableColumn<>("Recipe");
        dishColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDish_name()));

        TableColumn<RecipeFilterApp.Record, String> mealColumn = new TableColumn<>("MealTime");
        mealColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeal_type()));

        TableColumn<RecipeFilterApp.Record, String> proteinColumn = new TableColumn<>("Protein");
        proteinColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProtein()));

        TableColumn<RecipeFilterApp.Record, Integer> cooking_timeColumn = new TableColumn<>("CookingTime");
        cooking_timeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCooking_time()).asObject());

        TableColumn<RecipeFilterApp.Record, String> imgColumn = new TableColumn<>("Image");
        imgColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImg_url()));

        // Add columns
        recipes_table.getColumns().add(dishColumn);
        recipes_table.getColumns().add(mealColumn);
        recipes_table.getColumns().add(proteinColumn);
        recipes_table.getColumns().add(cooking_timeColumn);
        recipes_table.getColumns().add(imgColumn);

        // records from database
        RecipeFilterApp recipeRecords = new RecipeFilterApp();
        List<RecipeFilterApp.Record> records = recipeRecords.getNeededRecipes();
       
        data.addAll(records);

        recipes_table.setItems(data);

        // selecting a recipe
        recipes_table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                wantedrecipeID = newValue.getrecipe_id();

                PreparationOfRecipes starting = new PreparationOfRecipes();
                Stage inststage = new Stage();
                starting.start(inststage);
            }
        });


        VBox recipes = new VBox(recipes_table);

        Scene scene = new Scene(recipes);
        stage.setScene(scene);
        stage.setTitle("Click any one of the recipes to view its preparation");
        stage.show();
    }
}
