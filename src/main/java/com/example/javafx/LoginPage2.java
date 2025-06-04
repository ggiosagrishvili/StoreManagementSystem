package com.example.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    @Override
    public void stop() {
        DatabaseConnection2.closeConnection();
    }

    private void showWelcomePage() {
        Label greetLabel = new Label("Welcome to my online shop!");
        greetLabel.getStyleClass().add("label");

        Button greetButton = new Button("Go to online shop");

        VBox layout = new VBox(15, greetLabel, greetButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        greetButton.setOnAction(e -> showLoginPage());

        Scene scene = new Scene(layout, 400, 200);
        scene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());

        stage.setTitle("Welcome");
        stage.setScene(scene);
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

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (DatabaseConnection2.validateUser(username, password)) {
                messageLabel.setText("Logged in successfully!");
                showShopPage();
            } else {
                messageLabel.setText("Incorrect username or password.");
            }
        });

        registerButton.setOnAction(e -> showRegisterPage());

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);

        grid.add(userLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 0, 2);
        grid.add(registerButton, 1, 2);
        grid.add(messageLabel, 1, 3);

        Scene scene = new Scene(grid, 400, 250);
        scene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());

        stage.setTitle("Login");
        stage.setScene(scene);
    }

    private void showRegisterPage() {
        Label userLabel = new Label("Username:");
        Label passLabel = new Label("Password:");
        Label messageLabel = new Label();

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button signUpButton = new Button("Sign up");
        Button backButton = new Button("Back");

        signUpButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please fill in all fields.");
                return;
            }

            if (DatabaseConnection2.registerUser(username, password)) {
                messageLabel.setText("Registered successfully!");
            } else {
                messageLabel.setText("Registration failed.");
            }
        });

        backButton.setOnAction(e -> showLoginPage());

        VBox vbox = new VBox(10, new Label("Create your account"),
                userLabel, usernameField, passLabel, passwordField,
                signUpButton, backButton, messageLabel);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 400, 300);
        scene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());

        stage.setTitle("Register");
        stage.setScene(scene);
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
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 400, 500);
        scene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());

        stage.setTitle("Shop");
        stage.setScene(scene);
    }

    private ComboBox<String> createComboBox(String prompt, String... items) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setPromptText(prompt);
        return comboBox;
    }

    private void addIfSelected(ComboBox<String> box) {
        if (box.getValue() != null) {
            selectedProducts.add(box.getValue());
        }
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
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 400, 300);
        scene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());

        stage.setTitle("Payment");
        stage.setScene(scene);
    }

    private void showSuccessPage() {
        Label successLabel = new Label("You have successfully purchased all products.");
        Button viewButton = new Button("View all bought products");
        viewButton.setOnAction(e -> showBoughtProducts());

        VBox vbox = new VBox(10, successLabel, viewButton);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 400, 200);
        scene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());

        stage.setTitle("Success");
        stage.setScene(scene);
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
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 400, 300);
        scene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());

        stage.setTitle("Bought Products");
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}
