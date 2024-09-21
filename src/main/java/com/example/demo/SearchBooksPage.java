package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchBooksPage {

    private final Stage primaryStage;

    public SearchBooksPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene createSearchBooksScene() {
        VBox layout = new VBox(20);
        layout.getStyleClass().add("vbox");

        Label searchLabel = new Label("Search for a Book:");
        searchLabel.getStyleClass().add("input-label");

        TextField searchField = new TextField();
        searchField.getStyleClass().add("text-field");

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("button");

        Button backButton = new Button("Back to Home");
        backButton.getStyleClass().add("button");

        searchButton.setOnAction(e -> searchBooks(searchField.getText()));
        backButton.setOnAction(e -> new LibraryManagementApp().showHomePage(primaryStage));

        layout.getChildren().addAll(searchLabel, searchField, searchButton, backButton);

        Scene scene = new Scene(layout, 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    private void searchBooks(String query) {
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains(query)) {
                    showAlert("Search Result", "Book found: " + line);
                    found = true;
                    break;
                }
            }
            if (!found) {
                showAlert("Search Result", "No books found matching: " + query);
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to search books.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
