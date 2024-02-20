package com.example.helbelectro;

import com.example.helbelectro.components.Component;
import com.example.helbelectro.products.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketGenerator {
    public static void generateTicket(Product product) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        String formattedTime = LocalDateTime.now().format(timeFormatter);

        String fileName = "t_" + formattedTime + ".txt";

        try (FileWriter fileWriter = new FileWriter(fileName);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("----------------------------------------");
            printWriter.println("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")));
            printWriter.println("Type produit: " + product.getTypeProduct());
            printWriter.println("Prix: " + product.getPrice() + " euros");
            printWriter.println("Eco-Score: " + product.getEcoScore());

            for (Component component : product.getComponents()) {
                printWriter.println(component.getDetails());
            }
            printWriter.println("----------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}