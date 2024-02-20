module com.example.helbelectro {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.helbelectro to javafx.fxml;
    exports com.example.helbelectro;
    exports com.example.helbelectro.components;
    opens com.example.helbelectro.components to javafx.fxml;
    exports com.example.helbelectro.products;
    opens com.example.helbelectro.products to javafx.fxml;
}