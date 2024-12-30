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


public class CarManager implements Regex, Tools {

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

    public static ArrayList<Car> carlist = new ArrayList<>();
    ObservableList<Car> carData = FXCollections.observableArrayList(carlist);

    public static ArrayList<Car> getCarList(){
        return carlist;
    }





    @FXML
    private void add() {
        // Create a custom dialog
        Dialog<Car> dialog = new Dialog<>();
        dialog.setTitle("Add Car");
        dialog.setHeaderText("Enter the details of the new car:");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Input fields for car details
        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField modelField = new TextField();
        modelField.setPromptText("Model");
        TextField plateField = new TextField();
        plateField.setPromptText("License Plate");
        TextField availabilityField = new TextField();
        availabilityField.setPromptText("Availability (booked/unbooked)");

        // Layout: GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Model:"), 0, 1);
        grid.add(modelField, 1, 1);
        grid.add(new Label("License Plate:"), 0, 2);
        grid.add(plateField, 1, 2);
        grid.add(new Label("Availability:"), 0, 3);
        grid.add(availabilityField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Add a custom validation logic
        final Button addButton = (Button) dialog.getDialogPane().lookupButton(addButtonType);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String model = modelField.getText();
                String plate = plateField.getText();
                String availability = availabilityField.getText().toLowerCase();

                // Validate availability
                if (!availability.equals("booked") && !availability.equals("unbooked")) {
                    showError("Availability must be set to 'booked' or 'unbooked'.");
                    event.consume(); // Prevent dialog from closing
                    return;//as in room
                }
                if (!model.matches(CarModel)) {
                    showError("car model must be alphabetic");
                    event.consume(); // Prevent dialog from closing
                    return;//as in room
                }
                if (!plate.matches(LPlateNumber)) {
                    showError("Lisence plate number must have 3 letters, a space, and 1 to 4 digits.");
                    event.consume(); // Prevent dialog from closing
                    return;//as in room
                }


                    for (Car existingCar : carlist) {
                        if (existingCar.getId() == id) {
                            event.consume(); // Prevent dialog from closing
                            showError("Car ID already exists.");
                            return;
                        }
                    }
                    // Add car if validation passes
                    carlist.add(new Car(id, model, plate, availability));
                    showInfo("Car added successfully.");

            } catch (NumberFormatException e) {
                event.consume(); // Prevent dialog from closing
                showError("ID must be a valid number.");
            }
        });

        dialog.showAndWait();
    }

    @FXML
    private void remove() {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Remove Car");
        dialog.setHeaderText("Enter the ID of the car to remove:");

        ButtonType removeButtonType = new ButtonType("Remove", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(removeButtonType, ButtonType.CANCEL);

        TextField idField = new TextField();
        idField.setPromptText("ID");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == removeButtonType) {
                try {
                    return Integer.parseInt(idField.getText());
                } catch (NumberFormatException e) {
                    showError("ID must be a number.");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(id -> {

            boolean removed = carlist.removeIf(car -> car.getId() == id);
            if (removed) {
                showInfo("Car removed successfully.");
            } else {
                showError("No car found with the given ID.");
            }
        });
    }

    @FXML
    private void update(){
        Dialog<Car> dialog = new Dialog<>();
        dialog.setTitle("Update Car");
        dialog.setHeaderText("Enter the ID of the car to update and its new details:");

        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        // Input fields
        TextField idField = new TextField();
        idField.setPromptText("ID (existing)");
        TextField modelField = new TextField();
        modelField.setPromptText("New Model");
        TextField plateField = new TextField();
        plateField.setPromptText("New License Plate");
        TextField availabilityField = new TextField();
        availabilityField.setPromptText("New Availability (booked/unbooked)");

        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("New Model:"), 0, 1);
        grid.add(modelField, 1, 1);
        grid.add(new Label("New License Plate:"), 0, 2);
        grid.add(plateField, 1, 2);
        grid.add(new Label("New Availability:"), 0, 3);
        grid.add(availabilityField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Pre-fill fields when a matching ID is entered
        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int id = Integer.parseInt(newValue); // Parse ID
                Car matchingCar = null;

                for (Car car : carlist) {
                    if (car.getId() == id) {
                        matchingCar = car;
                        break;
                    }
                }

                if (matchingCar != null) {
                    // Pre-fill fields with matching car details
                    modelField.setText(matchingCar.getModel());
                    plateField.setText(matchingCar.getL_plateno());
                    availabilityField.setText(matchingCar.getAvailability());
                } else {
                    // Clear fields if no car found
                    modelField.clear();
                    plateField.clear();
                    availabilityField.clear();
                }

            } catch (NumberFormatException e) {
                // Clear fields for invalid ID input
                modelField.clear();
                plateField.clear();
                availabilityField.clear();
            }
        });
        modelField.textProperty().addListener((observable, oldValue, newValue) -> {
            int id = Integer.parseInt(idField.getText());

            boolean carFound=false;
            for (Car car : carlist) {
                if (car.getId() == id) {

                    carFound=true;
                    break;
                }
            }
            if(!carFound){
                showInfo("No car is found with this id");

            }
        });


            dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                String idText = idField.getText().trim();
                String model = modelField.getText().trim();
                String plate = plateField.getText().trim();
                String availability = availabilityField.getText().trim();

                if (idText.isEmpty() || model.isEmpty() || plate.isEmpty() || availability.isEmpty()) {
                    System.out.println("Please fill in all fields.");
                    return null;
                }

                try {
                    int id = Integer.parseInt(idText);
                    return new Car(id, model, plate, availability);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID entered. Please enter a valid number.");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(updatedCar -> {
            for (Car car : carlist) {
                if (car.getId() == updatedCar.getId()) {
                    carlist.remove(car);
                    carlist.add(updatedCar);
                    showInfo("Car updated successfully.");
                    return;
                }

            }
        });
    }




    @FXML
    private void display() {
        carData.setAll(carlist);
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Car List");
        dialog.setHeaderText("List of Cars:");

//        // TableView setup
        TableView tableView = new TableView<>();

        TableColumn<Car, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Car, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Car, String> plateColumn = new TableColumn<>("Plate");
        plateColumn.setCellValueFactory(new PropertyValueFactory<>("l_plateno"));

        TableColumn<Car, String> availabilityColumn = new TableColumn<>("Availability");
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        tableView.getColumns().addAll(idColumn, modelColumn, plateColumn, availabilityColumn);

        // Load the car data into the TableView
//        ObservableList<Car> carData = FXCollections.observableArrayList(CarManager.carlist);
        tableView.setItems(carData);

        // Add Close button
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        // Set the content
        dialog.getDialogPane().setContent(new VBox(10, tableView));
        dialog.getDialogPane().setPrefSize(600, 400); // Optional: Adjust size for better visibility

        // Show the dialog
        dialog.showAndWait();

        // After the dialog is closed, refresh the data if needed (this is where changes in the carlist will reflect)
        carData.setAll(carlist);// Refresh TableView with the latest data from carlist
//        tableView.refresh();
    }

    @FXML
    public void ExitToAdminPortal(ActionEvent event){
        switchScene(event,"/com/example/lodgify/AdminPortal.fxml","Admin Portal",previous);
    }




    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
