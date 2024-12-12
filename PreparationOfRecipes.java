package com.example.recipe_suggestion;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;


import java.util.ArrayList;
import java.util.List;

import java.sql.*;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class PreparationOfRecipes extends Application{

    private static List<Instructions> prep = new ArrayList<>();

    public List<Instructions> loadDataFromDatabase(int recipe_id) {

        String query = "SELECT * FROM Instructions WHERE recipe_id = ? ORDER BY step_number ASC;";

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(Credentials.url, Credentials.user, Credentials.password);

            PreparedStatement pstatemnt = con.prepareStatement(query);

            pstatemnt.clearParameters();
            pstatemnt.setInt(1, RecipeSelectionUI.wantedrecipeID);

            ResultSet rs = pstatemnt.executeQuery();

            while (rs.next()) {
                int step_number = rs.getInt("step_number");
                String instruction_description = rs.getString("instruction_description");

                Instructions preps = new Instructions(step_number, instruction_description);
                prep.add(preps);
            }

            rs.close();
            pstatemnt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return prep;
    }

    private TableView<Instructions> recipe_prep;
    private ObservableList<Instructions> data;

    public void start(Stage primaryStage){

        primaryStage.setTitle("Instructions");

        recipe_prep = new TableView<>();
        data = FXCollections.observableArrayList();

        TableColumn<Instructions, Integer> step_num = new TableColumn<>("");
        step_num.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStep_num()).asObject());

        TableColumn<Instructions, String> prep_instr = new TableColumn<>("");
        prep_instr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInstruction()));

        recipe_prep.getColumns().add(step_num);
        recipe_prep.getColumns().add(prep_instr);

        PreparationOfRecipes instr = new PreparationOfRecipes();
        List<Instructions> allinstr = instr.loadDataFromDatabase(RecipeSelectionUI.wantedrecipeID);

        data.addAll(allinstr);

        recipe_prep.setItems(data);

        VBox inst = new VBox();

        Scene scene = new Scene(inst);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static class Instructions{

        private int step_num;
        private String instruction;

        public Instructions(int step_num,String instruction){

            this.instruction = instruction;
            this.step_num = step_num;
        }
        public int getStep_num() {
            return step_num;
        }

        public String getInstruction() {
            return instruction;
        }
    }
}
