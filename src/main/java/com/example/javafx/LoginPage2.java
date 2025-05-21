package com.example.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class LoginPage2 extends Application {
    private Stage stage;
    private final List<String> selectedProducts = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        showWelcomePage();
    }

    private void showWelcomePage() {
        Label greetLabel = new Label("Welcome to my online shop!");
        Button greetButton = new Button("Go to online shop");

        VBox inputLayout = new VBox(10, greetLabel, greetButton);
        inputLayout.setPadding(new Insets(20));

        Scene inputScene = new Scene(inputLayout, 400, 200);
        inputScene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());

        greetButton.setOnAction(e -> showLoginPage());

        stage.setScene(inputScene);
        stage.show();
    }

    private void showLoginPage() {
        Label userLabel = new Label("Username:");
        Label passLabel = new Label("Password:");
        Label messageLabel = new Label();

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        loginButton.setOnAction(ev -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (DatabaseConnection.validateUser(username, password)) {
                messageLabel.setText("Logged in");
                showShopPage();
            } else {
                messageLabel.setText("Not logged in");
            }
        });

        registerButton.setOnAction(el -> showRegisterPage());

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        grid.add(userLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 0, 2);
        grid.add(registerButton, 1, 2);
        grid.add(messageLabel, 1, 3);

        Scene loginScene = new Scene(grid, 400, 250);
        loginScene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());
        stage.setScene(loginScene);
    }

    private void showRegisterPage() {
        Label userLabel = new Label("Username:");
        Label passLabel = new Label("Password:");
        Label messageLabel = new Label();

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button signUpButton = new Button("Sign up");
        Button backButton = new Button("Back");

        signUpButton.setOnAction(ev -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Fill all fields");
                return;
            }

            if (DatabaseConnection.registerUser(username, password)) {
                messageLabel.setText("Registered successfully");
            } else {
                messageLabel.setText("Registration failed");
            }
        });

        backButton.setOnAction(e -> showLoginPage());

        VBox vbox = new VBox(10, new Label("Create your account"),
                userLabel, usernameField, passLabel, passwordField,
                signUpButton, backButton, messageLabel);
        vbox.setPadding(new Insets(20));

        Scene registerScene = new Scene(vbox, 400, 300);
        registerScene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());
        stage.setScene(registerScene);
    }

    private void showShopPage() {
        selectedProducts.clear();

        Label shopLabel = new Label("Hello, this is your online shop!");

        ComboBox<String> drinkBox = new ComboBox<>();
        drinkBox.getItems().addAll("Coca Cola", "Fanta", "Sprite", "RC Cola", "Red Bull");
        drinkBox.setPromptText("Drink");

        ComboBox<String> dairyBox = new ComboBox<>();
        dairyBox.getItems().addAll("Milk", "Cheese", "Yogurt", "Cottage cheese");
        dairyBox.setPromptText("Dairy");

        ComboBox<String> snackBox = new ComboBox<>();
        snackBox.getItems().addAll("Chips", "Nuts", "Crackers", "Snickers");
        snackBox.setPromptText("Snack");

        ComboBox<String> juiceBox = new ComboBox<>();
        juiceBox.getItems().addAll("Apple", "Orange", "Grape", "Cherry");
        juiceBox.setPromptText("Juice");

        ComboBox<String> breadBox = new ComboBox<>();
        breadBox.getItems().addAll("White", "Whole grain", "Baguette");
        breadBox.setPromptText("Bread");

        ComboBox<String> cerealBox = new ComboBox<>();
        cerealBox.getItems().addAll("Cornflakes", "Muesli", "Oats", "Buckwheat");
        cerealBox.setPromptText("Cereal");

        Button buyButton = new Button("Buy Products");
        buyButton.setOnAction(e -> {
            selectedProducts.clear();
            if (drinkBox.getValue() != null) selectedProducts.add(drinkBox.getValue());
            if (dairyBox.getValue() != null) selectedProducts.add(dairyBox.getValue());
            if (snackBox.getValue() != null) selectedProducts.add(snackBox.getValue());
            if (juiceBox.getValue() != null) selectedProducts.add(juiceBox.getValue());
            if (breadBox.getValue() != null) selectedProducts.add(breadBox.getValue());
            if (cerealBox.getValue() != null) selectedProducts.add(cerealBox.getValue());

        });

        VBox vbox = new VBox(10, shopLabel, drinkBox, dairyBox, snackBox,
                juiceBox, breadBox, cerealBox, buyButton);
        vbox.setPadding(new Insets(20));

        Scene shopScene = new Scene(vbox, 400, 500);
        shopScene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());
        stage.setScene(shopScene);
    }

    public static void main(String[] args) {
        launch();
    }
}