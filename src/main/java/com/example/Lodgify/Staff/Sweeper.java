package com.example.Lodgify.Staff;

//import com.example.lodgify.HotelReceptionist.Booking;

import com.example.Lodgify.Administration.Room;
import com.example.Lodgify.HotelReceptionist.Booking;
import com.example.Lodgify.Tools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TooltipSkin;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class Sweeper extends StaffFunctions implements Tools {

//    ui components


    private StaffPortal previous;
    public void setPrevious(StaffPortal previous){
        this.previous=previous;
    }

    @FXML
    private Button all;
    @FXML
    private Button one;
    @FXML
    private  Button update;


    @FXML
    public void initialize(){
        all.setOnAction(e -> checkStatusOfAllRooms());
        one.setOnAction(e -> showDataForOne());
        update.setOnAction(e -> updateStatus());
    }


    @FXML
    public void checkStatusOfAllRooms() {
        // Create an ObservableList to store Room status data
        ObservableList<Room> roomStatusList = FXCollections.observableArrayList();

        // Fill the ObservableList with Room status data
        for (int i = 0; i < Booking.rLs1.size(); i++) {
            int rNumber = Booking.rLs1.get(i).getRNumber();
            String rStatus = Booking.rLs1.get(i).getRStatus();
            roomStatusList.add(new Room(rNumber, rStatus)); // Use Property Constructor
        }

        // Create a TableView to display the room status
        TableView<Room> tableView = new TableView<>(roomStatusList);

        // Define columns for the TableView using PropertyValueFactory
        TableColumn<Room, Integer> roomNumberColumn = new TableColumn<>("Room Number");
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rNumber")); // Use PropertyValueFactory

        TableColumn<Room, String> hygienicStatusColumn = new TableColumn<>("Hygienic Status");
        hygienicStatusColumn.setCellValueFactory(new PropertyValueFactory<>("rStatus")); // Use PropertyValueFactory

        // Add columns to the TableView
        tableView.getColumns().addAll(roomNumberColumn, hygienicStatusColumn);

        // Create a dialog box
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Room Hygienic Status");

        // Create a VBox to hold the TableView
        VBox content = new VBox(tableView);
        content.setSpacing(10);
        dialog.getDialogPane().setContent(content);

        // Add a button to close the dialog
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        // Show the dialog
        dialog.showAndWait();
    }
    //overriding abstract method for showing a specific room status

    @Override
    @FXML
    public void showDataForOne() {
        // First dialog for entering the room number
        Dialog<Integer> roomNumberDialog = new Dialog<>();
        roomNumberDialog.setTitle("Check Room Status");
        roomNumberDialog.setHeaderText("Enter the Room Number to Check:");

        // Create a layout for the first dialog
        VBox firstDialogContent = new VBox(10);
        firstDialogContent.setPadding(new Insets(10));

        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("Enter Room Number");

        firstDialogContent.getChildren().addAll(new Label("Room Number:"), roomNumberField);
        roomNumberDialog.getDialogPane().setContent(firstDialogContent);

        // Add buttons to the first dialog
        ButtonType checkButton = new ButtonType("Check", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = ButtonType.CANCEL;
        roomNumberDialog.getDialogPane().getButtonTypes().addAll(checkButton, cancelButton);

        // Result converter for the first dialog
        roomNumberDialog.setResultConverter(dialogButton -> {
            if (dialogButton == checkButton) {
                try {
                    return Integer.parseInt(roomNumberField.getText().trim());
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid integer for the room number.");
                    return null;
                }
            }
            return null;
        });

        // Show the first dialog
        Optional<Integer> result = roomNumberDialog.showAndWait();
        result.ifPresent(roomNumber -> {
            boolean roomFound = false;

            // Check for the room in the list
            for (Room room : Booking.rLs1) {
                if (room.getRNumber() == roomNumber) {
                    roomFound = true;

                    // Open second dialog with room status
                    Dialog<Void> statusDialog = new Dialog<>();
                    statusDialog.setTitle("Room Status");
                    statusDialog.setHeaderText("Room Number: " + roomNumber);

                    VBox statusContent = new VBox(10);
                    statusContent.setPadding(new Insets(20));

                    TextField statusField = new TextField(room.getRStatus());
                    statusField.setEditable(false); // Make status field non-editable
                    statusField.setPrefWidth(300);

                    statusContent.getChildren().addAll(new Label("Room Status:"), statusField);
                    statusDialog.getDialogPane().setContent(statusContent);

                    ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
                    statusDialog.getDialogPane().getButtonTypes().add(closeButton);

                    statusDialog.showAndWait();
                    break;
                }
            }

            // If room is not found
            if (!roomFound) {
                showAlert("Room Not Found", "There is no room with number " + roomNumber + ".");
            }
        });
    }


    //overriding abstract method to update room status

    @Override
    @FXML
    public void updateStatus() {
        // Create a dialog box for updating the hygienic status
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Update Hygienic Status");

        // Create a VBox to hold content
        VBox content = new VBox();
        content.setSpacing(10);

        // Text Field for entering the room number
        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("Enter Room Number");

        // Text Field for entering the new hygienic status
        TextField newStatusField = new TextField();
        newStatusField.setPromptText("Enter New Hygienic Status");

        // Label to display the current status of the room
        Label currentStatusLabel = new Label("Current Status: Not Found");

        // Add fields to the content
        content.getChildren().addAll(new Label("Room Number:"), roomNumberField,
                currentStatusLabel, new Label("New Hygienic Status:"), newStatusField);

        // Set content to the dialog
        dialog.getDialogPane().setContent(content);

        // Add buttons for confirming or cancelling the action
        ButtonType updateButton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = ButtonType.CANCEL;
        dialog.getDialogPane().getButtonTypes().addAll(updateButton, cancelButton);

        // Listen for changes in the room number field to pre-fill current status
        roomNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int number = Integer.parseInt(newValue);
                boolean found = false;

                for (Room room : Booking.rLs1) {
                    if (room.getRNumber() == number) {
                        currentStatusLabel.setText("Current Status: " + room.getRStatus());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    currentStatusLabel.setText("Current Status: Not Found");
                }
            } catch (NumberFormatException e) {
                currentStatusLabel.setText("Current Status: Invalid Input");
            }
        });

        // Get the Update button and add an event filter for validation
        Node updateButtonNode = dialog.getDialogPane().lookupButton(updateButton);
        updateButtonNode.addEventFilter(ActionEvent.ACTION, event -> {
            int number;
            try {
                number = Integer.parseInt(roomNumberField.getText()); // Try parsing room number
            } catch (NumberFormatException e) {
                showError("Invalid Input", "Please enter a valid integer for room number.");
                event.consume(); // Prevent dialog from closing
                return;
            }

            boolean found = false;

            // Loop through room list and find the room to update
            for (Room room : Booking.rLs1) {
                if (room.getRNumber() == number) {
                    String newStatus = newStatusField.getText().trim();
                    if (newStatus.isEmpty()) {
                        showError("Empty Status", "Please enter a new hygienic status.");
                        event.consume(); // Prevent dialog from closing
                        return;
                    }

                    // Validate the new status
                    if (!newStatus.equals("clean") && !newStatus.equals("dirty")) {
                        showError("Invalid Status", "Room status must be 'clean' or 'dirty'.");
                        event.consume(); // Prevent dialog from closing
                        return;
                    }

                    room.setrStatus(newStatus);
                    found = true;
                    showInfo("Hygienic status updated successfully!");
                    return; // Allow dialog to close
                }
            }

            if (!found) {
                showError("Room Not Found", "No room found with the given number.");
                event.consume(); // Prevent dialog from closing
            }
        });

        // Show the dialog box
        dialog.showAndWait();
    }




    @FXML
    public void exitToStaffPortal(ActionEvent event) {
        switchScene(event,"/com/example/lodgify/StaffPortal.fxml","Service Staff Dashboard",previous);
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to display an error message
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to display alerts
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}