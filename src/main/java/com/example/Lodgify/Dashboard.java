package com.example.Lodgify;

import com.example.Lodgify.Administration.*;
import com.example.Lodgify.HotelReceptionist.Booking;
import com.example.Lodgify.RestaurantManagement.Restaurant;
import com.example.Lodgify.Staff.StaffPortal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.example.Lodgify.Administration.Admin;
import com.example.Lodgify.HotelReceptionist.PersonWhoBook;
import com.example.Lodgify.RestaurantManagement.FoodItem;
import com.example.Lodgify.RestaurantManagement.Menu;
import com.example.Lodgify.RestaurantManagement.Order;
import com.example.Lodgify.RestaurantManagement.OrderItem;
import com.example.Lodgify.RestaurantManagement.SizePriceQuantity;
import com.example.Lodgify.Administration.Room;

import static com.example.Lodgify.Administration.EmployeeManager.hotelReceptionistList;
import static com.example.Lodgify.Administration.EmployeeManager.resturantReceptionistList;


public class Dashboard implements Tools {
    @FXML
    private Button Administration;
    @FXML
    private Button Hotel;
    @FXML
    private Button Staff;
    @FXML
    private Button Resturaunt;


    Restaurant restaurant=new Restaurant();
    Booking booking=new Booking();
    Admin admin=new Admin();
    StaffPortal staff=new StaffPortal();
    public static ArrayList<Registration> signupList = new ArrayList<>();




    @FXML
    public  void Administration(ActionEvent event){
        if(signupList.isEmpty()){
            Registration.RegisterAdmin();
        }
        else{
            String[] credentials = showLoginDialogForAdministration();
            if (credentials != null) {
                String username = credentials[0];
                String password = credentials[1];

                // Check if credentials are correct
                if (signupList.get(0).aut.checkAuthorization(username,password)) {
                    shiftToAdmin(event);
                } else {
                    // Login failed
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password. Please try again.");
                    alert.showAndWait();
                }
            }
        }




    }
    public void shiftToAdmin(ActionEvent event){
        admin.setPrevious(this);
        switchScene(event,"/com/example/lodgify/AdminPortal.fxml","Administration",admin);
    }
    @FXML
    public void Hotel(ActionEvent event){
        if(!hotelReceptionistList.isEmpty()){
            String[] credentials = showLoginDialog();
            Boolean login=false;
            if (credentials != null) {
                String username = credentials[0];
                String password = credentials[1];
                for (int i=0;i<admin.getEmployee().hotelReceptionistList.size();i++){
                    login=hotelReceptionistList.get(i).aut.checkAuthorization(username,password);
                    if(login==true){
                        break;
                    }
                }
            }
            if(login==true) {
                shiftToHotel(event);
            }
            else {
                // Login failed
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password. Please try again.");
                alert.showAndWait();
            }
        }else {
            // Login failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("There is no Hotel Receptionist in the Database");
            alert.showAndWait();
        }
    }

    public void shiftToHotel(ActionEvent event){
        booking.setPrevious(this);
        switchScene(event,"/com/example/lodgify/ReceptionistPortal.fxml","Hotel Receptionist",booking);
    }

    @FXML
    public void Resturaunt(ActionEvent event){
        if(!resturantReceptionistList.isEmpty()){
            String[] credentials = showLoginDialog();
            Boolean login=false;
            if (credentials != null) {
                String username = credentials[0];
                String password = credentials[1];
                for (int i=0;i<admin.getEmployee().resturantReceptionistList.size();i++){
                    login=resturantReceptionistList.get(i).aut.checkAuthorization(username,password);
                    if(login==true){
                        break;
                    }
                }
            }
            if(login==true) {
                shiftToResturaunt(event);
            }
            else {
                // Login failed
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password. Please try again.");
                alert.showAndWait();
            }
        }else {
            // Login failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("There is no Restaurant Manager in the Database");
            alert.showAndWait();
        }

    }
    public void shiftToResturaunt(ActionEvent event){
        restaurant.setPrevious(this);
        switchScene(event,"/com/example/lodgify/RestaurantManagement/Restraunt.fxml","Restraunt Management System",restaurant);
    }
    @FXML
    public  void Staff(ActionEvent event){
        shiftToStaff(event);
    }
    public void shiftToStaff(ActionEvent event){
        staff.setPrevious(this);
        switchScene(event,"/com/example/lodgify/StaffPortal.fxml","Service Staff Dashboard",staff);
    }
    @FXML
    public void Exit(){
        write.data();

        System.exit(0);
    }


    public static String[] showLoginDialog() {
        // Create the dialog
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Please enter your login credentials:");

        // Set the button types
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        GridPane.setHgrow(usernameField, Priority.ALWAYS);
        GridPane.setHgrow(passwordField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable login button based on input
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || passwordField.getText().trim().isEmpty());
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(usernameField.getText().trim().isEmpty() || newValue.trim().isEmpty());
        });

        // Set the dialog result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new String[]{usernameField.getText(), passwordField.getText()};
            }
            return null;
        });

        // Show the dialog and wait for the result
        return dialog.showAndWait().orElse(null);
    }

    public static String[] showLoginDialogForAdministration() {
        // Create the dialog
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Please enter your login credentials:");

        // Set the button types
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        ButtonType forgetButtonType = new ButtonType("Update Password", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL, forgetButtonType);

        // Create the username and password fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        GridPane.setHgrow(usernameField, Priority.ALWAYS);
        GridPane.setHgrow(passwordField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable login button based on input
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || passwordField.getText().trim().isEmpty());
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(usernameField.getText().trim().isEmpty() || newValue.trim().isEmpty());
        });

        // Set the dialog result
        dialog.setResultConverter(button -> {
            if (button == loginButtonType) {
                // Return an array with username and password
                return new String[]{usernameField.getText(), passwordField.getText()};
            } else if (button == forgetButtonType) {
                // Call the method to retrieve the password
                forgetpasswordretrive();
                return null; // Return null or appropriate value as needed
            }
            return null; // Handle cancel or close
        });

        // Show the dialog and wait for the result
        return dialog.showAndWait().orElse(null);
    }


    public static void forgetpasswordretrive(){
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Admin Password Updation");
        dialog.setHeaderText("Enter the password carefully:");

        ButtonType addButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        TextField questionField = new TextField();
        questionField.setPromptText("childhood friend ? ");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("New Password");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Enter the name of childhood friend: "), 0, 0);
        grid.add(questionField, 1, 0);
        grid.add(new Label("New password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        dialog.getDialogPane().setContent(grid);


        final Button addButton = (Button) dialog.getDialogPane().lookupButton(addButtonType);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (questionField.getText().trim().equals(Dashboard.signupList.get(0).getPrivateQuestion())){
                showInfo("Congratulation !! Password updated successfully ");
                Dashboard.signupList.get(0).setPrivateQuestion(questionField.getText().trim());
            }
            else {
                showError("Wrong Answer !! ");
                event.consume();
            }
        });
        dialog.showAndWait();


    }
    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    Database write=()->{

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Launch\\Launch By Gujjar\\src\\main\\java\\com\\example\\Lodgify\\data.txt"))) {

            writer.write(String.valueOf(Dashboard.signupList.size()));
            writer.newLine();

            for (Registration credentials : Dashboard.signupList)
            {
                writer.write(credentials.getName() != null ? credentials.getName() : "N/A");

                writer.newLine();
                writer.write(credentials.getCnic() != null ? credentials.getCnic() : "N/A");
                writer.newLine();
                writer.write(credentials.getGmail() != null ? credentials.getGmail() : "N/A");
                writer.newLine();
                writer.write(credentials.getPhoneNumber() != null ? credentials.getPhoneNumber() : "N/A");
                writer.newLine();
                writer.write(credentials.getDob() != null ? credentials.getDob() : "N/A");
                writer.newLine();
                writer.write(credentials.aut.getUserName()  !=null ? credentials.aut.getUserName() : "N/A");
                writer.newLine();
                writer.write(credentials.aut.getPassWord()  !=null ? credentials.aut.getPassWord() : "N/A");
                writer.newLine();
                writer.write(credentials.getPrivateQuestion() != null ? credentials.getPrivateQuestion() : "N/A");
                writer.newLine();


                writer.newLine();

            }

            // Write the size of the room list
            writer.write(String.valueOf(RoomManager.roomList.size()));
            writer.newLine();

            for (Room room : RoomManager.roomList) {
                writer.write(room.getRAvailability() != null ? room.getRAvailability() : "N/A");
                writer.newLine();

                writer.write(String.valueOf(room.getRBeds()));
                writer.newLine();

                writer.write(room.getRFloor() != null ? room.getRFloor() : "N/A");
                writer.newLine();

                writer.write(String.valueOf(room.getRNumber()));
                writer.newLine();

                writer.write(String.valueOf(room.getRPrice()));
                writer.newLine();

                writer.write(room.getRStatus() != null ? room.getRStatus() : "N/A");
                writer.newLine();

                writer.write(room.getRType() != null ? room.getRType() : "N/A");
                writer.newLine();

                writer.newLine(); // Move to the next line after each room object
            }

            writer.write(String.valueOf(CarManager.carlist.size()));
            writer.newLine();
            for (Car car : CarManager.carlist) {


                writer.write(car.getModel() != null ? car.getModel() : "N/A");
                writer.newLine();

                writer.write(String.valueOf(car.getId()));
                writer.newLine();

                writer.write(car.getL_plateno() != null ? car.getL_plateno() : "N/A");
                writer.newLine();

                writer.write(car.getAvailability() != null ? car.getAvailability() : "N/A");
                writer.newLine();

                writer.newLine();
            }
            writer.write(String.valueOf(EmployeeManager.driverList.size()));
            writer.newLine();
            for(DriverM driver:EmployeeManager.driverList){
                writer.write(driver.getName() != null ? driver.getName() : "N/A");

                writer.newLine();
                writer.write(driver.getCnic() != null ? driver.getCnic() : "N/A");
                writer.newLine();
                writer.write(driver.getGmail() != null ? driver.getGmail() : "N/A");
                writer.newLine();
                writer.write(driver.getPhoneNumber() != null ? driver.getPhoneNumber() : "N/A");
                writer.newLine();
                writer.write(driver.getDob() != null ? driver.getDob() : "N/A");
                writer.newLine();
                writer.write(String.valueOf(driver.getSalary()));
                writer.newLine();
                writer.write(driver.aut.getUserName()  !=null ? driver.aut.getUserName() : "N/A");
                writer.newLine();
                writer.write(driver.aut.getPassWord()  !=null ? driver.aut.getPassWord() : "N/A");
                writer.newLine();;


                writer.newLine();





            }
            writer.write(String.valueOf(EmployeeManager.sweeperList.size()));
            writer.newLine();
            for(SweeperM sweeper:EmployeeManager.sweeperList){
                writer.write(sweeper.getName() != null ? sweeper.getName() : "N/A");
                writer.newLine();
                writer.write(sweeper.getCnic() != null ? sweeper.getCnic() : "N/A");
                writer.newLine();
                writer.write(sweeper.getGmail() != null ? sweeper.getGmail() : "N/A");
                writer.newLine();
                writer.write(sweeper.getPhoneNumber() != null ? sweeper.getPhoneNumber() : "N/A");
                writer.newLine();
                writer.write(sweeper.getDob() != null ? sweeper.getDob() : "N/A");
                writer.newLine();
                writer.write(String.valueOf(sweeper.getSalary()));
                writer.newLine();
                writer.write(sweeper.aut.getUserName()  !=null ? sweeper.aut.getUserName() : "N/A");
                writer.newLine();
                writer.write(sweeper.aut.getPassWord()  !=null ? sweeper.aut.getPassWord() : "N/A");
                writer.newLine();

                writer.newLine();


            }
            writer.write(String.valueOf(EmployeeManager.hotelReceptionistList.size()));
            writer.newLine();
            for(ReceptionistHotel receptionist:EmployeeManager.hotelReceptionistList){
                writer.write(receptionist.getName() != null ? receptionist.getName() : "N/A");
                writer.newLine();
                writer.write(receptionist.getCnic() != null ? receptionist.getCnic() : "N/A");
                writer.newLine();
                writer.write(receptionist.getGmail() != null ? receptionist.getGmail() : "N/A");
                writer.newLine();
                writer.write(receptionist.getPhoneNumber() != null ? receptionist.getPhoneNumber() : "N/A");
                writer.newLine();
                writer.write(receptionist.getDob() != null ? receptionist.getDob() : "N/A");
                writer.newLine();
                writer.write(String.valueOf(receptionist.getSalary()));
                writer.newLine();

                writer.write(receptionist.aut.getUserName()  !=null ? receptionist.aut.getUserName() : "N/A");
                writer.newLine();
                writer.write(receptionist.aut.getPassWord()  !=null ? receptionist.aut.getPassWord() : "N/A");
                writer.newLine();

                writer.newLine();



            }

            writer.write(String.valueOf(EmployeeManager.resturantReceptionistList.size()));
            writer.newLine();
            for(ReceptionistResturant receptionist:EmployeeManager.resturantReceptionistList){
                writer.write(receptionist.getName() != null ? receptionist.getName() : "N/A");
                writer.newLine();
                writer.write(receptionist.getCnic() != null ? receptionist.getCnic() : "N/A");
                writer.newLine();
                writer.write(receptionist.getGmail() != null ? receptionist.getGmail() : "N/A");
                writer.newLine();
                writer.write(receptionist.getPhoneNumber() != null ? receptionist.getPhoneNumber() : "N/A");
                writer.newLine();
                writer.write(receptionist.getDob() != null ? receptionist.getDob() : "N/A");
                writer.newLine();
                writer.write(String.valueOf(receptionist.getSalary()));
                writer.newLine();
                writer.write(receptionist.aut.getUserName()  !=null ? receptionist.aut.getUserName() : "N/A");
                writer.newLine();
                writer.write(receptionist.aut.getPassWord()  !=null ? receptionist.aut.getPassWord() : "N/A");
                writer.newLine();

                writer.newLine();




            }

            //person database arraylists
            writer.write(String.valueOf(Booking.database.size()));
            writer.newLine();
            for(PersonWhoBook bookedPerson:Booking.database){
                writer.write(bookedPerson.getName() !=null ? bookedPerson.getName() : "N/A");
                writer.newLine();
                writer.write(bookedPerson.getCnic() !=null ? bookedPerson.getCnic() : "N/A");
                writer.newLine();
                writer.write(bookedPerson.getPhoneNumber() !=null ? bookedPerson.getPhoneNumber() : "N/A");
                writer.newLine();
                writer.write(bookedPerson.getGmail() !=null ? bookedPerson.getGmail() : "N/A");
                writer.newLine();
                writer.write(bookedPerson.getDob() !=null ? bookedPerson.getDob() : "N/A");
                writer.newLine();
                writer.write(String.valueOf(bookedPerson.getRNumber()));
                writer.newLine();

                writer.newLine();
            }

            //pBook arraylist
            writer.write(String.valueOf(Booking.pBook.size()));
            writer.newLine();
            for(PersonWhoBook bookingPerson:Booking.pBook){
                writer.write(bookingPerson.getName() !=null ? bookingPerson.getName() : "N/A");
                writer.newLine();
                writer.write(bookingPerson.getCnic() !=null ? bookingPerson.getCnic() : "N/A");
                writer.newLine();
                writer.write(bookingPerson.getPhoneNumber() !=null ? bookingPerson.getPhoneNumber() : "N/A");
                writer.newLine();
                writer.write(bookingPerson.getGmail() !=null ? bookingPerson.getGmail() : "N/A");
                writer.newLine();
                writer.write(bookingPerson.getDob() !=null ? bookingPerson.getDob() : "N/A");
                writer.newLine();
                writer.write(String.valueOf(bookingPerson.getRNumber()));
                writer.newLine();

                writer.newLine();
            }

            //arraylist of allRooms
            writer.write(String.valueOf(Booking.rLs1.size()));
            writer.newLine();
            for(Room rooms:Booking.rLs1){
                writer.write(rooms.getRAvailability() != null ? rooms.getRAvailability() : "N/A");
                writer.newLine();
                writer.write(String.valueOf(rooms.getRBeds()));
                writer.newLine();
                writer.write(rooms.getRFloor() != null ? rooms.getRFloor() : "N/A");
                writer.newLine();
                writer.write(String.valueOf(rooms.getRNumber()));
                writer.newLine();
                writer.write(String.valueOf(rooms.getRPrice()));
                writer.newLine();
                writer.write(rooms.getRStatus() != null ? rooms.getRStatus() : "N/A");
                writer.newLine();
                writer.write(rooms.getRType() != null ? rooms.getRType() : "N/A");
                writer.newLine();

                writer.newLine(); // Move to the next line after each room object

            }

            //array list of bookedRooms
            writer.write(String.valueOf(Booking.rBook.size()));
            writer.newLine();
            for(Room bookedRooms:Booking.rBook){
                writer.write(bookedRooms.getRAvailability() != null ? bookedRooms.getRAvailability() : "N/A");
                writer.newLine();
                writer.write(String.valueOf(bookedRooms.getRBeds()));
                writer.newLine();
                writer.write(bookedRooms.getRFloor() != null ? bookedRooms.getRFloor() : "N/A");
                writer.newLine();
                writer.write(String.valueOf(bookedRooms.getRNumber()));
                writer.newLine();
                writer.write(String.valueOf(bookedRooms.getRPrice()));
                writer.newLine();
                writer.write(bookedRooms.getRStatus() != null ? bookedRooms.getRStatus() : "N/A");
                writer.newLine();
                writer.write(bookedRooms.getRType() != null ? bookedRooms.getRType() : "N/A");
                writer.newLine();

                writer.newLine(); // Move to the next line after each room object
            }

            // Writing Menu Data
            writer.write(String.valueOf(Menu.menuItems.size())); // Write the number of items in the menu

            writer.newLine();

            for (FoodItem foodItem : Menu.menuItems) {
                writer.write(foodItem.getName() != null ? foodItem.getName() : "N/A"); // Write the name
                writer.newLine();

                writer.write(String.valueOf(foodItem.getSizePriceQuantities().size())); // Write the number of sizes
                writer.newLine();

                for (SizePriceQuantity spq : foodItem.getSizePriceQuantities()) {
                    writer.write(spq.getSize() != null ? spq.getSize() : "N/A"); // Write the size
                    writer.newLine();
                    writer.write(String.valueOf(spq.getPrice())); // Write the price
                    writer.newLine();
                    writer.write(String.valueOf(spq.getQuantity())); // Write the quantity
                    writer.newLine();

                    writer.newLine();
                }

            }

            // Writing Orders
            writer.write(String.valueOf(Restaurant.orders.size())); // Number of orders
            writer.newLine();

            for (Order order : Restaurant.orders) {
                writer.write(String.valueOf(order.getid())); // Order ID
                writer.newLine();
                writer.write(String.valueOf(order.getMyorder().size())); // Number of items in the order
                writer.newLine();

                for (OrderItem orderItem : order.getMyorder()) {
                    writer.write(orderItem.getItemName() != null ? orderItem.getItemName() : "N/A");
                    writer.newLine();
                    writer.write(String.valueOf(orderItem.getQuantity()));
                    writer.newLine();
                    writer.write(orderItem.getSize() != null ? orderItem.getSize() : "N/A");
                    writer.newLine();
                    writer.write(String.valueOf(orderItem.getPrice()));
                    writer.newLine();

                }
            }


            System.out.println("Data has been written to database.txt successfully.");
        } catch (IOException e) {
            e.printStackTrace(); // Handle any I/O exceptions
        }
    };



}