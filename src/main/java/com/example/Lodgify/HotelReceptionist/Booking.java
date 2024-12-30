package com.example.Lodgify.HotelReceptionist;

import com.example.Lodgify.Administration.Regex;
import com.example.Lodgify.Administration.Room;
import com.example.Lodgify.Administration.RoomManager;
import com.example.Lodgify.Staff.Driver;
import com.example.Lodgify.Dashboard;
import com.example.Lodgify.Tools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.Optional;

import static com.example.Lodgify.Administration.EmployeeManager.driverList;
import static com.example.Lodgify.Dashboard.showLoginDialog;
import static com.example.Lodgify.Staff.StaffPortal.driver;

public class Booking implements Tools, Regex {

    //   UI COMPONOENTS
    private Dashboard previous;
    public void setPrevious(Dashboard previous){
        this.previous=previous;
    }

    @FXML
    private Button available;
    @FXML
    private Button roomBook;
    @FXML
    private Button checkout;
    @FXML
    private Button viewBook;
    @FXML
    private Button updateBook;
    @FXML
    private  Button viewHygienic;
    @FXML
    private  Button updateHygienic;
    @FXML
    private  Button carBook;


    //define arraylist Room class..
    public static ArrayList<Room> rLs1 = RoomManager.roomList; // arraylist of allRooms
    public static ArrayList<Room> rAvailable = new ArrayList<>(); // array list of availableRooms
    public static ArrayList<Room> rBook = new ArrayList<>(); // array list of bookedRooms

    //define arraylist of person class
    public static ArrayList<PersonWhoBook> database = new ArrayList<>(); //arraylist of all persons
    public static ArrayList<PersonWhoBook> pBook = new ArrayList<>(); //arraylist of person who booked tRoom.

    @FXML
    public void initialize() {
        // Event handling for buttons
        available.setOnAction(e -> showAvailableRooms());
        roomBook.setOnAction(e -> bookRoom());
        viewBook.setOnAction(e -> viewBookedRooms());
        updateBook.setOnAction(e -> updateCustomerDetails());
        viewHygienic.setOnAction(e -> checkHygienicStatus());
        updateHygienic.setOnAction(e -> updateHygienicStatus());
        checkout.setOnAction(e -> checkOut());
        loadArraylists();
    }


    public void loadArraylists(){
        rAvailable.clear();
        // Filter available rooms
        for (Room room : rLs1) {
            if (room.getRAvailability().equalsIgnoreCase("yes")) { // Ensure case-insensitivity
                rAvailable.add(room);
            }
        }
        //testing purposes rBook removed
    }

    @FXML
    public void showAvailableRooms() {
        // Convert ArrayList to ObservableList
        ObservableList<Room> availableRooms = FXCollections.observableArrayList(rAvailable);

        // Create a TableView
        TableView<Room> tableView = new TableView<>(availableRooms);

        // Define columns for the TableView
        TableColumn<Room, String> roomIdColumn = new TableColumn<>("Room ID");
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("rNumber"));
        roomIdColumn.setPrefWidth(100);

        TableColumn<Room, String> roomTypeColumn = new TableColumn<>("Room Type");
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("rType"));
        roomTypeColumn.setPrefWidth(150);

        TableColumn<Room, String> roomStatusColumn = new TableColumn<>("Status");
        roomStatusColumn.setCellValueFactory(new PropertyValueFactory<>("rStatus"));
        roomStatusColumn.setPrefWidth(100);

        // Add columns to the TableView
        tableView.getColumns().addAll(roomIdColumn, roomTypeColumn, roomStatusColumn);

        tableView.setPrefHeight(200); // Adjust height to display a reasonable number of rows
        tableView.setPrefWidth(400); // Adjust width to match column widths


        // Create a dialog box
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Available Rooms");

        // Create a VBox to hold the TableView
        VBox content = new VBox(tableView);
        content.setSpacing(10);
        content.setStyle("-fx-padding: 20; -fx-alignment: center;");
        dialog.getDialogPane().setContent(content);

        // Add a button to close the dialog
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        // Show the dialog
        dialog.showAndWait();
    }


    @FXML
    public void bookRoom() {
        // Create a dialog to input room number
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Room Booking");

        // Create a TextField for room number input
        Label roomNumberLabel = new Label("Enter the Room number you want to book: ");
        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("Room number");

        // Create a button for submitting the input
        ButtonType submitButtonType = new ButtonType("Book Room", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(submitButtonType);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);


        // Layout the content of the dialog
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(roomNumberLabel, roomNumberField);
        dialog.getDialogPane().setContent(vbox);




        // Handle the dialog result when "Book Room" is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                return Integer.parseInt(roomNumberField.getText());
            }
            return null;
        });

        // Show the dialog and wait for input
        dialog.showAndWait().ifPresent(rNumber -> {
            boolean found = false;
            boolean cancelButtonNotClicked = false;

            if (rAvailable.size() == 0) {
                showInfo("No rooms are available in the database.");
            } else {
                // Backend code to book the room
                for (int i = 0; i < rAvailable.size(); i++) {
                    if (rAvailable.get(i).getRNumber() == rNumber) {
                        PersonWhoBook p = new PersonWhoBook(rNumber);
                        if(p.getRNumber()!= 0 && p.getCnic() != null){
                            rBook.add(rAvailable.get(i));
                            pBook.add(p);
                            cancelButtonNotClicked = true;
                        }

                        found = true;
                        break;
                    }
                }

                if(cancelButtonNotClicked){
                    // Remove room from available rooms and update availability
                    for (int i = 0; i < rAvailable.size(); i++) {
                        if (rAvailable.get(i).getRNumber() == rNumber) {
                            rAvailable.remove(i);
                            break;
                        }
                    }

                    for (int i = 0; i < rLs1.size(); i++) {
                        if (rLs1.get(i).getRNumber() == rNumber) {
                            rLs1.get(i).setrAvailability("book");
                            break;
                        }
                    }
                }





                // Display success or failure message
                // agr kamra found hogaya to found true ho jaia gaa or cancel button dabaya huaa to
                // cancelButtonNOtClicked false hi raha ga or dusra else if chal jaia gaa.
                if (!found) {
                    showInfo("This Room is not available.");
                }
                else if(!cancelButtonNotClicked){
                    showInfo("Booking canceled");
                } else {
                    showInfo("Room booked successfully.");
                }
            }
        });
    }



    @FXML
    public void viewBookedRooms() {
        // If no rooms are booked, show an alert dialog
        if (rBook.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Rooms Booked");
            alert.setHeaderText("Sorry");
            alert.setContentText("None of the rooms are booked.");
            alert.showAndWait();
        } else {
            // Create a dialog for viewing booked rooms
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Booked Rooms Details");

            // Set the dialog's preferred width and height
            dialog.getDialogPane().setMinWidth(800);
            dialog.getDialogPane().setMinHeight(600);

            // Create a layout for the dialog content
            VBox layout = new VBox(20);
            layout.setStyle("-fx-padding: 10;");
            layout.setPrefSize(780, 580); // Set size for the VBox

            // Create a TableView to display booked rooms and customer details
            TableView<PersonWhoBook> bookedRoomTable = new TableView<>();

            // Set the preferred size of the TableView
            bookedRoomTable.setPrefWidth(750);
            bookedRoomTable.setPrefHeight(550);

            // Define columns for room details
            TableColumn<PersonWhoBook, Integer> roomNumberColumn = new TableColumn<>("Room Number");
            TableColumn<PersonWhoBook, String> customerNameColumn = new TableColumn<>("Customer Name");
            TableColumn<PersonWhoBook, String> cnicColumn = new TableColumn<>("CNIC");
            TableColumn<PersonWhoBook, String> gmailColumn = new TableColumn<>("Gmail");
            TableColumn<PersonWhoBook, String> phoneNumColumn = new TableColumn<>("Phone Number");

            // Set the columns to show room and customer details
            roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rNumber"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            cnicColumn.setCellValueFactory(new PropertyValueFactory<>("cnic"));
            gmailColumn.setCellValueFactory(new PropertyValueFactory<>("gmail"));
            phoneNumColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

            if(pBook.size()==0){
                roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Unavailable"));
            }
            //for just testing...

            // Set preferred widths for columns
            roomNumberColumn.setPrefWidth(150);
            customerNameColumn.setPrefWidth(200);
            cnicColumn.setPrefWidth(150);
            gmailColumn.setPrefWidth(150);
            phoneNumColumn.setPrefWidth(150);

            // Add columns to the TableView
            bookedRoomTable.getColumns().addAll(roomNumberColumn, customerNameColumn, cnicColumn, phoneNumColumn, gmailColumn);

            // Create a custom object for displaying the room details
            ObservableList<PersonWhoBook> bookedRooms = FXCollections.observableArrayList(pBook);
            bookedRoomTable.setItems(bookedRooms);

            // Add the TableView to the layout
            layout.getChildren().add(bookedRoomTable);

            // Set the dialog content
            dialog.getDialogPane().setContent(layout);

            // Add OK button to close the dialog
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(okButton);

            // Show the dialog
            dialog.showAndWait();
        }
    }



    // Function to Check out from the room...
    @FXML
    public void checkOut() {
        // Create a Dialog for Checkout
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Checkout");

        // Create the layout
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Create a TableView to display booked rooms
        TableView<PersonWhoBook> bookedRoomTable = new TableView<>();
        TableColumn<PersonWhoBook, Integer> roomNumberColumn = new TableColumn<>("Room Number");
        TableColumn<PersonWhoBook, String> customerNameColumn = new TableColumn<>("Customer Name");

        // Adjust column widths
        roomNumberColumn.setPrefWidth(150); // Adjust as per content
        customerNameColumn.setPrefWidth(250); // Adjust as per content

        // Set the columns
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rNumber"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        bookedRoomTable.getColumns().addAll(roomNumberColumn, customerNameColumn);

        // Populate the table with booked rooms
        ObservableList<PersonWhoBook> bookedRooms = FXCollections.observableArrayList(pBook);
        bookedRoomTable.setItems(bookedRooms);

        // Adjust table size
        bookedRoomTable.setPrefHeight(200); // Adjust to display a reasonable number of rows
        bookedRoomTable.setPrefWidth(400); // Adjust to match column widths

        // Create a TextField for the room number input
        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("Enter Room Number to Checkout");

        // Add cancel and checkout buttons to dialog
        ButtonType checkOutButtonType = new ButtonType("Checkout", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = ButtonType.CANCEL;
        dialog.getDialogPane().getButtonTypes().addAll(checkOutButtonType, cancelButtonType);

        // Handle checkout action
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == checkOutButtonType) {
                try {
                    int roomNumber = Integer.parseInt(roomNumberField.getText());
                    boolean roomFound = false;

                    // Perform checkout logic
                    for (int i = 0; i < rBook.size(); i++) {
                        if (rBook.get(i).getRNumber() == roomNumber) {
                            rBook.remove(i); // Remove from booked rooms
                            pBook.remove(i); // Remove the customer from pBook
                            roomFound = true;

                            // Update room availability
                            for (Room room : rLs1) {
                                if (room.getRNumber() == roomNumber) {
                                    room.setrAvailability("yes"); // Set availability to "yes"
                                    rAvailable.add(room); // Add to available rooms
                                }
                            }

                            // Show success alert
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Checkout Successful");
                            successAlert.setHeaderText(null);
                            successAlert.setContentText("Room " + roomNumber + " has been checked out successfully!");
                            successAlert.showAndWait();
                            break;
                        }
                    }

                    // Show error if room is not found
                    if (!roomFound) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Room Not Found");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Room number " + roomNumber + " was not booked.");
                        errorAlert.showAndWait();
                    }
                } catch (NumberFormatException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Invalid Input");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Please enter a valid room number.");
                    errorAlert.showAndWait();
                }
            }
            return null;
        });

        // Set the dialog content
        layout.getChildren().addAll(bookedRoomTable, roomNumberField);
        dialog.getDialogPane().setContent(layout);

        // Show the dialog box
        dialog.showAndWait();
    }


    //Function to Update the Customer Details..
    @FXML
    public void updateCustomerDetails() {
        // Create a dialog for updating customer details
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Update Customer Details");

        // Create VBox for dialog content
        VBox content = new VBox();
        dialog.getDialogPane().setPrefSize(600, 400); // Width = 600, Height = 400

        content.setSpacing(10);

        // Create a ComboBox (dropdown) for selecting the update method
        ComboBox<String> updateOptions = new ComboBox<>();
        updateOptions.getItems().addAll("Update by Room Number", "Update by CNIC");

        // Set a default value for the ComboBox
        updateOptions.setValue("Select Update Option");

        // Add ComboBox to the content
        content.getChildren().addAll(updateOptions);

        // Set the dialog content
        dialog.getDialogPane().setContent(content);


        // Add OK and CANCEL buttons
        ButtonType cancelButton = ButtonType.CANCEL;
        dialog.getDialogPane().getButtonTypes().add(cancelButton);

        // Handle the selection action directly from the ComboBox (dropdown)
        updateOptions.setOnAction(event -> {
            String selectedOption = updateOptions.getValue();

            if (selectedOption != null) {
                if (selectedOption.equals("Update by Room Number")) {
                    // Handle updating by room number
                    updateByRoomNumber();
                } else if (selectedOption.equals("Update by CNIC")) {
                    // Handle updating by CNIC
                    updateByCnic();
                }
                dialog.close(); // Close the dialog after action
            } else {
                showError("No Option Selected", "Please select an update option.");
            }
        });

        // Show the dialog
        dialog.showAndWait();
    }

    // Function to Update By Room Number
    private void updateByRoomNumber() {
        // Get the room number from user input
        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("Enter Room Number");

        // Create a dialog to get the room number
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Update by Room Number");
        inputDialog.setHeaderText("Enter Room Number to Update Details");
        inputDialog.getDialogPane().setContent(roomNumberField);

        inputDialog.showAndWait().ifPresent(event -> {
            boolean found = false;

            // Validate room number input
            String roomNumberInput = roomNumberField.getText();
            int roomN;
            try {
                roomN = Integer.parseInt(roomNumberInput);
            } catch (NumberFormatException e) {
                showError("Invalid Room Number", "Room Number must be a valid integer.");
                return;
            }

            for (int i = 0; i < rBook.size(); i++) {
                if (rBook.get(i).getRNumber() == roomN) {
                    // Found the room, display dialog to update details
                    PersonWhoBook person = pBook.get(i);

                    // Create a new dialog for editing the customer details
                    Dialog<ButtonType> updateDialog = new Dialog<>();
                    updateDialog.setTitle("Update Customer Details");
                    updateDialog.setHeaderText("Update the details for Room Number: " + roomN);

                    // Create text fields with pre-filled values
                    TextField nameField = new TextField(person.getName());
                    TextField phoneField = new TextField(person.getPhoneNumber());
                    TextField emailField = new TextField(person.getGmail());
                    TextField dobField = new TextField(person.getDob());

                    // Labels for the fields
                    Label nameLabel = new Label("Name");
                    Label phoneLabel = new Label("Phone Number");
                    Label gmailLabel = new Label("Gmail");
                    Label dobLabel = new Label("Date of Birth");

                    // Set prompt texts
                    nameField.setPromptText("Enter Customer Name");
                    phoneField.setPromptText("Enter Phone Number (XXXX-XXXXXXX)");
                    emailField.setPromptText("Enter Email");
                    dobField.setPromptText("Enter Date of Birth (YYYY-MM-DD)");

                    // Add the fields to a VBox layout
                    VBox layout = new VBox(10, nameLabel, nameField,  phoneLabel, phoneField, gmailLabel, emailField, dobLabel, dobField);
                    layout.setStyle("-fx-padding: 20;");

                    updateDialog.getDialogPane().setContent(layout);
                    updateDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                    // Add input validation to the OK button
                    Node okButton = updateDialog.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.addEventFilter(ActionEvent.ACTION, e -> {
                        String name = nameField.getText();
                        String phone = phoneField.getText();
                        String email = emailField.getText();
                        String dob = dobField.getText();

                        // Validate inputs
                        if (!name.matches(NAME)) {
                            showError("Invalid Name", "Name must contain only alphabetic characters and spaces.");
                            e.consume();
                            return;
                        }

                        if (!phone.matches(PHONE)) {
                            showError("Invalid Phone Number", "Phone Number must be in the format XXXX-XXXXXXX.");
                            e.consume();
                            return;
                        }
                        if (!email.matches(GMAIL)) {
                            showError("Invalid Email", "Email must be in the format username@example.com.");
                            e.consume();
                            return;
                        }
                        if (!dob.matches(DOB)) {
                            showError("Invalid Date of Birth", "Date of Birth must be in the format YYYY-MM-DD.");
                            e.consume();
                            return;
                        }

                        //duplication

                        // Update the PersonWhoBook object with new values
                        person.setName(name);
                        person.setPhoneNumber(phone);
                        person.setGmail(email);
                        person.setDob(dob);


                    });

                    Optional<ButtonType> result = updateDialog.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        showInfo("Customer details updated successfully!");
                    }

                    found = true;
                    break;
                }
            }

            if (!found) {
                showError("No Room Found", "No data found for the given Room Number.");
            }
        });
    }



    //Function to update by cnic.
    private void updateByCnic() {
        // Get the CNIC from user input
        TextField cnicField = new TextField();
        cnicField.setPromptText("Enter CNIC");

        // Create a dialog to get the CNIC
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Update by CNIC");
        inputDialog.setHeaderText("Enter CNIC to Update Details");
        inputDialog.getDialogPane().setContent(cnicField);

        inputDialog.showAndWait().ifPresent(event -> {
            boolean found = false;

            String cnic = cnicField.getText();

            // Validate CNIC input
            if (!cnic.matches(CNIC)) {
                showError("Invalid CNIC", "CNIC must be in the format XXXXX-XXXXXXX-X.");
                return;
            }

            // Loop through the pBook list to find the matching CNIC
            for (int i = 0; i < pBook.size(); i++) {
                PersonWhoBook person = pBook.get(i);

                if (cnic.equals(pBook.get(i).getCnic())) {
                    // Found the person - Create a dialog for updating details
                    Dialog<ButtonType> updateDialog = new Dialog<>();
                    updateDialog.setTitle("Update Customer Details");
                    updateDialog.setHeaderText("Update details for CNIC: " + cnic);

                    // text set for updation...
                    TextField nameField = new TextField(person.getName());
                    TextField phoneField = new TextField(person.getPhoneNumber());
                    TextField emailField = new TextField(person.getGmail());

                    Label nameLabel = new Label("Name");
                    Label phoneLabel = new Label("Phone Number");
                    Label gmailLabel = new Label("Gmail");
                    nameField.setPromptText("Enter Customer Name");
                    phoneField.setPromptText("Enter Phone Number");
                    emailField.setPromptText("Enter Gmail");

                    // Add fields to the layout
                    VBox layout = new VBox(10, nameLabel, nameField, phoneLabel, phoneField, gmailLabel, emailField);
                    layout.setStyle("-fx-padding: 20;");

                    updateDialog.getDialogPane().setContent(layout);
                    updateDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                    // Add input validation to the OK button
                    Node okButton = updateDialog.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.addEventFilter(ActionEvent.ACTION, e -> {
                        String name = nameField.getText();
                        String phoneNumber = phoneField.getText();
                        String email = emailField.getText();

                        // Validate inputs
                        if (!name.matches(NAME)) {
                            showError("Invalid Name", "Name must contain only alphabetic characters and spaces.");
                            e.consume();
                            return;
                        }
                        if (!phoneNumber.matches(PHONE)) {
                            showError("Invalid Phone Number", "Phone number must be in the format XXXX-XXXXXXX.");
                            e.consume();
                            return;
                        }
                        if (!email.matches(GMAIL)) {
                            showError("Invalid Email", "Email must be in the format username@example.com.");
                            e.consume();
                            return;
                        }

                        // Update the PersonWhoBook object with new values
                        person.setName(name);
                        person.setPhoneNumber(phoneNumber);
                        person.setGmail(email);


                    });
                    // Reflect changes in the pBook ArrayList
//                    pBook.set(i, person); testing purposes

                    // Show the update dialog and wait for the response
                    Optional<ButtonType> result = updateDialog.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        showInfo("Customer details updated successfully!");
                    }
                    found = true;
                    break;
                }
            }

            if (!found) {
                showError("No Data Found", "No data found for the given CNIC.");
            }
        });
    }


    //Function to update Hygenic Status..
    @FXML
    public void updateHygienicStatus() {
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
                    currentStatusLabel.setText("Room Number: Not Found");
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

                    //set that room status
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
    public void checkHygienicStatus() {
        // Ensure rLs1 has data
        if (rLs1.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Rooms");
            alert.setHeaderText(null);
            alert.setContentText("No room data available.");
            alert.showAndWait();
            return;
        }

        // Create an ObservableList to store Room status data
        ObservableList<Room> roomStatusList = FXCollections.observableArrayList();

        // Fill the ObservableList with Room status data
        for (Room room : rLs1) {
            roomStatusList.add(new Room(room.getRNumber(), room.getRStatus()));
        }

        // Create a TableView to display the room status
        TableView<Room> tableView = new TableView<>(roomStatusList);

        // Define columns for the TableView using PropertyValueFactory
        TableColumn<Room, Integer> roomNumberColumn = new TableColumn<>("Room Number");
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rNumber"));
        roomNumberColumn.setPrefWidth(120);

        TableColumn<Room, String> hygienicStatusColumn = new TableColumn<>("Hygienic Status");
        hygienicStatusColumn.setCellValueFactory(new PropertyValueFactory<>("rStatus"));
        hygienicStatusColumn.setPrefWidth(150);

        // Add columns to the TableView
        tableView.getColumns().addAll(roomNumberColumn, hygienicStatusColumn);

        tableView.setPrefHeight(200); // Adjust height to display a reasonable number of rows
        tableView.setPrefWidth(350); // Adjust width to match column widths

        // Create a dialog box
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Room Hygienic Status");

        // Create a VBox to hold the TableView
        VBox content = new VBox(tableView);
        content.setSpacing(10);
        content.setStyle("-fx-padding: 20; -fx-alignment: center;");

        dialog.getDialogPane().setContent(content);

        // Add a button to close the dialog
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        // Show the dialog
        dialog.showAndWait();
    }

    @FXML
    public void driverPortal(ActionEvent event) {
        driver.setPrevious(this);
        switchScene(event,"/com/example/Lodgify/DrivePortal.fxml","Driver",driver);
    }

    @FXML
    public void exitToDashboard(ActionEvent event){
        switchScene(event,"/com/example/Lodgify/dashboard.fxml","Hotel Management System",previous);
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

}