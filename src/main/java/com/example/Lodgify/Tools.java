package com.example.Lodgify;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

public interface Tools {


    default <T> void switchScene(ActionEvent event, String fxmlFilePath, String title,T obj){
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            loader.setController(obj);


            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Optionally, set the title for the new scene
            stage.setTitle(title);

            // Show the new scene
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






     default void showAlert(String title, String message) {
        // Create an Alert of type INFORMATION
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);

        // Create a TextArea to display the message with scroll bar
        TextArea textArea = new TextArea(message);
        textArea.setWrapText(true); // Enable text wrapping
        textArea.setEditable(false); // Disable editing
        textArea.setMaxHeight(Region.USE_PREF_SIZE); // Adjust height based on content
        textArea.setPrefSize(450, 250); // Set a preferred size for the TextArea
        textArea.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-text-fill: #333333; -fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-padding: 10px;");

        // Style for the alert's content area (optional)
        alert.getDialogPane().setStyle("-fx-background-color: #ffffff; -fx-padding: 20px;");

        // Embed the TextArea into the Alert's dialog pane
        alert.getDialogPane().setContent(textArea);

        // Show the alert
        alert.showAndWait();
    }

}
