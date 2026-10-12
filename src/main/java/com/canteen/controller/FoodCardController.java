package com.canteen.controller;

import com.canteen.model.MenuItem;
import com.canteen.util.CartManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class FoodCardController {

    @FXML private ImageView imageView;
    @FXML private Label nameLabel;
    @FXML private Label categoryLabel;
    @FXML private Label priceLabel;
    @FXML private Spinner<Integer> qtySpinner;
    @FXML private Button addBtn;

    private MenuItem item;

    @FXML
    public void initialize() {
        qtySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1)
        );
    }

    public void setData(MenuItem menuItem) {
        this.item = menuItem;

        nameLabel.setText(menuItem.getName());
        categoryLabel.setText(menuItem.getCategory());
        priceLabel.setText("₹ " + menuItem.getPrice().toPlainString());

        // Load image safely
        String imagePath = menuItem.getImagePath();

        if (imagePath == null || imagePath.isBlank()) {
            imageView.setImage(null);
            return;
        }

        java.net.URL imageUrl = getClass().getResource(imagePath);

        if (imageUrl == null) {
            System.out.println("Image not found: " + imagePath);
            imageView.setImage(null);
            return;
        }

        Image img = new Image(imageUrl.toExternalForm());
        imageView.setImage(img);
        imageView.setFitWidth(220);
        imageView.setFitHeight(140);
        imageView.setPreserveRatio(false);
        imageView.setSmooth(true);
    }

    @FXML
    private void addToCart() {
        if (item == null) {
            return;
        }

        int qty = qtySpinner.getValue();

        CartManager.addItem(
                item.getName(),
                item.getPrice().doubleValue(),
                qty
        );

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION,
                item.getName() + " × " + qty + " added to cart",
                ButtonType.OK
        );

        alert.setHeaderText(null);
        alert.showAndWait();
    }
}