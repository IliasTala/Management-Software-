package com.example.helbelectro;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        InterfaceView.initGrid();
        MainController mainController = new MainController();
        mainController.initialize();

        Scene scene = new Scene(InterfaceView.splitPane, 1100, 700);
        primaryStage.setTitle("HELBELECTRO");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}