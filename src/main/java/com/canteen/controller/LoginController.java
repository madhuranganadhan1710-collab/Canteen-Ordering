package com.canteen.controller;

import com.canteen.dao.UserDAO;
import com.canteen.app.AppSession;
import com.canteen.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLogin(javafx.event.ActionEvent event) {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both fields.");
            return;
        }

        User user = userDAO.fetchUser(username, password);

        if (user == null) {
            errorLabel.setText("Invalid username or password.");
            return;
        }

        AppSession.setUser(user);

        loadPage(event, "/fxml/menu.fxml");
    }

    @FXML
    private void goToSignup(javafx.event.ActionEvent event) {
        loadPage(event, "/fxml/signup.fxml");
    }

    private void loadPage(javafx.event.ActionEvent event, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
