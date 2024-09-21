package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterMemberPage {

    private final Stage primaryStage;

    // Store members with their IDs
    private final Map<Integer, String> memberDatabase = new HashMap<>();
    private static int memberIdCounter = 1; // Start Member ID from 1

    public RegisterMemberPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene createRegisterMemberScene() {
        VBox layout = new VBox(20);
        layout.getStyleClass().add("vbox");

        // Member Name Input
        Label memberLabel = new Label("Member Name:");
        memberLabel.getStyleClass().add("input-label");

        TextField memberField = new TextField();
        memberField.getStyleClass().add("text-field");

        // Member Email Input
        Label emailLabel = new Label("Email:");
        emailLabel.getStyleClass().add("input-label");

        TextField emailField = new TextField();
        emailField.getStyleClass().add("text-field");

        // Member ID (auto-generated, non-editable)
        Label memberIdLabel = new Label("Member ID:");
        memberIdLabel.getStyleClass().add("input-label");

        TextField memberIdField = new TextField();
        memberIdField.setEditable(false); // This will be automatically filled
        memberIdField.getStyleClass().add("text-field");

        // Register Button
        Button registerButton = new Button("Register Member");
        registerButton.getStyleClass().add("button");

        // Back to Home Button
        Button backButton = new Button("Back to Home");
        backButton.getStyleClass().add("button");

        // Register Button Action
        registerButton.setOnAction(e -> {
            String memberName = memberField.getText();
            String email = emailField.getText();
            if (!memberName.isEmpty() && !email.isEmpty()) {
                int memberId = registerMember(memberName, email);
                memberIdField.setText(String.valueOf(memberId)); // Display the numeric ID
                showAlert("Success", "Member registered with ID: " + memberId);
            } else {
                showAlert("Error", "Member name and email cannot be empty!");
            }
        });

        // Back Button Action
        backButton.setOnAction(e -> new LibraryManagementApp().showHomePage(primaryStage));

        layout.getChildren().addAll(memberLabel, memberField, emailLabel, emailField, memberIdLabel, memberIdField, registerButton, backButton);

        Scene scene = new Scene(layout, 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    private int registerMember(String memberName, String email) {
        // Generate a unique numeric ID (incremental)
        int memberId = memberIdCounter++;
        memberDatabase.put(memberId, memberName); // Store member in the database

        // Save the member information to a file
        saveMemberToFile(memberId, memberName, email);

        return memberId;
    }

    private void saveMemberToFile(int memberId, String memberName, String email) {
        try (FileWriter writer = new FileWriter("members.txt", true)) {
            writer.write("ID: " + memberId + ", Name: " + memberName + ", Email: " + email + "\n");
        } catch (IOException e) {
            showAlert("Error", "Failed to save member information to file.");
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
