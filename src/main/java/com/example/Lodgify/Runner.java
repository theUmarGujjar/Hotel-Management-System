package com.example.Lodgify;

import com.example.Lodgify.Administration.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import com.example.Lodgify.HotelReceptionist.Booking;
import com.example.Lodgify.HotelReceptionist.PersonWhoBook;
import com.example.Lodgify.RestaurantManagement.FoodItem;
import com.example.Lodgify.RestaurantManagement.Menu;
import com.example.Lodgify.RestaurantManagement.Order;
import com.example.Lodgify.RestaurantManagement.OrderItem;
import com.example.Lodgify.RestaurantManagement.Restaurant;
import com.example.Lodgify.RestaurantManagement.SizePriceQuantity;



public class Runner extends Application {

    Database read = () -> {
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Launch\\Launch By Gujjar\\src\\main\\java\\com\\example\\Lodgify\\data.txt"))) {

            int signupListSize = Integer.parseInt(reader.readLine());
            Dashboard.signupList.clear();

            for (int i = 0; i < signupListSize; i++) {
                String name = reader.readLine();
                String cnic = reader.readLine();
                String gmail = reader.readLine();
                String phoneNumber = reader.readLine();
                String dob = reader.readLine();
                String userName = reader.readLine();
                String passWord = reader.readLine();
                String privateQuestion = reader.readLine();


                reader.readLine();
                Registration admin = Registration.loadRegisteration(name, cnic, phoneNumber, gmail, dob, userName, passWord ,privateQuestion);
                Dashboard.signupList.add(admin);
            }



            int roomListSize = Integer.parseInt(reader.readLine()); // Read the list size

            RoomManager.roomList.clear(); // Clear the ArrayList before loading data

            for (int i = 0; i < roomListSize; i++) {

                String rAvailability = reader.readLine();
                int rBeds = Integer.parseInt(reader.readLine());
                String rFloor = reader.readLine();
                int rNumber = Integer.parseInt(reader.readLine());
                int rPrice = Integer.parseInt(reader.readLine());
                String rStatus = reader.readLine();
                String rType = reader.readLine();
                reader.readLine();
                Room room = new Room(rNumber, rPrice, rType, rBeds, rStatus, rFloor, rAvailability);
                RoomManager.roomList.add(room);
            }


            int carListSize = Integer.parseInt(reader.readLine());
            CarManager.carlist.clear();
            for (int i = 0; i < carListSize; i++) {
                String model = reader.readLine();
                int id = Integer.parseInt(reader.readLine());
                String L_plateno = reader.readLine();
                String availability = reader.readLine();
                reader.readLine();
                Car car = new Car(id, model, L_plateno, availability);

                CarManager.carlist.add(car);

            }
            int driverListSize = Integer.parseInt(reader.readLine());
            EmployeeManager.driverList.clear();
            for (int i = 0; i < driverListSize; i++) {
                String name = reader.readLine();
                String cnic = reader.readLine();
                String gmail = reader.readLine();
                String phoneNumber = reader.readLine();
                String dob = reader.readLine();
                int salary = Integer.parseInt(reader.readLine());
                String userName = reader.readLine();
                String passWord = reader.readLine();


                reader.readLine();
                DriverM driver = new DriverM(name, cnic, phoneNumber, gmail, dob, salary, userName, passWord);
                EmployeeManager.driverList.add(driver);
                EmployeeManager.allEmployees.add(driver);


            }
            int sweeperListSize = Integer.parseInt(reader.readLine());
            EmployeeManager.sweeperList.clear();
            for (int i = 0; i < sweeperListSize; i++) {
                String name = reader.readLine();
                String cnic = reader.readLine();
                String gmail = reader.readLine();
                String phoneNumber = reader.readLine();
                String dob = reader.readLine();
                int salary = Integer.parseInt(reader.readLine());
                String userName = reader.readLine();
                String passWord = reader.readLine();

                reader.readLine();
                SweeperM sweeper = new SweeperM(name, cnic, phoneNumber, gmail, dob, salary, userName, passWord);
                EmployeeManager.sweeperList.add(sweeper);
                EmployeeManager.allEmployees.add(sweeper);

            }

            int hotelreceptionistListSize = Integer.parseInt(reader.readLine());
            EmployeeManager.hotelReceptionistList.clear();
            for (int i = 0; i < hotelreceptionistListSize; i++) {
                String name = reader.readLine();
                String cnic = reader.readLine();
                String gmail = reader.readLine();
                String phoneNumber = reader.readLine();
                String dob = reader.readLine();
                int salary = Integer.parseInt(reader.readLine());
                String userName = reader.readLine();
                String passWord = reader.readLine();
                reader.readLine();
                ReceptionistHotel hrecep = new ReceptionistHotel(name, cnic, phoneNumber, gmail, dob, salary, userName, passWord);
                EmployeeManager.hotelReceptionistList.add(hrecep);
                EmployeeManager.allEmployees.add(hrecep);
            }

            int resturantreceptionistListSize = Integer.parseInt(reader.readLine());
            EmployeeManager.resturantReceptionistList.clear();
            for (int i = 0; i < resturantreceptionistListSize; i++) {
                String name = reader.readLine();
                String cnic = reader.readLine();
                String gmail = reader.readLine();
                String phoneNumber = reader.readLine();
                String dob = reader.readLine();
                int salary = Integer.parseInt(reader.readLine());
                String userName = reader.readLine();
                String passWord = reader.readLine();
                reader.readLine();
                ReceptionistResturant restRecep = new ReceptionistResturant(name, cnic, phoneNumber, gmail, dob, salary, userName, passWord);
                EmployeeManager.resturantReceptionistList.add(restRecep);
                EmployeeManager.allEmployees.add(restRecep);
            }


            int allPersonsListSize = Integer.parseInt(reader.readLine());
            Booking.database.clear();
            for (int i = 0; i < allPersonsListSize; i++) {
                String name = reader.readLine();
                String cnic = reader.readLine();
                String phnNum = reader.readLine();
                String gmail = reader.readLine();
                String dob = reader.readLine();
                int rNum = Integer.parseInt(reader.readLine());
                reader.readLine();
                PersonWhoBook allPersons = new PersonWhoBook(rNum, name, cnic, phnNum, gmail, dob);
                Booking.database.add(allPersons);
            }

            int bookPersListSize = Integer.parseInt(reader.readLine());
            Booking.pBook.clear();
            for (int i = 0; i < bookPersListSize; i++) {
                String name = reader.readLine();
                String cnic = reader.readLine();
                String phnNum = reader.readLine();
                String gmail = reader.readLine();
                String dob = reader.readLine();
                int rNum = Integer.parseInt(reader.readLine());
                reader.readLine();
                PersonWhoBook bookP = new PersonWhoBook(rNum, name, cnic, phnNum, gmail, dob);
                Booking.pBook.add(bookP);
            }

            int allRoomListSize = Integer.parseInt(reader.readLine());
            Booking.rLs1.clear();
            for (int i = 0; i < allRoomListSize; i++) {
                String rAvailability = reader.readLine();
                int rBeds = Integer.parseInt(reader.readLine());
                String rFloor = reader.readLine();
                int rNumber = Integer.parseInt(reader.readLine());
                int rPrice = Integer.parseInt(reader.readLine());
                String rStatus = reader.readLine();
                String rType = reader.readLine();
                reader.readLine();
                Room room = new Room(rNumber, rPrice, rType, rBeds, rStatus, rFloor, rAvailability);
                Booking.rLs1.add(room);
            }


            int bookedRoomListSize = Integer.parseInt(reader.readLine());
            Booking.rBook.clear();
            for (int i = 0; i < bookedRoomListSize; i++) {
                String rAvailability = reader.readLine();
                int rBeds = Integer.parseInt(reader.readLine());
                String rFloor = reader.readLine();
                int rNumber = Integer.parseInt(reader.readLine());
                int rPrice = Integer.parseInt(reader.readLine());
                String rStatus = reader.readLine();
                String rType = reader.readLine();
                reader.readLine();
                Room room = new Room(rNumber, rPrice, rType, rBeds, rStatus, rFloor, rAvailability);
                Booking.rBook.add(room);
            }


            // Reading Menu Data
            int menuSize = Integer.parseInt(reader.readLine()); // Read number of items in the menu
            // Clear old menu items before reading new ones
            Menu.menuItems.clear();

            for (int i = 0; i < menuSize; i++) {
                String name = reader.readLine().trim(); // Read item name
                int sizeCount = Integer.parseInt(reader.readLine().trim()); // Read size count

                ArrayList<SizePriceQuantity> sizePriceQuantities = new ArrayList<>();
                for (int j = 0; j < sizeCount; j++) {
                    String size = reader.readLine().trim();
                    double price = Double.parseDouble(reader.readLine().trim());
                    int quantity = Integer.parseInt(reader.readLine().trim());
                    reader.readLine(); // To consume the blank line

                    SizePriceQuantity spq = new SizePriceQuantity(size, price, quantity);
                    sizePriceQuantities.add(spq);
                }

                FoodItem foodItem = new FoodItem(name, sizePriceQuantities);
                Menu.menuItems.add(foodItem); // Add food item to the list
            }


            //Reading Orders
            int orderListSize = Integer.parseInt(reader.readLine().trim());
            Restaurant.orders.clear(); // Clear old orders before reading new ones

            for (int i = 0; i < orderListSize; i++) {
                int fileOrderId = Integer.parseInt(reader.readLine().trim()); // Read order ID
                int itemCount = Integer.parseInt(reader.readLine().trim()); // Number of items in the order

                Order order = new Order(new ArrayList<>(), false);
                //Order.setId(fileOrderId); // Explicitly set the ID for the order

                for (int j = 0; j < itemCount; j++) {
                    String itemName = reader.readLine().trim();
                    int quantity = Integer.parseInt(reader.readLine().trim());
                    String size = reader.readLine().trim();
                    double price = Double.parseDouble(reader.readLine().trim());

                    OrderItem orderItem = new OrderItem(itemName, size, quantity, price);
                    order.getMyorder().add(orderItem);
                }

                Restaurant.orders.add(order); // Add order to the list
            }


            System.out.println("Data has been loaded from database.txt successfully.");
        } catch (IOException e) {
            e.printStackTrace(); // Handle any I/O exceptions
        } catch (NumberFormatException e) {
            System.out.println("Error parsing integer values from the file.");
            e.printStackTrace();
        }
    };

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Dashboard.class.getResource("/com/example/Lodgify/dashboard.fxml"));
        fxmlLoader.setController(new Dashboard());
        read.data();

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hotel Management System");
        stage.setScene(scene);
        stage.show();
    }


}










