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
        inputLayout.getStyleClass().add("center-box");

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

        ComboBox<String> drinkBox = createComboBox("Drink", "Cola", "Fanta", "Sprite");
        ComboBox<String> dairyBox = createComboBox("Dairy", "Milk", "Cheese", "Yogurt");
        ComboBox<String> snackBox = createComboBox("Snack", "Chips", "Nuts", "Crackers");
        ComboBox<String> juiceBox = createComboBox("Juice", "Apple", "Orange", "Grape");
        ComboBox<String> breadBox = createComboBox("Bread", "White", "Whole grain", "Baguette");
        ComboBox<String> cerealBox = createComboBox("Cereal", "Cornflakes", "Muesli", "Oats");

        Button buyButton = new Button("Buy Products");
        buyButton.setOnAction(e -> {
            addIfSelected(drinkBox);
            addIfSelected(dairyBox);
            addIfSelected(snackBox);
            addIfSelected(juiceBox);
            addIfSelected(breadBox);
            addIfSelected(cerealBox);
            showPaymentPage();
        });

        VBox vbox = new VBox(10, shopLabel, drinkBox, dairyBox, snackBox,
                juiceBox, breadBox, cerealBox, buyButton);
        vbox.setPadding(new Insets(20));

        Scene shopScene = new Scene(vbox, 400, 500);
        shopScene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());
        stage.setScene(shopScene);
    }

    private ComboBox<String> createComboBox(String prompt, String... items) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setPromptText(prompt);
        return comboBox;
    }

    private void addIfSelected(ComboBox<String> box) {
        if (box.getValue() != null) selectedProducts.add(box.getValue());
    }

    private void showPaymentPage() {
        Label label = new Label("Now you can buy all products!");

        ToggleGroup paymentGroup = new ToggleGroup();
        RadioButton cardBtn = new RadioButton("By Card");
        cardBtn.setToggleGroup(paymentGroup);
        RadioButton cashBtn = new RadioButton("By Cash");
        cashBtn.setToggleGroup(paymentGroup);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showShopPage());

        Button buyButton = new Button("Buy");
        buyButton.setOnAction(e -> showSuccessPage());

        VBox vbox = new VBox(10, label, cardBtn, cashBtn, backButton, buyButton);
        vbox.setPadding(new Insets(20));

        Scene paymentScene = new Scene(vbox, 400, 300);
        paymentScene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());
        stage.setScene(paymentScene);
    }

    private void showSuccessPage() {
        Label successLabel = new Label("You have successfully purchased all products.");
        Button viewButton = new Button("View all bought products");
        viewButton.setOnAction(e -> showBoughtProducts());

        VBox vbox = new VBox(10, successLabel, viewButton);
        vbox.setPadding(new Insets(20));

        Scene successScene = new Scene(vbox, 400, 200);
        successScene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());
        stage.setScene(successScene);
    }

    private void showBoughtProducts() {
        Label label = new Label("There are all your products:");
        StringBuilder productList = new StringBuilder();
        for (String product : selectedProducts) {
            productList.append("✔ ").append(product).append("\n");
        }

        TextArea productsArea = new TextArea(productList.toString());
        productsArea.setEditable(false);

        VBox vbox = new VBox(10, label, productsArea);
        vbox.setPadding(new Insets(20));

        Scene boughtScene = new Scene(vbox, 400, 300);
        boughtScene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());
        stage.setScene(boughtScene);
    }

    public static void main(String[] args) {
        launch();
    }
}