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

import javax.tools.Tool;
import java.util.ArrayList;




public class EmployeeManager implements Regex, Tools {

    // UI COMPONENT
    private Admin previous;

    public void setPrevious(Admin previous) {
        this.previous = previous;
    }

    @FXML
    public Button addButton;
    @FXML
    public Button removeButton;
    @FXML
    public Button updateButton;
    @FXML
    public Button displayButton;


    Employee carryEmployeeDetails;


    // Static arraylists of different type
    public static ArrayList<ReceptionistHotel> hotelReceptionistList = new ArrayList<ReceptionistHotel>(10);
    public static ArrayList<ReceptionistResturant> resturantReceptionistList = new ArrayList<ReceptionistResturant>(10);
    public static ArrayList<DriverM> driverList = new ArrayList<DriverM>(10);
    public static ArrayList<SweeperM> sweeperList = new ArrayList<SweeperM>(10);
    // For showing credentials.
    public static ArrayList<Employee> allEmployees = new ArrayList<>();


    @FXML
    private void add() {
        // Create a custom dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Employee");
        dialog.setHeaderText("Enter employee details:");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Dropdown for selecting employee type
        ComboBox<String> employeeTypeComboBox = new ComboBox<>();
        employeeTypeComboBox.getItems().addAll("Driver", "Sweeper", "ReceptionistHotel", "ReceptionistRestaurant");
        employeeTypeComboBox.setPromptText("Select Employee Type");

        // Input fields for employee details
        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField cnicField = new TextField();
        cnicField.setPromptText("CNIC");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone Number");
        TextField dobField = new TextField();
        dobField.setPromptText("Date of Birth (YYYY-MM-DD)");
        TextField salaryField = new TextField();
        salaryField.setPromptText("Salary");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Layout: GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Employee Type:"), 0, 0);
        grid.add(employeeTypeComboBox, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("CNIC:"), 0, 2);
        grid.add(cnicField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Phone Number:"), 0, 4);
        grid.add(phoneNumberField, 1, 4);
        grid.add(new Label("Date of Birth:"), 0, 5);
        grid.add(dobField, 1, 5);
        grid.add(new Label("Salary:"), 0, 6);
        grid.add(salaryField, 1, 6);
        grid.add(new Label("Username:"), 0, 7);
        grid.add(usernameField, 1, 7);
        grid.add(new Label("Password:"), 0, 8);
        grid.add(passwordField, 1, 8);

        dialog.getDialogPane().setContent(grid);

        // Add custom validation logic
        final Button addButton = (Button) dialog.getDialogPane().lookupButton(addButtonType);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                String type = employeeTypeComboBox.getValue();
                if (type == null) {
                    showError("Please select an employee type.");
                    event.consume(); // Prevent dialog from closing
                    return;
                }

                String name = nameField.getText();
                String cnic = cnicField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneNumberField.getText();
                String dob = dobField.getText();
                int salary = Integer.parseInt(salaryField.getText());
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Validate required fields
                if (name.isEmpty() || cnic.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || dob.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    showError("All fields must be filled.");
                    event.consume(); // Prevent dialog from closing
                    return;
                }

                if (!username.matches(USERNAME)) {
                    showError("Name must be alphabetic");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!name.matches(NAME)) {
                    showError("Name must be alphabetic");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!cnic.matches(CNIC)) {
                    showError("CNIC must be like XXXXX-XXXXXXX-X");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!phoneNumber.matches(PHONE)) {
                    showError("Phone number must be like XXXX-XXXXXXX");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!email.matches(GMAIL)) {
                    showError("email id must be like username@gmail.com");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!dob.matches(DOB)) {
                    showError("The date MUST be in 'YYYY-MM-DD' format (e.g., '1990-12-31')");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (salary < 0) {
                    showError("Salary cant be negative");
                    event.consume(); // Prevent dialog from closing
                    return;
                }

                for (ReceptionistHotel emp : hotelReceptionistList) {
                    if (emp.getCnic().equals(cnic)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Cnic already exists.");
                        return;
                    }
                    if (emp.getGmail().equals(email)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Gmail already exists.");
                        return;
                    }
                    if (emp.getPhoneNumber().equals(phoneNumber)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Phone number already exists.");
                        return;
                    }
                    if (emp.getUserName().equals(username)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Username already exists.");
                        return;
                    }
                }

                for (ReceptionistResturant emp : resturantReceptionistList) {
                    if (emp.getCnic().equals(cnic)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Cnic already exists.");
                        return;
                    }
                    if (emp.getGmail().equals(email)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Gmail already exists.");
                        return;
                    }
                    if (emp.getPhoneNumber().equals(phoneNumber)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Phone number already exists.");
                        return;
                    }
                    if (emp.getUserName().equals(username)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Username already exists.");
                        return;
                    }
                }

                for (DriverM emp : driverList) {
                    if (emp.getCnic().equals(cnic)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Cnic already exists.");
                        return;
                    }
                    if (emp.getGmail().equals(email)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Gmail already exists.");
                        return;
                    }
                    if (emp.getPhoneNumber().equals(phoneNumber)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Phone number already exists.");
                        return;
                    }
                    if (emp.getUserName().equals(username)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Username already exists.");
                        return;
                    }

                }
                for (SweeperM emp : sweeperList) {
                    if (emp.getCnic().equals(cnic)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Cnic already exists.");
                        return;
                    }
                    if (emp.getGmail().equals(email)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Gmail already exists.");
                        return;
                    }
                    if (emp.getPhoneNumber().equals(phoneNumber)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Phone number already exists.");
                        return;
                    }
                    if (emp.getUserName().equals(username)) {
                        event.consume(); // Prevent dialog from closing
                        showError(" Username already exists.");
                        return;
                    }

                }

                // Create employee object based on type
                Employee newEmployee = switch (type) {
                    case "Driver" -> new DriverM(name, cnic, phoneNumber, email, dob, salary, username, password);

                    case "Sweeper" -> new SweeperM(name, cnic, phoneNumber, email, dob, salary, username, password);
                    case "ReceptionistHotel" ->
                            new ReceptionistHotel(name, cnic, phoneNumber, email, dob, salary, username, password);
                    case "ReceptionistRestaurant" ->
                            new ReceptionistResturant(name, cnic, phoneNumber, email, dob, salary, username, password);
                    default -> null;
                };

                if (newEmployee != null) {
                    // Add to respective list
                    if (newEmployee instanceof DriverM) {
                        driverList.add((DriverM) newEmployee);
                    } else if (newEmployee instanceof SweeperM) {
                        sweeperList.add((SweeperM) newEmployee);
                    } else if (newEmployee instanceof ReceptionistHotel) {
                        hotelReceptionistList.add((ReceptionistHotel) newEmployee);
                    } else if (newEmployee instanceof ReceptionistResturant) {
                        resturantReceptionistList.add((ReceptionistResturant) newEmployee);
                    }
                    updateAllEmployees();
                    showInfo("Employee added successfully.");
                }
            } catch (NumberFormatException e) {
                showError("Salary must be a valid numerical value.");
                event.consume(); // Prevent dialog from closing
            } catch (Exception e) {
                showError("An error occurred: " + e.getMessage());
                event.consume(); // Prevent dialog from closing
            }
        });

        dialog.showAndWait();
    }



    @FXML
    private void update() {
        // Create a custom dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Update Employee");
        dialog.setHeaderText("Enter the CNIC of the employee to update their details:");

        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        // Dropdown for selecting employee type
        ComboBox<String> employeeTypeComboBox = new ComboBox<>();
        employeeTypeComboBox.getItems().addAll("Driver", "Sweeper", "ReceptionistHotel", "ReceptionistRestaurant");
        employeeTypeComboBox.setPromptText("Select Employee Type");

        // Input fields
        TextField cnicField = new TextField();
        cnicField.setPromptText("CNIC");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone Number");
        TextField dobField = new TextField();
        dobField.setPromptText("Date of Birth (YYYY-MM-DD)");
        TextField salaryField = new TextField();
        salaryField.setPromptText("Salary");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Layout: GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Employee Type:"), 0, 0);
        grid.add(employeeTypeComboBox, 1, 0);
        grid.add(new Label("CNIC:"), 0, 1);
        grid.add(cnicField, 1, 1);
        grid.add(new Label("Name:"), 0, 2);
        grid.add(nameField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Phone Number:"), 0, 4);
        grid.add(phoneNumberField, 1, 4);
        grid.add(new Label("Date of Birth:"), 0, 5);
        grid.add(dobField, 1, 5);
        grid.add(new Label("Salary:"), 0, 6);
        grid.add(salaryField, 1, 6);
        grid.add(new Label("Username:"), 0, 7);
        grid.add(usernameField, 1, 7);
        grid.add(new Label("Password:"), 0, 8);
        grid.add(passwordField, 1, 8);

        dialog.getDialogPane().setContent(grid);

        // Prefill text fields when CNIC is entered
        cnicField.textProperty().addListener((observable, oldValue, newValue) -> {

            String type = employeeTypeComboBox.getValue();
            if (type == null || newValue.isEmpty()) {
                return;
            }
            switch (type) {
                case "Driver" -> {
                    boolean driverFound=false;
                    for (DriverM driver : driverList) {
                        if (driver.getCnic().equals(newValue)) {
                            nameField.setText(driver.getName());
                            emailField.setText(driver.getGmail());
                            phoneNumberField.setText(driver.getPhoneNumber());
                            dobField.setText(driver.getDob());
                            salaryField.setText(String.valueOf(driver.getSalary()));
                            usernameField.setText(driver.aut.getUserName());
                            passwordField.setText(driver.aut.getPassWord());

                            driverFound=true;

                            return;

                        }

                    }if(!driverFound){
                        if(newValue.matches(CNIC)){
                            showError("No driver found with this cnic");
                            cnicField.clear();
                        }

                    }

                }
                case "Sweeper" -> {
                    boolean sweeperFound=false;
                    for (SweeperM sweeper : sweeperList) {
                        if (sweeper.getCnic().equals(newValue)) {
                            nameField.setText(sweeper.getName());
                            emailField.setText(sweeper.getGmail());
                            phoneNumberField.setText(sweeper.getPhoneNumber());
                            dobField.setText(sweeper.getDob());
                            salaryField.setText(String.valueOf(sweeper.getSalary()));
                            usernameField.setText(sweeper.aut.getUserName());
                            passwordField.setText(sweeper.aut.getPassWord());
                            sweeperFound=true;
                            return;
                        }

                    }if(!sweeperFound){
                        if(newValue.matches(CNIC)){
                            showError("No sweeper with this cnic");
                            cnicField.clear();
                        }
                    }
                }
                case "ReceptionistHotel" -> {
                    boolean recFound=false;
                    for (ReceptionistHotel rec : hotelReceptionistList) {
                        if (rec.getCnic().equals(newValue)) {
                            nameField.setText(rec.getName());
                            emailField.setText(rec.getGmail());
                            phoneNumberField.setText(rec.getPhoneNumber());
                            dobField.setText(rec.getDob());
                            salaryField.setText(String.valueOf(rec.getSalary()));
                            usernameField.setText(rec.aut.getUserName());
                            passwordField.setText(rec.aut.getPassWord());
                            recFound=true;
                            return;
                        }

                    }if(!recFound){
                        if(newValue.matches(CNIC)){
                            showError("No hotel receptionist found with this cnic");
                            cnicField.clear();
                        }

                    }
                }
                case "ReceptionistRestaurant" -> {
                    boolean recFound=false;
                    for (ReceptionistResturant rec : resturantReceptionistList) {
                        if (rec.getCnic().equals(newValue)) {
                            nameField.setText(rec.getName());
                            emailField.setText(rec.getGmail());
                            phoneNumberField.setText(rec.getPhoneNumber());
                            dobField.setText(rec.getDob());
                            salaryField.setText(String.valueOf(rec.getSalary()));
                            usernameField.setText(rec.aut.getUserName());
                            passwordField.setText(rec.aut.getPassWord());
                            recFound=true;
                            return;
                        }

                    }if(!recFound){
                        if(newValue.matches(CNIC)){
                            showError("No restaraunt receptionist found with this cnic");
                            cnicField.clear();
                        }

                    }
                }
            }
            // Clear fields if no match found
            nameField.clear();
            emailField.clear();
            phoneNumberField.clear();
            dobField.clear();
            salaryField.clear();
            usernameField.clear();
            passwordField.clear();
        });



        // Add event filter for validation and update logic
        final Button updateButton = (Button) dialog.getDialogPane().lookupButton(updateButtonType);
        updateButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                String type = employeeTypeComboBox.getValue();
                if (type == null) {
                    showError("Please select an employee type.");
                    event.consume();
                    return;
                }
                String cnic = cnicField.getText();

                String name = nameField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneNumberField.getText();
                String dob = dobField.getText();
                int salary = Integer.parseInt(salaryField.getText());
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (!username.matches(USERNAME)) {
                    showError("Name must be alphabetic");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!name.matches(NAME)) {
                    showError("Name must be alphabetic");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!cnic.matches(CNIC)) {
                    showError("CNIC must be like XXXXX-XXXXXXX-X");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!phoneNumber.matches(PHONE)) {
                    showError("Phone number must be like XXXX-XXXXXXX");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!email.matches(GMAIL)) {
                    showError("email id must be like username@gmail.com");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (!dob.matches(DOB)) {
                    showError("The date MUST be in 'YYYY-MM-DD' format (e.g., '1990-12-31')");
                    event.consume(); // Prevent dialog from closing
                    return;
                }
                if (name.isEmpty() || cnic.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || dob.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    showError("All fields must be filled.");
                    event.consume();
                    return;
                }

//                .........
                if (salary<0) {
                    showError("Salary cant be negative");
                    event.consume(); // Prevent dialog from closing
                    return;
                }

                carryEmployeeDetails = new Employee(cnic,phoneNumber,email,username);

                boolean found = false;
                switch (type) {
                    case "Driver" -> {
                        for (DriverM driver : driverList) {
                            if (driver.getCnic().equals(cnic)) {
                                 found = checkDuplicationofInfo(carryEmployeeDetails,driver,event);
                                if(!found) {
                                    driverList.remove(driver);
                                    driverList.add(new DriverM(name, cnic, phoneNumber, email, dob, salary, username, password));
                                    found = false;
                                }

                                break;
                            }
                        }
                    }
                    case "Sweeper" -> {
                        for (SweeperM sweeper : sweeperList) {
                            if (sweeper.getCnic().equals(cnic)) {
                                found = checkDuplicationofInfo(carryEmployeeDetails,sweeper,event);
                                if(!found) {
                                    sweeperList.remove(sweeper);
                                    sweeperList.add(new SweeperM(name, cnic, phoneNumber, email, dob, salary, username, password));
                                    found = false;
                                }
                                break;
                            }
                        }
                    }
                    case "ReceptionistHotel" -> {
                        for (ReceptionistHotel rec : hotelReceptionistList) {
                            if (rec.getCnic().equals(cnic)) {
                                found = checkDuplicationofInfo(carryEmployeeDetails,rec,event);
                                if(!found){
                                    hotelReceptionistList.remove(rec);
                                    hotelReceptionistList.add(new ReceptionistHotel(name, cnic, phoneNumber,email, dob, salary, username, password));
                                    found = false;
                                }
                                break;
                            }
                        }
                    }
                    case "ReceptionistRestaurant" -> {
                        for (ReceptionistResturant rec : resturantReceptionistList) {
                            if (rec.getCnic().equals(cnic)) {
                                found = checkDuplicationofInfo(carryEmployeeDetails,rec,event);
                                if(!found) {
                                    resturantReceptionistList.remove(rec);
                                    resturantReceptionistList.add(new ReceptionistResturant(name, cnic, phoneNumber, email, dob, salary, username, password));
                                    found = false;
                                }
                                break;
                            }
                        }
                    }
                }

                if (!found) {
                    updateAllEmployees();
                    showInfo("Employee updated successfully.");
                }

            } catch (Exception e) {
                showError("An error occurred: " + e.getMessage());
                event.consume();
            }
        });
        dialog.showAndWait();
    }

    @FXML

    private void remove() {
        // Create a custom dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Remove Employee");
        dialog.setHeaderText("Enter details to remove an employee:");

        ButtonType removeButtonType = new ButtonType("Remove", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(removeButtonType, ButtonType.CANCEL);

        // Dropdown for selecting employee type
        ComboBox<String> employeeTypeComboBox = new ComboBox<>();
        employeeTypeComboBox.getItems().addAll("Driver", "Sweeper", "ReceptionistHotel", "ReceptionistRestaurant");
        employeeTypeComboBox.setPromptText("Select Employee Type");

        // Input field for CNIC
        TextField cnicField = new TextField();
        cnicField.setPromptText("CNIC");

        // Layout: GridPane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Employee Type:"), 0, 0);
        grid.add(employeeTypeComboBox, 1, 0);
        grid.add(new Label("CNIC:"), 0, 1);
        grid.add(cnicField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Add event filter for validation and removal logic
        final Button removeButton = (Button) dialog.getDialogPane().lookupButton(removeButtonType);
        removeButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                String type = employeeTypeComboBox.getValue();
                String cnic = cnicField.getText();

                if (type == null || cnic.isEmpty()) {
                    showError("Please select an employee type and enter a valid CNIC.");
                    event.consume(); // Prevent dialog from closing
                    return;
                }

                // Check and remove from the respective list based on the type
                boolean found = false;

                switch (type) {
                    case "Driver" -> {
                        for (int i = 0; i < driverList.size(); i++) {
                            if (cnic.equals(driverList.get(i).getCnic())) {
                                driverList.remove(i);
                                found = true;
                                break; // Exit the loop after removing
                            }
                        }
                    }
                    case "Sweeper" -> {
                        for (int i = 0; i < sweeperList.size(); i++) {
                            if (cnic.equals(sweeperList.get(i).getCnic())) {
                                sweeperList.remove(i);
                                found = true;
                                break; // Exit the loop after removing
                            }
                        }
                    }
                    case "ReceptionistHotel" -> {
                        for (int i = 0; i < hotelReceptionistList.size(); i++) {
                            if (cnic.equals(hotelReceptionistList.get(i).getCnic())) {
                                hotelReceptionistList.remove(i);
                                found = true;
                                break; // Exit the loop after removing
                            }
                        }
                    }
                    case "ReceptionistRestaurant" -> {
                        for (int i = 0; i < resturantReceptionistList.size(); i++) {
                            if (cnic.equals(resturantReceptionistList.get(i).getCnic())) {

                                resturantReceptionistList.remove(i);
                                found = true;
                                break; // Exit the loop after removing
                            }
                        }
                    }
                }

                if (found) {
                    updateAllEmployees();
                    showInfo("Employee removed successfully.");
                } else {
                    showError("No employee found with the given CNIC.");
                    event.consume();
                }

            } catch (Exception e) {
                showError("An error occurred: " + e.getMessage());
                event.consume(); // Prevent dialog from closing
            }
        });

        dialog.showAndWait();
    }



    // Helper methods to display error and info messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML

    private void display() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Employee List");
        dialog.setHeaderText("Select Employee Type to View");

        // Create ComboBox for selecting employee type
        ComboBox<String> employeeTypeComboBox = new ComboBox<>();
        employeeTypeComboBox.getItems().addAll("Hotel Receptionist", "Restaurant Receptionist", "Driver", "Sweeper");

        // TableView to display employee data
        TableView<Employee> tableView = new TableView<>();

        // Define columns for employee properties
        TableColumn<Employee, String> employeeNameColumn = new TableColumn<>("Name");
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, String> employeeCnicColumn = new TableColumn<>("CNIC");
        employeeCnicColumn.setCellValueFactory(new PropertyValueFactory<>("cnic"));

        TableColumn<Employee, String> employeeGmailColumn = new TableColumn<>("Gmail");
        employeeGmailColumn.setCellValueFactory(new PropertyValueFactory<>("gmail"));

        TableColumn<Employee, String> employeePhoneNumberColumn = new TableColumn<>("Phone Number");
        employeePhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Employee, String> employeeDobColumn = new TableColumn<>("Date of Birth");
        employeeDobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));

        TableColumn<Employee, Integer> employeeSalaryColumn = new TableColumn<>("Salary");
        employeeSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        TableColumn<Employee, String> employeeUsernameColumn = new TableColumn<>("Username");
        employeeUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<Employee, String> employeePasswordColumn = new TableColumn<>("Password");
        employeePasswordColumn.setCellValueFactory(new PropertyValueFactory<>("passWord"));

        // Add columns to the table
        tableView.getColumns().addAll(
                employeeNameColumn,
                employeeCnicColumn,
                employeeGmailColumn,
                employeePhoneNumberColumn,
                employeeDobColumn,
                employeeSalaryColumn,
                employeeUsernameColumn,
                employeePasswordColumn

        );

        // VBox to contain ComboBox and TableView
        VBox vBox = new VBox(10, employeeTypeComboBox, tableView);
        dialog.getDialogPane().setContent(vBox);

        // Add a close button to the dialog
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        // Set action when a selection is made in the ComboBox
        employeeTypeComboBox.setOnAction(e -> {
            String selectedType = employeeTypeComboBox.getValue();
            ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList();

            // Populate the table based on the selected employee type
            switch (selectedType) {
                case "Hotel Receptionist":
                    if (hotelReceptionistList.isEmpty()) {
                        tableView.setPlaceholder(new Label("No data available for hotel receptionists."));
                    } else {
                        employeeObservableList.addAll(hotelReceptionistList);
                    }
                    break;

                case "Restaurant Receptionist":
                    if (resturantReceptionistList.isEmpty()) {
                        tableView.setPlaceholder(new Label("No data available for restaurant receptionists."));
                    } else {
                        employeeObservableList.addAll(resturantReceptionistList);
                    }
                    break;

                case "Driver":
                    if (driverList.isEmpty()) {
                        tableView.setPlaceholder(new Label("No data available for drivers."));
                    } else {
                        employeeObservableList.addAll(driverList);
                    }
                    break;

                case "Sweeper":
                    if (sweeperList.isEmpty()) {
                        tableView.setPlaceholder(new Label("No data available for sweepers."));
                    } else {
                        employeeObservableList.addAll(sweeperList);
                    }
                    break;

                default:
                    tableView.setPlaceholder(new Label("Please select a valid employee type."));
            }

            // Set the ObservableList to the TableView to display the selected employee type
            tableView.setItems(employeeObservableList);
        });



        // Show the dialog
        dialog.showAndWait();
    }

    // Dynamic Polymorphism.
    @FXML
    public void credentials() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < allEmployees.size(); i++) {
            Employee emp = allEmployees.get(i);
            strBuilder.append(getCredentials(i, emp));
        }
        showAlert("ALL Employees", strBuilder.toString());
    }

    // For adding index of employee and formatting the information.
    private String getCredentials(int index, Employee employee) {
        return "Employee: " + (index + 1) + "\n"
                + employee.giveCredentials() + "\n\n";
    }



    public void updateAllEmployees(){
        allEmployees.clear();
        allEmployees.addAll(hotelReceptionistList);
        allEmployees.addAll(driverList);
        allEmployees.addAll(sweeperList);
        allEmployees.addAll(resturantReceptionistList);
    }

    @FXML
    public void ExitToAdminPortal(ActionEvent event){
        switchScene(event,"/com/example/lodgify/AdminPortal.fxml","Admin Portal",previous);
    }




    public boolean checkDuplicationofInfo(Employee carryEmployeeDetails, Employee employeeForDuplication, ActionEvent event) {
        boolean found = false;

        for (ReceptionistHotel emp : hotelReceptionistList) {
            if (emp.getCnic().equals(carryEmployeeDetails.getCnic())) {

                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Cnic already exists.");
                    found = true;
                }
            }
            if (emp.getGmail().equals(carryEmployeeDetails.getGmail())) {
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Gmail already exists.");
                    found = true;
                }
            }
            if (emp.getPhoneNumber().equals(carryEmployeeDetails.getPhoneNumber())) {
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Phone number already exists.");
                    found = true;
                }
            }
            if (emp.getUserName().equals(carryEmployeeDetails.getUserName())) {
                System.out.println("matches ");
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Username already exists.");
                    found = true;
                }
            }

        }

        for (ReceptionistResturant emp : resturantReceptionistList) {
            if (emp.getCnic().equals(carryEmployeeDetails.getCnic())) {
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Cnic already exists.");
                    found = true;
                }
            }
            if (emp.getGmail().equals(carryEmployeeDetails.getGmail())) {
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Gmail already exists.");
                    found = true;
                }
            }
            if (emp.getPhoneNumber().equals(carryEmployeeDetails.getPhoneNumber())) {
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Phone number already exists.");
                    found = true;
                }
            }
            if (emp.getUserName().equals(carryEmployeeDetails.getUserName())) {
                System.out.println("matches ");
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Username already exists.");
                    found = true;
                }
            }
        }

        for (DriverM emp : driverList) {
            if (emp.getCnic().equals(carryEmployeeDetails.getCnic())) {
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Cnic already exists.");
                    found = true;
                }
            }
            if (emp.getGmail().equals(carryEmployeeDetails.getGmail())) {
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Gmail already exists.");
                    found = true;
                }
            }
            if (emp.getPhoneNumber().equals(carryEmployeeDetails.getPhoneNumber())) {
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Phone number already exists.");
                    found = true;
                }
            }

            if (emp.getUserName().equals(carryEmployeeDetails.getUserName())) {
                System.out.println("matches ");
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Username already exists.");
                    found = true;
                }
            }
        }

        for (SweeperM emp : sweeperList) {
            if (emp.getCnic().equals(carryEmployeeDetails.getCnic())) {
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Cnic already exists.");
                    found = true;
                }
            }
            if (emp.getGmail().equals(carryEmployeeDetails.getGmail())) {
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Gmail already exists.");
                    found = true;
                }
            }
            if (emp.getPhoneNumber().equals(carryEmployeeDetails.getPhoneNumber())) {
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Phone number already exists.");
                    found = true;
                }
            }
            if (emp.getUserName().equals(carryEmployeeDetails.getUserName())) {
                System.out.println("matches ");
                if (emp != employeeForDuplication) {
                    event.consume(); // Prevent dialog from closing
                    showError(" Username already exists.");
                    found = true;
                }
            }
        }
        return found;

    }

}
