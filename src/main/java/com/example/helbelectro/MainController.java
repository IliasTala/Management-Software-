package com.example.helbelectro;

import com.example.helbelectro.components.Component;
import com.example.helbelectro.components.ComponentZone;
import com.example.helbelectro.components.FactoryComponents;
import com.example.helbelectro.products.FactoryProducts;
import com.example.helbelectro.products.Product;
import com.example.helbelectro.products.ProductDetail;
import com.example.helbelectro.products.ProductZone;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.helbelectro.InterfaceView.productGrid;

public class MainController {

    protected static ArrayList<ProductZone> listProductZone = new ArrayList<>(); // Liste des emplacement
    protected static ArrayList<Product> listProduct = new ArrayList<>();
    protected static ArrayList<Component> listComponent = new ArrayList<>();
    protected static Timer timer = new Timer();
    protected static final int columns = 4;
    protected static final int rows = 100;
    private static final String filePath = "/helbelectro.data.txt";
    private static boolean isProductInProduction = false;
    protected static FactoryComponents factoryComponent = FactoryComponents.getInstance();
    protected static FactoryProducts factoryProduct = FactoryProducts.getInstance();
    protected static ComponentZone componentZone = new ComponentZone();
    private static final Map<String, List<String>> productComponentsMap = new HashMap<>() {{
        put("Drone de surveillance", Arrays.asList("battery", "sensorMotion", "motorElectrical"));
        put("Alarme de sécurité", Arrays.asList("battery", "sensorMotion"));
        put("Voiture télécommandée", Arrays.asList("battery", "motorElectrical"));
        put("Robot suiveur", Arrays.asList("sensorMotion", "motorElectrical"));
        put("Batterie", List.of("battery"));
        put("Capteur de mouvement", List.of("sensorMotion"));
        put("Moteur électrique", List.of("motorElectrical"));
    }};
    protected static List<Map.Entry<String, List<String>>> sortedProductTypes = new ArrayList<>(productComponentsMap.entrySet());
    protected static boolean isNumberLabel = true;
    public static List<Label> rowLabels = new ArrayList<>();
    public static List<Label> columnLabels = new ArrayList<>();
    private static boolean productSold = false;

    public static void setProductSold(boolean value) {
        productSold = value;
    }

    public void initialize() {
        initializeProductZones();
      //  initializeLabels();

        loadComponentsFromFile();
        startProductCreationTimer();
        //initializeProductZoneControllers();
    }
    private void initializeProductZones() {
        int cpt = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                ProductZone pZone = new ProductZone(cpt, j, i);
                listProductZone.add(pZone);
                pZone.getButton().setPrefHeight(100);
                pZone.getButton().setPrefWidth(250);
                productGrid.add(pZone.getButton(), j + 1, i + 1);

                ProductDetail productDetail = new ProductDetail(pZone);
                pZone.getButton().setOnAction(e -> {
                    productDetail.showDetails();
                });
            }
        }
    }
    /*private void initializeLabels() {
        for (int j = 0; j < columns; j++) {
            Label columnLabel = new Label(Integer.toString(j));
            columnLabels.add(columnLabel);
            columnLabel.setAlignment(Pos.CENTER);
        }

        for (int i = 0; i < rows; i++) {
            Label rowLabel = new Label(Integer.toString(i));
            rowLabels.add(rowLabel);
            rowLabel.setAlignment(Pos.CENTER);
        }

       *//* for (int j = 0; j < columns; j++) {
            productGrid.add(columnLabels.get(j), j + 1, 0);
        }*//*

        for (int i = 0; i < rows; i++) {
            productGrid.add(rowLabels.get(i), 0, i + 1);
        }
    }*/
    private void initializeProductZoneControllers() {
        for (ProductZone productZone : listProductZone) {
            ProductSeller productSeller = new ProductSeller(productZone);
        }
    }
    public void changeLabels() {
        if (isNumberLabel) {
            // Changer les labels pour afficher les lettres
            for (int i = 0; i < rows; i++) {
                rowLabels.get(i).setText(convertNumberToLetters(i));
            }
            for (int j = 0; j < columns; j++) {
                columnLabels.get(j).setText(convertNumberToLetters(j));
            }
        } else {
            // Changer les labels pour afficher les numéros
            for (int i = 0; i < rows; i++) {
                rowLabels.get(i).setText(Integer.toString(i));
            }
            for (int j = 0; j < columns; j++) {
                columnLabels.get(j).setText(Integer.toString(j));
            }
        }
        // Basculer entre les chiffres et les lettres
        isNumberLabel = !isNumberLabel;
    }
    private static String convertNumberToLetters(int number) {
        StringBuilder result = new StringBuilder();
        while (number >= 0) {
            result.insert(0, (char) ('A' + number % 26));
            number = number / 26 - 1;
        }
        return result.toString();
    }
    private void loadComponentsFromFile() {
        Scanner scan = new Scanner(MainController.class.getResourceAsStream(filePath));

        double accumulatedDelay = 0;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] tokens = line.split(",");

            int delay = Integer.parseInt(tokens[0]);
            accumulatedDelay += delay;

            PauseTransition pause = new PauseTransition(Duration.seconds(accumulatedDelay));
            pause.setOnFinished(event -> {
                if (componentZone.getComponents().size() >= componentZone.getMaxComponentCanBeCreated()) {
                    return;
                }

                boolean allProductZonesOccupied = listProductZone.stream().noneMatch(ProductZone::isFree);
                if (allProductZonesOccupied) {
                    return;
                }

                String type = tokens[1];
                String value = tokens[2];
                String color = tokens.length > 3 ? tokens[3] : null;
                Component component = factoryComponent.createComponent(type, value, color);
                componentZone.addComponent(component);
                listComponent.add(component);
            });
            pause.play();
        }
    }
    public Callback<ListView<Component>, ListCell<Component>> getComponentCellFactory() {
        return param -> new ListCell<>() {
            @Override
            protected void updateItem(Component item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getTypeComponent());
                    setAlignment(Pos.CENTER);
                    setStyle("-fx-background-color: " + item.getColor() + ";");
                }
            }
        };
    }
    public static void sortByOptimisationApply() {
        Comparator<Map.Entry<String, List<String>>> comparator = null;
        // ajouter reversed() pour decroissant
        if (InterfaceView.selectedOptimization == Optimization.PRICE) {
            comparator = Comparator.comparing(o -> factoryProduct.createProduct(o.getKey(), new ArrayList<>()).getPrice());
        }
        else if (InterfaceView.selectedOptimization == Optimization.DURATION) {
            comparator = Comparator.comparing(o -> factoryProduct.createProduct(o.getKey(), new ArrayList<>()).getTimeFactor());
        }
        else if (InterfaceView.selectedOptimization == Optimization.ECO_SCORE) {
            comparator = Comparator.comparing(o -> factoryProduct.createProduct(o.getKey(), new ArrayList<>()).getEcoScore());
        }
        else if (InterfaceView.selectedOptimization == Optimization.DIVERSITY) {
            Map<String, Long> productCounts = listProduct.stream()
                    .collect(Collectors.groupingBy(Product::getTypeProduct, Collectors.counting()));

            comparator = Comparator.comparing(o -> productCounts.getOrDefault(o.getKey(), 0L));
        }

        if (comparator != null) {
            sortedProductTypes.sort(comparator);
        }
    }
    public static void startProductCreationTimer() {
        // Création d'un nouveau Timer
        timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(MainController::createProductFromComponents);
            }
        };
        long interval = 1000; // 1000 ms = 1 seconde

        timer.scheduleAtFixedRate(task, 0, interval);
    }
    private static void removeUsedComponents(Product product) {
            Map<String, Integer> requiredComponentsCount = new HashMap<>();

            // Compter le nombre de chaque type de composant requis
            for (Component component : product.getComponents()) {
                String componentType = component.getType();
                requiredComponentsCount.put(componentType, requiredComponentsCount.getOrDefault(componentType, 0) + 1);
            }

            // Supprimer un seul composant de chaque type requis
            for (Map.Entry<String, Integer> entry : requiredComponentsCount.entrySet()) {
                String requiredComponentType = entry.getKey();
                int requiredComponentCount = entry.getValue();

                for (int i = 0; i < requiredComponentCount; i++) {
                    Component foundComponent = null;
                    for (Component component : listComponent) {
                        if (component.getType().equals(requiredComponentType)) {
                            foundComponent = component;
                            break;
                        }
                    }
                    if (foundComponent != null) {
                        listComponent.remove(foundComponent);
                        componentZone.removeComponent(foundComponent);
                    }
                }
            }
        }
    private static void createProductFromComponents() {
        if (isProductInProduction || InterfaceView.selectedOptimization == null ) {
            return; // Si un produit est déjà en cours de fabrication, ignorez cette itération
        }

        // Vérifie si toutes les zones de produits sont occupées avant de créer un nouveau produit
        boolean allProductZonesOccupied = listProductZone.stream().noneMatch(ProductZone::isFree);
        if (allProductZonesOccupied) {
            // Arrête la production de produits
            timer.cancel();

            // Affiche le popup
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Entrepôt plein");
                alert.setHeaderText(null);
                alert.setContentText("La totalité du stock va vers un nouvel entrepôt plus grand.");

                ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == okButton){
                    // Supprime tous les produits des zones de produits
                    listProductZone.forEach(productZone -> productZone.setProduct(null));
                    listProduct.clear();

                    // Reprend la production de produits
                    startProductCreationTimer();
                }
            });

            return;
        }

        sortByOptimisationApply();

        for (Map.Entry<String, List<String>> entry : sortedProductTypes) {
            String productType = entry.getKey();
            List<String> requiredComponents = entry.getValue();

            // Créer un HashSet des types de composants requis pour supprimer les doublons
            Set<String> uniqueComponentTypes = new HashSet<>(requiredComponents);

            // Vérifie si tous les composants requis sont disponibles
            boolean allComponentsAvailable = uniqueComponentTypes.stream()
                    .allMatch(componentType -> listComponent.stream()
                            .anyMatch(component -> component.getType().equals(componentType)));

            if (allComponentsAvailable) {
                isProductInProduction = true;

                // Créer une nouvelle liste de composants utilisables avec un seul composant de chaque type
                List<Component> componentsToUse = new ArrayList<>();
                for (String componentType : uniqueComponentTypes) {
                    Component foundComponent = null;
                    for (Component component : listComponent) {
                        if (component.getType().equals(componentType)) {
                            foundComponent = component;
                            break;
                        }
                    }
                    if (foundComponent != null) {
                        componentsToUse.add(foundComponent);
                    }
                }

                Product product = factoryProduct.createProduct(productType, componentsToUse);

                // Ajouter un délai correspondant au temps de fabrication du produit
                PauseTransition pause = new PauseTransition(Duration.seconds(product.getTimeFactor()));
                pause.setOnFinished(event -> {
                    isProductInProduction = false;

                    // Ajoute le produit à la première zone de produit disponible
                    for (ProductZone zone : listProductZone) {
                        if (zone.isFree()) {
                            zone.setProduct(product);
                            break;
                        }
                    }

                    listProduct.add(product);
                    System.out.println(product);

                    removeUsedComponents(product);
                });

                pause.play();
                System.out.println(product.getPrice()+" "+ product.getTimeFactor()+" "+product.getComponents()+ " "+ product.getTypeProduct());
                pause.play();
                break;
            }
        }
    }
    public static boolean isProductSold() {
        return productSold;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
}
