package com.example.helbelectro.products;

import com.example.helbelectro.MainController;
import com.example.helbelectro.ProductSeller;
import com.example.helbelectro.components.Component;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;
/**
 * Classe ProductDetail
 * Cette classe gère l'affichage des détails d'un produit.
 */
public class ProductDetail implements ProductZoneObserver {
    private static Stage stageDetails; // Variable statique pour la fenêtre de détails
    private static boolean isDetailsOpen = false; // Variable statique pour suivre l'état de la fenêtre de détails
    private static boolean isClosingDetails = false;
    private Stage stageStats;
    private ProductZone productZone;

    public ProductDetail(ProductZone productZone) {
        this.productZone = productZone;
        productZone.addObserver(this);
    }

    public void showDetails() {
        if (isDetailsOpen || isClosingDetails) {
            return;
        }

        String rowLabel = MainController.rowLabels.get(productZone.getY()).getText();
        String columnLabel = MainController.columnLabels.get(productZone.getX()).getText();

        stageDetails = new Stage();
        stageDetails.setTitle("Emplacement produit");
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Button statsButton = createStatsButton(rowLabel, columnLabel);
        vbox.getChildren().add(statsButton);

        String status = productZone.isFree() ? "libre" : "occupé";
        Label lbLocation = new Label("Emplacement : (" + rowLabel + ", " + columnLabel + ")");
        Label lbStatus = new Label("Statut : " + status);
        vbox.getChildren().addAll(lbLocation, lbStatus);

        Product product = productZone.getProduct();
        if (product != null) {
            Label typeLabel = new Label("Type Produit: " + product.getTypeProduct());
            Label priceLabel = new Label("Prix : " + product.getPrice() + " euros");
            Label ecoScoreLabel = new Label("Eco Score : " + product.getEcoScore());

            vbox.getChildren().addAll(typeLabel, ecoScoreLabel, priceLabel);

            for (Component component : product.getComponents()) {
                Label componentLabel = new Label(component.getDetails());
                vbox.getChildren().add(componentLabel);
            }

            Button sellButton = createSellButton();
            vbox.getChildren().add(sellButton);
        }

        stageDetails.setOnCloseRequest(e -> {
            isClosingDetails = true;
            if (stageStats != null && stageStats.isShowing()) {
                stageStats.close();
            }
            isDetailsOpen = false;
            isClosingDetails = false;
        });

        Scene scene = new Scene(vbox);
        stageDetails.setScene(scene);
        stageDetails.sizeToScene();
        stageDetails.show();

        isDetailsOpen = true;
    }


    private Button createSellButton() {
        Button sellButton = new Button("Vendre Produit");
        sellButton.setOnAction(e -> {
            ProductSeller productSeller = new ProductSeller(productZone);
            productSeller.sellProduct();
            stageDetails.close();
            if (stageStats != null) {
                stageStats.close();
            }
            isDetailsOpen = false; // Met à jour l'état de la fenêtre de détails
        });
        return sellButton;
    }


    private Button createStatsButton(String rowLabel, String columnLabel) {
        Button statsButton = new Button("Voir les statistiques");
        statsButton.setOnAction(e -> {
            if (stageStats != null) {
                stageStats.close();
            }

            stageStats = new Stage();
            stageStats.setTitle("Statistiques pour l'emplacement (" + rowLabel + ", " + columnLabel + ")");
            VBox vbox2 = new VBox(10);

            for (Map.Entry<String, Integer> entry : productZone.getProductCounts().entrySet()) {
                vbox2.getChildren().add(new Label("Nombre total de " + entry.getKey() + ": " + entry.getValue()));
            }

            vbox2.getChildren().add(new Label("Total des ventes: " + productZone.getTotalSales()));

            Scene scene = new Scene(vbox2);
            stageStats.setScene(scene);
            stageStats.sizeToScene();
            stageStats.showAndWait();
        });
        return statsButton;
    }
    public Stage getStatsStage() {
        return stageStats;
    }
    public Stage getDetailStage() {
        return stageDetails;
    }



    @Override
    public void updateProductZone(ProductZone productZone) {
        if (isDetailsOpen && !MainController.isProductSold()) {
            Platform.runLater(this::showDetails);
        }
    }
}