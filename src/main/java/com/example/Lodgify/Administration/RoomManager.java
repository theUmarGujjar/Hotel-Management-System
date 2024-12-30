package com.example.Lodgify.Administration;

import com.example.Lodgify.Tools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class RoomManager implements Regex, Tools {

    //    UI COMPONENT
    private Admin previous;
    public void setPrevious(Admin previous){
        this.previous=previous;
    }


    @FXML
    public  Button addButton;
    @FXML
    public  Button removeButton;
    @FXML
    public  Button updateButton;
    @FXML
    public Button displayButton;

    // Assuming RoomManager has a static list of rooms
    public static ArrayList<Room> roomList = new ArrayList<>();
    @FXML
    // Add room method (similar to your car add method)
    private void add() {
        // Create a custom dialog
        Dialog<Room> dialog = new Dialog<>();
        dialog.setTitle("Add Room");
        dialog.setHeaderText("Enter room details:");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Input fields for room details
        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("Room Number");
        TextField roomPriceField = new TextField();
        roomPriceField.setPromptText("Room Price");
        TextField roomTypeField = new TextField();
        roomTypeField.setPromptText("Room Type");
        TextField numberOfBedsField = new TextField();
        numberOfBedsField.setPromptText("Number of Beds");
        TextField roomFloorField = new TextField();
        roomFloorField.setPromptText("Room Floor");
        TextField roomStatusField = new TextField();
        roomStatusField.setPromptText("Room Status");
        TextField roomAvailabilityField = new TextField();
        roomAvailabilityField.setPromptText("Room Availability (yes/no)");

        // Layout: GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Room Number:"), 0, 0);
        grid.add(roomNumberField, 1, 0);
        grid.add(new Label("Price:"), 0, 1);
        grid.add(roomPriceField, 1, 1);
        grid.add(new Label("Room Type:"), 0, 2);
        grid.add(roomTypeField, 1, 2);
        grid.add(new Label("Number of Beds:"), 0, 3);
        grid.add(numberOfBedsField, 1, 3);
        grid.add(new Label("Room Floor:"), 0, 4);
        grid.add(roomFloorField, 1, 4);
        grid.add(new Label("Room Status:"), 0, 5);
        grid.add(roomStatusField, 1, 5);
        grid.add(new Label("Room Availability:"), 0, 6);
        grid.add(roomAvailabilityField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Add custom validation logic
        final Button addButton = (Button) dialog.getDialogPane().lookupButton(addButtonType);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                int roomNumber = Integer.parseInt(roomNumberField.getText());
                int roomPrice = Integer.parseInt(roomPriceField.getText());
                String roomType = roomTypeField.getText();
                int numberOfBeds = Integer.parseInt(numberOfBedsField.getText());
                String roomFloor = roomFloorField.getText();
                String roomStatus = roomStatusField.getText();
                String roomAvailability = roomAvailabilityField.getText().toLowerCase();

                if (!roomFloor.matches(RoomFloor)) {
                    showError("Room floor must be alphabetic,numeric or alphanumeric");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!roomAvailability.equals("yes") && !roomAvailability.equals("no")) {
                    showError("Availability must be 'yes' or 'no'.");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!roomStatus.equals("clean") && !roomStatus.equals("dirty")) {
                    showError("Room status must be 'clean' or 'dirty'.");
                    event.consume(); // Prevent dialog from closing
                    return;
                }

                if (!roomType.matches(RoomType)) {
                    showError("Room type must be alphabetic");
                    event.consume(); // Prevent dialog from closing
                    return;
                }

                // Check for duplicate room numbers
                for (Room existingRoom : roomList) {
                    if (existingRoom.getRNumber() == roomNumber) {
                        showError("Room number already exists.");
                        event.consume(); // Prevent dialog from closing
                        return;
                    }
                }

                // Add room if validation passes
                roomList.add(new Room(roomNumber, roomPrice, roomType, numberOfBeds, roomStatus,roomFloor, roomAvailability));
                showInfo("Room added successfully.");
            } catch (NumberFormatException e) {
                showError("Please enter valid numerical values for room number, price, number of beds, and floor.");
                event.consume(); // Prevent dialog from closing
            }
        });

        dialog.showAndWait();
    }

    @FXML
    private void remove() {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Remove Room");
        dialog.setHeaderText("Enter the Room Number to remove:");

        ButtonType removeButtonType = new ButtonType("Remove", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(removeButtonType, ButtonType.CANCEL);

        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("Room Number");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Room Number:"), 0, 0);
        grid.add(roomNumberField, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == removeButtonType) {
                try {
                    return Integer.parseInt(roomNumberField.getText());
                } catch (NumberFormatException e) {
                    showError("Room Number must be a number.");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(roomNumber -> {

            boolean removed = roomList.removeIf(room -> room.getRNumber() == roomNumber);
            if (removed) {
                showInfo("Room removed successfully.");
            } else {
                showError("No room found with the given Room Number.");
            }
        });
    }


    // Update room method (updated to use dialog approach)
    @FXML
    private void update() {
        Dialog<Room> dialog = new Dialog<>();
        dialog.setTitle("Update Room");
        dialog.setHeaderText("Enter room number and new details:");

        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        // Input fields for room details
        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("Room Number (existing)");
        TextField roomPriceField = new TextField();
        roomPriceField.setPromptText("New Price");
        TextField roomTypeField = new TextField();
        roomTypeField.setPromptText("New Type");
        TextField numberOfBedsField = new TextField();
        numberOfBedsField.setPromptText("New Number of Beds");
        TextField roomFloorField = new TextField();
        roomFloorField.setPromptText("New Floor");
        TextField roomStatusField = new TextField();
        roomStatusField.setPromptText("New Status");
        TextField roomAvailabilityField = new TextField();
        roomAvailabilityField.setPromptText("New Availability (yes/no)");

        // Layout: GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Room Number:"), 0, 0);
        grid.add(roomNumberField, 1, 0);
        grid.add(new Label("New Price:"), 0, 1);
        grid.add(roomPriceField, 1, 1);
        grid.add(new Label("New Type:"), 0, 2);
        grid.add(roomTypeField, 1, 2);
        grid.add(new Label("New Number of Beds:"), 0, 3);
        grid.add(numberOfBedsField, 1, 3);
        grid.add(new Label("New Floor:"), 0, 4);
        grid.add(roomFloorField, 1, 4);
        grid.add(new Label("New Status:"), 0, 5);
        grid.add(roomStatusField, 1, 5);
        grid.add(new Label("New Availability:"), 0, 6);
        grid.add(roomAvailabilityField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Fetch room details to pre-fill the fields when room number is entered
        roomNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int roomNumber = Integer.parseInt(newValue);  // Check if entered value is a number
                Room roomToUpdate = null;
                for (Room room : roomList) {
                    if (room.getRNumber() == roomNumber) {
                        roomToUpdate = room;
                        break;
                    }
                }
                if (roomToUpdate != null) {
                    // Pre-fill the fields with the existing room details
                    roomPriceField.setText(String.valueOf(roomToUpdate.getRPrice()));
                    roomTypeField.setText(roomToUpdate.getRType());
                    numberOfBedsField.setText(String.valueOf(roomToUpdate.getRBeds()));
                    roomFloorField.setText(roomToUpdate.getRFloor());
                    roomStatusField.setText(roomToUpdate.getRStatus());
                    roomAvailabilityField.setText(roomToUpdate.getRAvailability());
                } else {
                    // Clear fields if no room found
                    roomPriceField.clear();
                    roomTypeField.clear();
                    numberOfBedsField.clear();
                    roomFloorField.clear();
                    roomStatusField.clear();
                    roomAvailabilityField.clear();
                }
            } catch (NumberFormatException ex) {
                // Clear fields when invalid room number is entered
                roomPriceField.clear();
                roomTypeField.clear();
                numberOfBedsField.clear();
                roomFloorField.clear();
                roomStatusField.clear();
                roomAvailabilityField.clear();
            }
        });
        roomPriceField.textProperty().addListener((observable, oldValue, newValue) -> {
            int roomNumber = Integer.parseInt(roomNumberField.getText());  // Check if entered value is a number
        boolean roomFound=false;
            for (Room room : roomList) {
                if (room.getRNumber() == roomNumber) {
                    roomFound=true;
                    break;
                }
            }
            if(!roomFound){
                showError("No room with this room number");
            }
        });

        // Add validation logic
        final Button updateButton = (Button) dialog.getDialogPane().lookupButton(updateButtonType);
        updateButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                int roomNumber = Integer.parseInt(roomNumberField.getText());
                int roomPrice = Integer.parseInt(roomPriceField.getText());
                String roomType = roomTypeField.getText();
                int numberOfBeds = Integer.parseInt(numberOfBedsField.getText());
                String roomFloor = roomFloorField.getText();
                String roomStatus = roomStatusField.getText();
                String roomAvailability = roomAvailabilityField.getText().toLowerCase();

                // Validate availability
                if (!roomFloor.matches(RoomFloor)) {
                    showError("Room floor must be alphabetic, numeric, or alphanumeric.");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!roomAvailability.equals("yes") && !roomAvailability.equals("no")) {
                    showError("Availability must be 'yes' or 'no'.");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!roomStatus.equals("clean") && !roomStatus.equals("dirty")) {
                    showError("Room status must be 'clean' or 'dirty'.");
                    event.consume(); // Prevent dialog from closing
                    return;
                }

                if (!roomType.matches(RoomType)) {
                    showError("Room type must be alphabetic.");
                    event.consume(); // Prevent dialog from closing
                    return;
                }

                // Find the room to update
                Room roomToUpdate = null;
                for (Room room : roomList) {
                    if (room.getRNumber() == roomNumber) {
                        roomList.remove(room);
                        roomList.add(new Room(roomNumber, roomPrice, roomType, numberOfBeds, roomStatus,roomFloor, roomAvailability));
                        roomToUpdate = room;
                        break;
                    }
                }

                if (roomToUpdate == null) {
                    showError("No room found with the given room number.");
                    event.consume(); // Prevent dialog from closing
                    return;
                }

                showInfo("Room updated successfully.");
            } catch (NumberFormatException e) {
                showError("Please enter valid numerical values for room number, price, and number of beds.");
                event.consume(); // Prevent dialog from closing
            }
        });

        dialog.showAndWait();
    }





    // Display room list method
    @FXML
    private void display() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Room List");
        dialog.setHeaderText("List of Rooms:");

        TableView<Room> tableView = new TableView<>();

        // Define columns for Room properties
        TableColumn<Room, Integer> roomNumberColumn = new TableColumn<>("Room Number");
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rNumber"));

        TableColumn<Room, Integer> roomPriceColumn = new TableColumn<>("Price");
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("rPrice"));

        TableColumn<Room, String> roomTypeColumn = new TableColumn<>("Room Type");
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("rType"));

        TableColumn<Room, Integer> numberOfBedsColumn = new TableColumn<>("Number of Beds");
        numberOfBedsColumn.setCellValueFactory(new PropertyValueFactory<>("rBeds"));

        TableColumn<Room, String> roomFloorColumn = new TableColumn<>("Floor");
        roomFloorColumn.setCellValueFactory(new PropertyValueFactory<>("rFloor"));

        TableColumn<Room, String> roomStatusColumn = new TableColumn<>("Status");
        roomStatusColumn.setCellValueFactory(new PropertyValueFactory<>("rStatus"));

        TableColumn<Room, String> roomAvailabilityColumn = new TableColumn<>("Availability");
        roomAvailabilityColumn.setCellValueFactory(new PropertyValueFactory<>("rAvailability"));



//         Add columns to the table
        tableView.getColumns().addAll(
                roomNumberColumn,
                roomPriceColumn,
                roomTypeColumn,
                numberOfBedsColumn,
                roomFloorColumn,
                roomStatusColumn,
                roomAvailabilityColumn
        );

        // Use RoomManager.roomList to populate data

        ObservableList<Room> roomObservableList = FXCollections.observableArrayList(roomList);
        tableView.setItems(roomObservableList);

        // Add the table to a VBox and set it in the dialog
        VBox vBox = new VBox(tableView);
        dialog.getDialogPane().setContent(vBox);

        // Add a close button to the dialog
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();

    }
    @FXML
    public void ExitToAdminPortal(ActionEvent event){
        switchScene(event,"/com/example/lodgify/AdminPortal.fxml","Admin Portal",previous);
    }

    // Utility method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Utility method to show info messages
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
