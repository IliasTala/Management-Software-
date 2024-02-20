package com.example.helbelectro.products;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe ProductZone
 * Elle représente une zone de produit dans l'application.
 */
public class ProductZone {

    // Variables d'instance
    private Product product;
    private int location;
    private Button button;
    private boolean isFree = true;
    private String typeProduct = "";
    private String ecoScore;
    private int price;
    private int x;
    private int y;
    private Map<String, Integer> productCounts = new HashMap<>();
    private int totalSales = 0;
    private List<ProductZoneObserver> observers = new ArrayList<>();
    private ProductDetail productDetail;
    private boolean productSold = false;

    // Constantes pour le style des boutons
    private static final String buttonStyleFree = "-fx-background-color: white; -fx-border-color: black;";
    private static final String buttonStyleFreeOccupied = "-fx-background-color: ";

    public ProductZone(int location, int x, int y) {
        this.location = location;
        this.x = x;
        this.y = y;
        button = new Button(location + "");
        if (isFree) {
            button.setStyle("-fx-background-color: white");
            button.setStyle("-fx-border-color: black;");
            button.setText("");
        }
        display();
    }

    private void display() {
        button.setOnAction(e -> {
            notifyObservers();
            if (productDetail != null) {
                Stage detailStage = productDetail.getDetailStage();
                if (detailStage != null && detailStage.isShowing()) {
                    detailStage.close(); // Fermer la fenêtre de détails existante si elle est ouverte
                }
                productDetail.showDetails();
            }
        });
    }

    ///////////// Méthodes pour la gestion des observateurs ////////////////
    public void addObserver(ProductZoneObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ProductZoneObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (ProductZoneObserver observer : observers) {
            observer.updateProductZone(this);
        }
    }

    public boolean isFree() {
        return isFree;
    }
    /////////////////////////     GETTERS     ///////////////////////////////////
    public Button getButton() {
        return button;
    }

    public Product getProduct() {
        return product;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public String getEcoScore() {
        return ecoScore;
    }

    public int getPrice() {
        return price;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Map<String, Integer> getProductCounts() {
        return productCounts;
    }

    public int getTotalSales() {
        return totalSales;
    }

    /////////////////////////     SETTERS     ///////////////////////////////////
    public void setProduct(Product product) {
        this.product = product;

        if (product != null) {
            this.isFree = false;
            String newType = product.getTypeProduct();
            productCounts.put(newType, productCounts.getOrDefault(newType, 0) + 1);

            this.typeProduct = product.getTypeProduct();
            this.ecoScore = product.getEcoScore();
            this.price = product.getPrice();
            this.button.setText(product.toString());
            this.button.setStyle(buttonStyleFreeOccupied + product.getColor());
        } else {
            this.isFree = true;
            this.button.setText("");
            this.button.setStyle(buttonStyleFree);

            if (productDetail != null) {
                Stage statsStage = productDetail.getStatsStage();
                if (statsStage != null && statsStage.isShowing()) {
                    statsStage.close();
                }
            }
        }

        if (!productSold) {
            notifyObservers();
        }
    }

    public void setIncrementSales() {
        totalSales++;
    }

}
