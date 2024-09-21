package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class BorrowBooksPage {

    private final Stage primaryStage;
    private final Map<String, String> bookDatabase = new HashMap<>();

    public BorrowBooksPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadBooks(); // Load books from the file
    }

    public Scene createBorrowBooksScene() {
        VBox layout = new VBox(20);
        layout.getStyleClass().add("vbox");

        Label bookIdLabel = new Label("Select Book ID:");
        bookIdLabel.getStyleClass().add("input-label");

        ComboBox<String> bookIdComboBox = new ComboBox<>();
        bookIdComboBox.getStyleClass().add("combo-box");
        bookIdComboBox.getItems().addAll(bookDatabase.keySet()); // Populate ComboBox with book IDs

        Label bookTitleLabel = new Label("Book Title:");
        bookTitleLabel.getStyleClass().add("input-label");

        Label bookTitleDisplay = new Label();
        bookTitleDisplay.getStyleClass().add("display-label");

        Label memberLabel = new Label("Member Name:");
        memberLabel.getStyleClass().add("input-label");

        TextField memberField = new TextField();
        memberField.getStyleClass().add("text-field");

        Button borrowButton = new Button("Borrow Book");
        borrowButton.getStyleClass().add("button");

        Button backButton = new Button("Back to Home");
        backButton.getStyleClass().add("button");

        bookIdComboBox.setOnAction(e -> {
            String selectedBookId = bookIdComboBox.getValue();
            if (selectedBookId != null) {
                String bookTitle = bookDatabase.get(selectedBookId);
                bookTitleDisplay.setText(bookTitle != null ? bookTitle : "No title available");
            }
        });

        borrowButton.setOnAction(e -> {
            String selectedBookId = bookIdComboBox.getValue();
            String memberName = memberField.getText();
            if (selectedBookId != null && !memberName.isEmpty()) {
                borrowBook(selectedBookId, memberName);
            } else {
                showAlert("Error", "Please select a book and enter a member name.");
            }
        });

        backButton.setOnAction(e -> new LibraryManagementApp().showHomePage(primaryStage));

        layout.getChildren().addAll(bookIdLabel, bookIdComboBox, bookTitleLabel, bookTitleDisplay, memberLabel, memberField, borrowButton, backButton);

        Scene scene = new Scene(layout, 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    private void borrowBook(String bookId, String memberName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"));
             PrintWriter writer = new PrintWriter(new FileWriter("books_temp.txt"));
             BufferedWriter borrowedWriter = new BufferedWriter(new FileWriter("borrowed_books.txt", true))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: " + bookId)) {
                    found = true;
                    // Save borrowed book information
                    LocalDate borrowDate = LocalDate.now();
                    LocalDate dueDate = borrowDate.plusWeeks(2); // Assuming 2 weeks borrowing period
                    borrowedWriter.write(String.format("ID: %s | Member: %s | Borrowed: %s | Due: %s%n", bookId, memberName, borrowDate, dueDate));
                    continue; // Skip writing the borrowed book to available books
                }
                writer.println(line);
            }

            if (found) {
                showAlert("Success", "Book borrowed successfully.");
            } else {
                showAlert("Error", "Book not found.");
            }

        } catch (IOException e) {
            showAlert("Error", "Failed to borrow book.");
            return;
        }

        // Replace old file with updated file
        if (!new java.io.File("books.txt").delete()) {
            showAlert("Error", "Failed to update book list.");
            return;
        }
        if (!new java.io.File("books_temp.txt").renameTo(new java.io.File("books.txt"))) {
            showAlert("Error", "Failed to update book list.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadBooks() {
        // Load books from the books.txt file
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Example line: ID: 1, Title: Book Title, Author: Author Name
                String[] parts = line.split(","); // Split the line by comma

                if (parts.length >= 2) {
                    String idPart = parts[0].trim();
                    String titlePart = parts[1].trim();

                    // Extract ID and Title
                    String bookId = idPart.split(":")[1].trim();
                    String bookTitle = titlePart.split(":")[1].trim();

                    // Add to the database
                    bookDatabase.put(bookId, bookTitle);
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load books from file.");
        }
    }
}
