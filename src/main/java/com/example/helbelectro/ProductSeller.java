package com.example.helbelectro;

import com.example.helbelectro.products.ProductDetail;
import com.example.helbelectro.products.ProductZone;

public class ProductSeller {
    private ProductZone productZone;

    public ProductSeller(ProductZone productZone) {
        this.productZone = productZone;
        initialize();
    }

    private void initialize() {
        ProductDetail productDetail = new ProductDetail(productZone);
        productZone.getButton().setOnAction(e -> {
            productDetail.showDetails();
        });
    }

    public void sellProduct() {
        TicketGenerator.generateTicket(productZone.getProduct());
        productZone.setIncrementSales();
        productZone.setProduct(null);
        MainController.setProductSold(true);
        productZone.notifyObservers();
        MainController.setProductSold(false);
    }

    public boolean isProductSold() {
        return MainController.isProductSold();
    }
}
