package com.example.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPage2 extends Application {
    @Override
    public void start(Stage stage) {
        Label greetLabel = new Label("Welcome to my online shop!");
        Button greetButton = new Button("Go to online shop");

        VBox inputLayout = new VBox(10, greetLabel, greetButton);
        Scene inputScene = new Scene(inputLayout, 300, 150);
        stage.setScene(inputScene);
        stage.show();

        greetButton.setOnAction(e -> {
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
                } else {
                    messageLabel.setText("Not logged in");
                }
            });

            registerButton.setOnAction(el -> {
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

            GridPane grid = new GridPane();
            grid.setVgap(10);
            grid.setHgap(10);

            grid.add(userLabel, 0, 0);
            grid.add(usernameField, 1, 0);
            grid.add(passLabel, 0, 1);
            grid.add(passwordField, 1, 1);

            grid.add(loginButton, 0, 2);
            grid.add(registerButton, 1, 2);
            grid.add(messageLabel, 1, 3);

            Scene loginScene = new Scene(grid, 300, 200);
            stage.setScene(loginScene);
            stage.show();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
