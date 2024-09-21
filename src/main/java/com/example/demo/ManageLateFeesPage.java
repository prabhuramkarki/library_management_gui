package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ManageLateFeesPage {

    private final Stage primaryStage;

    // Store members with their IDs
    private final Map<String, String> memberDatabase = new HashMap<>();

    public ManageLateFeesPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene createManageLateFeesScene() {
        VBox layout = new VBox(20);
        layout.getStyleClass().add("vbox");

        Label memberLabel = new Label("Member Name:");
        memberLabel.getStyleClass().add("input-label");

        TextField memberField = new TextField();
        memberField.getStyleClass().add("text-field");

        Label memberIdLabel = new Label("Member ID:");
        memberIdLabel.getStyleClass().add("input-label");

        TextField memberIdField = new TextField();
        memberIdField.setEditable(false); // This will be automatically filled
        memberIdField.getStyleClass().add("text-field");

        Button registerButton = new Button("Register Member");
        registerButton.getStyleClass().add("button");

        Label amountLabel = new Label("Late Fee Amount:");
        amountLabel.getStyleClass().add("input-label");

        TextField amountField = new TextField();
        amountField.getStyleClass().add("text-field");

        Button payButton = new Button("Pay Fee");
        payButton.getStyleClass().add("button");

        Button backButton = new Button("Back to Home");
        backButton.getStyleClass().add("button");

        registerButton.setOnAction(e -> {
            String memberName = memberField.getText();
            if (!memberName.isEmpty()) {
                String memberId = registerMember(memberName);
                memberIdField.setText(memberId);
                showAlert("Success", "Member registered with ID: " + memberId);
            } else {
                showAlert("Error", "Member name cannot be empty!");
            }
        });

        payButton.setOnAction(e -> payLateFee(memberField.getText(), memberIdField.getText(), amountField.getText()));
        backButton.setOnAction(e -> new LibraryManagementApp().showHomePage(primaryStage));

        layout.getChildren().addAll(memberLabel, memberField, memberIdLabel, memberIdField, registerButton, amountLabel, amountField, payButton, backButton);

        Scene scene = new Scene(layout, 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    private String registerMember(String memberName) {
        // Generate a unique ID for the member (can use UUID for more complex systems)
        String memberId = UUID.randomUUID().toString();
        memberDatabase.put(memberId, memberName); // Store member in the database
        return memberId;
    }

    private void payLateFee(String memberName, String memberId, String amount) {
        if (memberDatabase.containsKey(memberId) && memberDatabase.get(memberId).equals(memberName)) {
            // Implement late fee management logic (e.g., updating a database or file)
            showAlert("Success", "Fee of " + amount + " paid by " + memberName + " (ID: " + memberId + ").");
        } else {
            showAlert("Error", "Member ID and Name do not match.");
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
