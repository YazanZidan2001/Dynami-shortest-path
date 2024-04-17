package com.example.project1_algo;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    static Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    @Override
    public void start(Stage stage) throws Exception {


//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
//        fileChooser.setTitle("please choose file");
//        File file = fileChooser.showOpenDialog(stage);
        File file = new File("C:\\Users\\yazoo\\Desktop\\College\\algo\\Project1_algo\\input.txt");
        String fPath = file.getPath();


        DPShortestPath dp = new DPShortestPath(file);
        String tt = dp.getDynamicTable4(fPath);
        System.out.println(tt);
        dp.print();

        List<String> starts = null;
        List<String> ends = null;
        List<String> startPoints = null;
        List<String> letters = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fPath))) {
            startPoints = new ArrayList<>();
            letters = new ArrayList<>();

            starts = new ArrayList<>();
            ends = new ArrayList<>();


            String line;
            int lineNumber = 0;
            String[] stringArray = new String[2];
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 2) {
                    stringArray = line.split(", ");
                    //   System.out.println(Arrays.toString(stringArray));

                }

                String[] parts = line.split(",");
                String task = parts[0].trim();

                if (task.equals(stringArray[0])) {
                    startPoints.add(task);
                } else {
                    starts.add(task);
                    ends.add(task);
                    letters.add(task);
                }
            }


            letters.remove(0);
            starts.add(0, stringArray[0]);
            starts.remove(1);
            ends.add(stringArray[1]);
            ends.remove(0);

            System.out.println("Start points: " + starts);
            System.out.println("Letters: " + letters);
            System.out.println("End points: " + ends);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert ends != null;
        int lenEnds = ends.size();


        Pane root = new Pane();
        root.setStyle("-fx-background-color: lightblue;");
        root.setPrefSize(1100, 856);


        Label startLabel = new Label("Please choose the start point");
        startLabel.setFont(new Font(20));
        startLabel.setLayoutX(23);
        startLabel.setLayoutY(30);
        startLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));

        ComboBox<String> startCom = new ComboBox<>();
        startCom.setLayoutX(313);
        startCom.setLayoutY(30);
        startCom.setPrefSize(149, 44);
        startCom.setItems(FXCollections.observableArrayList(starts));
        startCom.setValue(starts.get(0));
        startCom.setStyle("-fx-font: 20px \"Serif\"; -fx-border-color: black; -fx-background-color:tan ");


        Label endLabel = new Label("Please choose the end point");
        endLabel.setFont(new Font(20));
        endLabel.setLayoutX(485);
        endLabel.setLayoutY(30);
        endLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));

        ComboBox<String> endCom = new ComboBox<>();
        endCom.setLayoutX(763);
        endCom.setLayoutY(30);
        endCom.setPrefSize(144, 44);
        endCom.setItems(FXCollections.observableArrayList(ends));
        endCom.setValue(ends.get(lenEnds - 1));
        endCom.setStyle("-fx-font: 20px \"Serif\"; -fx-border-color: black; -fx-background-color:tan");


        Button findCost = new Button("Find the lowest cost");
        findCost.setLayoutX(18.0);
        findCost.setLayoutY(96);
        findCost.setPrefSize(203, 37);
        findCost.setStyle("-fx-font: 20px \"Serif\"; -fx-border-color: black; -fx-background-color:tan ");


        TextField costText = new TextField();
        costText.setLayoutX(229);
        costText.setLayoutY(96);
        costText.setPrefSize(203, 37);
        costText.setEditable(false);
        costText.setFont(Font.font(15));
        costText.setStyle("-fx-border-color : black;");


        findCost.setOnAction(e -> {
            try {
                int length = dp.getShortestPathCost(startCom.getValue(), endCom.getValue(), 1);
                costText.setText(String.valueOf(length));

            } catch (Exception o) {
                displayError("Error!!!!!!!!!!!", o.getMessage());
            }

        });

        Button findPath = new Button("Find the path");
        findPath.setLayoutX(452);
        findPath.setLayoutY(96);
        findPath.setPrefSize(144, 37);
        findPath.setStyle("-fx-font: 20px \"Serif\"; -fx-border-color: black; -fx-background-color:tan");


        TextField pathText = new TextField();
        pathText.setLayoutX(610);
        pathText.setLayoutY(96);
        pathText.setPrefSize(428, 37);
        pathText.setEditable(false);
        pathText.setFont(Font.font(15));
        pathText.setStyle("-fx-border-color : black;");

        final int[] count = {2};

        findPath.setOnAction(e -> {
            try {
                String paths = dp.getShortestPath(startCom.getValue(), endCom.getValue(), 1);
                pathText.setText(paths);
            } catch (Exception o) {
                displayError("Error!!!!!!!!!!!", o.getMessage());
            }

        });


        Button findCost2 = new Button("Find the next solution");
        findCost2.setLayoutX(388.0);
        findCost2.setLayoutY(148);
        findCost2.setPrefHeight(37.0);
        findCost2.setPrefWidth(209.0);
        findCost2.setStyle("-fx-font: 20px \"Serif\"; -fx-border-color: black; -fx-background-color:tan");

        TextField costText2 = new TextField();
        costText2.setLayoutX(223.0);
        costText2.setLayoutY(200);
        costText2.setPrefHeight(37.0);
        costText2.setPrefWidth(209.0);
        costText2.setEditable(false);
        costText.setFont(Font.font(15));
        costText2.setStyle("-fx-border-color : black;");

        TextField pathText2 = new TextField();
        pathText2.setLayoutX(610.0);
        pathText2.setLayoutY(200);
        pathText2.setPrefHeight(37.0);
        pathText2.setPrefWidth(428.0);
        pathText.setEditable(false);
        pathText2.setFont(Font.font(15));
        pathText2.setStyle("-fx-border-color : black;");


        findCost2.setOnAction(e -> {


            try {
                int length = dp.getShortestPathCost(startCom.getValue(), endCom.getValue(), count[0]);
                costText2.setText(String.valueOf(length));

                String paths = dp.getShortestPath(startCom.getValue(), endCom.getValue(), count[0]);
                pathText2.setText(paths);

                count[0]++;

            } catch (Exception o) {
                displayError("Error!!!!!!", "no another paths");
            }


        });

        Label cost2 = new Label("The next cost");
        cost2.setLayoutX(45.0);
        cost2.setLayoutY(200);
        cost2.setPrefHeight(37.0);
        cost2.setPrefWidth(149.0);
        cost2.setFont(new Font(20.0));
        cost2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));

        Label path2 = new Label("The next path");
        path2.setLayoutX(450.0);
        path2.setLayoutY(200);
        path2.setPrefHeight(23.0);
        path2.setPrefWidth(149.0);
        path2.setFont(new Font(20.0));
        path2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));


        Button tableBut = new Button("Print the table");
        tableBut.setLayoutX(391.0);
        tableBut.setLayoutY(245);
        tableBut.setPrefHeight(40);
        tableBut.setPrefWidth(170.0);
        tableBut.setStyle("-fx-font: 20px \"Serif\"; -fx-border-color: black; -fx-background-color:tan  ");


        TextArea table = new TextArea();
        table.setLayoutX(13.0);
        table.setLayoutY(290);
        table.setPrefHeight(500);
        table.setPrefWidth(1087);
        table.setEditable(false);
        table.setStyle("-fx-border-color : black;");
        Font font = Font.font("Monospaced", 15);
        table.setFont(font);


        tableBut.setOnAction(e ->
                table.setText(dp.getDynamicTable4(fPath))
        );


        Button restBut = new Button("CLEAR");
        restBut.setLayoutX(940);
        restBut.setLayoutY(245);
        restBut.setPrefSize(150, 40);
        restBut.setStyle("-fx-font: 20px \"Serif\"; -fx-border-color: black; -fx-background-color:tan  ");

        restBut.setOnAction(e -> {
            costText.setText("");
            pathText.setText("");
            costText2.setText("");
            pathText2.setText("");
            table.setText("");
            count[0] = 2;
        });

        root.getChildren().addAll(
                startLabel, startCom, endLabel, endCom, findCost, costText, findPath, pathText, findCost2,
                costText2, pathText2, tableBut, table, cost2, path2, restBut
        );


        Scene scene = new Scene(root, 1120, 800);
        stage.setScene(scene);
        stage.setTitle("Get shortest path");
        stage.show();

    }

    public void displayError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("Error Massage");
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch();
    }
}