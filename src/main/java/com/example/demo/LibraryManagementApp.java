package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibraryManagementApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        showHomePage(primaryStage);
    }

    public void showHomePage(Stage primaryStage) {
        VBox homeLayout = new VBox(20);
        homeLayout.getStyleClass().add("vbox");

        Button registerButton = new Button("Register Member");
        Button searchBooksButton = new Button("Search Books");
        Button manageLateFeesButton = new Button("Manage Late Fees");
        Button addBooksButton = new Button("Add Books");
        Button borrowBooksButton = new Button("Borrow Books");
        Button returnBooksButton = new Button("Return Books");

        registerButton.getStyleClass().add("button");
        searchBooksButton.getStyleClass().add("button");
        manageLateFeesButton.getStyleClass().add("button");
        addBooksButton.getStyleClass().add("button");
        borrowBooksButton.getStyleClass().add("button");
        returnBooksButton.getStyleClass().add("button");

        registerButton.setOnAction(e -> showRegisterMemberPage(primaryStage));
        searchBooksButton.setOnAction(e -> showSearchBooksPage(primaryStage));
        manageLateFeesButton.setOnAction(e -> showManageLateFeesPage(primaryStage));
        addBooksButton.setOnAction(e -> showAddBooksPage(primaryStage));
        borrowBooksButton.setOnAction(e -> showBorrowBooksPage(primaryStage));
        returnBooksButton.setOnAction(e -> showReturnBooksPage(primaryStage));

        homeLayout.getChildren().addAll(registerButton, searchBooksButton, manageLateFeesButton, addBooksButton, borrowBooksButton, returnBooksButton);

        Scene homeScene = new Scene(homeLayout, 600, 600);
        homeScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Library Management System");
        primaryStage.show();
    }

    public void showRegisterMemberPage(Stage primaryStage) {
        RegisterMemberPage registerPage = new RegisterMemberPage(primaryStage);
        Scene registerScene = registerPage.createRegisterMemberScene();
        primaryStage.setScene(registerScene);
    }

    public void showSearchBooksPage(Stage primaryStage) {
        SearchBooksPage searchPage = new SearchBooksPage(primaryStage);
        Scene searchScene = searchPage.createSearchBooksScene();
        primaryStage.setScene(searchScene);
    }

    public void showManageLateFeesPage(Stage primaryStage) {
        ManageLateFeesPage manageLateFeesPage = new ManageLateFeesPage(primaryStage);
        Scene manageLateFeesScene = manageLateFeesPage.createManageLateFeesScene();
        primaryStage.setScene(manageLateFeesScene);
    }

    public void showAddBooksPage(Stage primaryStage) {
        AddBooksPage addBooksPage = new AddBooksPage(primaryStage);
        Scene addBooksScene = addBooksPage.createAddBooksScene();
        primaryStage.setScene(addBooksScene);
    }

    public void showBorrowBooksPage(Stage primaryStage) {
        BorrowBooksPage borrowBooksPage = new BorrowBooksPage(primaryStage);
        Scene borrowBooksScene = borrowBooksPage.createBorrowBooksScene();
        primaryStage.setScene(borrowBooksScene);
    }

    public void showReturnBooksPage(Stage primaryStage) {
        ReturnBooksPage returnBooksPage = new ReturnBooksPage(primaryStage);
        Scene returnBooksScene = returnBooksPage.createReturnBooksScene();
        primaryStage.setScene(returnBooksScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}