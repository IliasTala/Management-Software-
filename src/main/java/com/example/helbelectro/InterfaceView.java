package com.example.helbelectro;

import com.example.helbelectro.components.Component;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class InterfaceView {
    public static GridPane productGrid = new GridPane();
    public static ListView<Component> componentListView = new ListView<>();

    public static VBox componentContainer = new VBox(10);
    public static VBox productContainer = new VBox(10);
    public static HBox mainContainer = new HBox(15);
    public static SplitPane splitPane = new SplitPane();
    public static Optimization selectedOptimization;
    static Button changeLabelsButton = new Button();

    public static void initGrid() {

        ComboBox<Optimization> comboBox = new ComboBox<>();
        ObservableList<Optimization> options = FXCollections.observableArrayList(Optimization.values());
        comboBox.setItems(options);
        componentContainer.getChildren().add(comboBox);
        comboBox.setPromptText("Choice Optimization");

        MainController mainController = new MainController();

        comboBox.setOnAction(event -> {
            selectedOptimization = comboBox.getValue();
        });

        changeLabelsButton = new Button("Changer le type d'emplacement");
        changeLabelsButton.setOnAction(e -> {
            mainController.changeLabels();
        });
       // productContainer.getChildren().addAll(changeLabelsButton, productGrid);

        componentContainer.getChildren().add( componentListView);
        componentContainer.setPadding(new Insets(20));

        mainContainer.getChildren().addAll(productContainer, componentContainer);

        splitPane.getItems().addAll(productContainer, componentContainer);
        splitPane.setDividerPositions(0.8);

        mainContainer.setMaxWidth(Double.MAX_VALUE);

        // Style de la zone des composants
        componentListView.setItems(mainController.componentZone.getComponents());
        componentListView.setCellFactory(mainController.getComponentCellFactory());
        componentListView.setFixedCellSize(50);
        componentListView.setMaxHeight(400);
        componentListView.setStyle("-fx-border-color: black;");

        //Style de la zone des produits
        productGrid.setVgap(30);
        productGrid.setHgap(30);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(productGrid);
        productContainer.getChildren().addAll(changeLabelsButton, scrollPane);
        productContainer.setPadding(new Insets(20));

        // Initialise le contrôleur
        mainController.initialize();
    }
}

