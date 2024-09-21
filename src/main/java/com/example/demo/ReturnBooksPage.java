package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReturnBooksPage {

    private final Stage primaryStage;

    public ReturnBooksPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene createReturnBooksScene() {
        VBox layout = new VBox(20);
        layout.getStyleClass().add("vbox");

        Label titleLabel = new Label("Book Title to Return:");
        titleLabel.getStyleClass().add("input-label");

        TextField titleField = new TextField();
        titleField.getStyleClass().add("text-field");

        Button returnButton = new Button("Return Book");
        returnButton.getStyleClass().add("button");

        Button backButton = new Button("Back to Home");
        backButton.getStyleClass().add("button");

        returnButton.setOnAction(e -> returnBook(titleField.getText()));
        backButton.setOnAction(e -> new LibraryManagementApp().showHomePage(primaryStage));

        layout.getChildren().addAll(titleLabel, titleField, returnButton, backButton);

        Scene scene = new Scene(layout, 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    private void returnBook(String title) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt", true))) {
            writer.write(title);
            writer.newLine();
            showAlert("Success", "Book returned successfully.");
        } catch (IOException e) {
            showAlert("Error", "Failed to return book.");
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
