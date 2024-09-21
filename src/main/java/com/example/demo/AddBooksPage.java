package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AddBooksPage {

    private final Stage primaryStage;

    public AddBooksPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene createAddBooksScene() {
        VBox layout = new VBox(20);
        layout.getStyleClass().add("vbox");

        Label titleLabel = new Label("Book Title:");
        titleLabel.getStyleClass().add("input-label");

        TextField titleField = new TextField();
        titleField.getStyleClass().add("text-field");

        Label authorLabel = new Label("Author:");
        authorLabel.getStyleClass().add("input-label");

        TextField authorField = new TextField();
        authorField.getStyleClass().add("text-field");

        Button addButton = new Button("Add Book");
        addButton.getStyleClass().add("button");

        Button backButton = new Button("Back to Home");
        backButton.getStyleClass().add("button");

        addButton.setOnAction(e -> addBook(titleField.getText(), authorField.getText()));
        backButton.setOnAction(e -> new LibraryManagementApp().showHomePage(primaryStage));

        layout.getChildren().addAll(titleLabel, titleField, authorLabel, authorField, addButton, backButton);

        Scene scene = new Scene(layout, 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    private void addBook(String title, String author) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt", true))) {
            writer.write(title + " by " + author);
            writer.newLine();
            showAlert("Success", "Book added successfully.");
        } catch (IOException e) {
            showAlert("Error", "Failed to add book.");
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
