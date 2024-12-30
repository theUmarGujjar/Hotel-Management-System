package com.example.Lodgify.Staff;

import com.example.Lodgify.Administration.Car;
import com.example.Lodgify.Administration.CarManager;
import com.example.Lodgify.Administration.Regex;
import com.example.Lodgify.HotelReceptionist.Booking;
import com.example.Lodgify.Tools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Optional;

public class  Driver <T> extends StaffFunctions implements Tools, Regex {

//    ui components

    private T previous;
    public void setPrevious(T previous){
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
        all.setOnAction(e -> allCarsData());
        one.setOnAction(e -> showDataForOne());
        update.setOnAction(e -> updateStatus());
    }

    @FXML
    public void allCarsData() {
        // Get the list of cars
        ArrayList<Car> carList = CarManager.getCarList();
        ObservableList<Car> carData = FXCollections.observableArrayList(carList);

        // Create a TableView
        TableView<Car> tableView = new TableView<>();

        // Define columns
        TableColumn<Car, Integer> idColumn = new TableColumn<>("Car ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Car, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Car, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        tableView.getColumns().addAll(idColumn, modelColumn, statusColumn);
        tableView.setItems(carData);

        // Create a dialog and set the TableView in its content
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("All Cars Data");

        VBox content = new VBox(tableView);
        dialog.getDialogPane().setContent(content);

        // Add OK button to close the dialog
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        dialog.showAndWait();
    }


    @Override
    @FXML
    public void showDataForOne() {
        // Dialog to get car ID
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Car Information");
        dialog.setHeaderText("Enter the Car ID");
        dialog.setContentText("Car ID:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return; // Exit if canceled
        }

        // Validate input
        int carId;
        try {
            carId = Integer.parseInt(result.get());
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid integer for Car ID.");
            return;
        }

        // Find the car with the given ID
        ArrayList<Car> carList = CarManager.carlist;
        ObservableList<Car> matchedCar = FXCollections.observableArrayList();

        for (Car car : carList) {
            if (carId == car.getId()) {
                matchedCar.add(car);
            }
        }

        if (matchedCar.isEmpty()) {
            showAlert("Car Not Found", "No car found with ID: " + carId);
            return;
        }

        // Create a TableView to display the matched car
        TableView<Car> tableView = new TableView<>();

        TableColumn<Car, Integer> idColumn = new TableColumn<>("Car ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Car, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Car, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        tableView.getColumns().addAll(idColumn, modelColumn, statusColumn);
        tableView.setItems(matchedCar);

        // Create a dialog and add the TableView
        Dialog<Void> dialogBox = new Dialog<>();
        dialogBox.setTitle("Car Details");
        VBox content = new VBox(tableView);
        dialogBox.getDialogPane().setContent(content);

        // Add OK button to close the dialog
        dialogBox.getDialogPane().getButtonTypes().add(ButtonType.OK);

        dialogBox.showAndWait();
    }


    @Override
    @FXML
    public void updateStatus() {
        // Dialog to get car ID
        TextInputDialog carIdDialog = new TextInputDialog();
        carIdDialog.setTitle("Update Car Status");
        carIdDialog.setHeaderText("Enter the Car ID");
        carIdDialog.setContentText("Car ID:");

        Optional<String> carIdResult = carIdDialog.showAndWait();
        if (carIdResult.isEmpty()) {
            return; // Exit if canceled
        }

        // Validate car ID input
        int carId;
        try {
            carId = Integer.parseInt(carIdResult.get());
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid integer for Car ID.");
            return;
        }

        // Find the car
        Car carToUpdate = null;
        for (Car car : CarManager.carlist) {
            if (carId == car.getId()) {
                carToUpdate = car;
                break;
            }
        }

        if (carToUpdate == null) {
            showAlert("Car Not Found", "No car found with ID: " + carId);
            return;
        }

        // Show current status and get new status
        TextInputDialog statusDialog = new TextInputDialog(carToUpdate.getAvailability());
        statusDialog.setTitle("Update Car Status");
        statusDialog.setHeaderText("Current Status: " + carToUpdate.getAvailability());
        statusDialog.setContentText("Enter new status:");

        // Add validation with event.consume
        Button okButton = (Button) statusDialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            String newAvailability = statusDialog.getEditor().getText().trim(); // Get input directly as a string
            if (!newAvailability.equals("booked") && !newAvailability.equals("unbooked")) {
                showAlert("Invalid Status", "Availability must be set to 'booked' or 'unbooked'.");
                event.consume(); // Prevent the dialog from closing
            }
        });

        Optional<String> statusResult = statusDialog.showAndWait();
        if (statusResult.isEmpty()) {
            return; // Exit if canceled
        }

        // If validation passes, update car status
        String newAvailability = statusResult.get().trim();
        carToUpdate.setAvailability(newAvailability);

        // Confirm update in a TableView
        ObservableList<Car> updatedCar = FXCollections.observableArrayList(carToUpdate);

        TableView<Car> tableView = new TableView<>();

        TableColumn<Car, Integer> idColumn = new TableColumn<>("Car ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Car, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Car, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        tableView.getColumns().addAll(idColumn, modelColumn, statusColumn);
        tableView.setItems(updatedCar);

        Dialog<Void> dialogBox = new Dialog<>();
        dialogBox.setTitle("Updated Car Status");
        VBox content = new VBox(tableView);
        dialogBox.getDialogPane().setContent(content);

        dialogBox.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialogBox.showAndWait();
    }


    @FXML
    public void exitToStaffPortal(ActionEvent event) {
        if (previous instanceof StaffPortal) {
            switchScene(event, "/com/example/lodgify/StaffPortal.fxml", "Restraunt Management System", previous);
        }
        else if (previous instanceof Booking) {
            switchScene(event,"/com/example/lodgify/ReceptionistPortal.fxml","Hotel Receptionist",previous);
        }
    }




}
