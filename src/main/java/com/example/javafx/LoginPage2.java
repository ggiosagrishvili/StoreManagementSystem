package com.example.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginPage2 extends Application {
    private Stage stage;

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
                messageLabel.setText("Logged in successfully.");
            } else {
                messageLabel.setText("Login failed.");
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
                messageLabel.setText("Fill all fields.");
                return;
            }

            if (DatabaseConnection.registerUser(username, password)) {
                messageLabel.setText("Registered successfully.");
            } else {
                messageLabel.setText("Registration failed.");
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

    public static void main(String[] args) {
        launch();
    }
}
