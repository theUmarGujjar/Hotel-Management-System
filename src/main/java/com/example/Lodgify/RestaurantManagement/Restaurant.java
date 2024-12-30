package com.example.Lodgify.RestaurantManagement;

import com.example.Lodgify.Dashboard;
import com.example.Lodgify.Tools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class  Restaurant  implements Tools {

//    Ui Tools
    private Dashboard previous;
    public void setPrevious(Dashboard previous) {
        this.previous = previous;
    }


    public static ArrayList<Order> orders = new ArrayList<>();
    public static Menu menu = new Menu();
    Order order;


    public static void setMenu(Menu menu) {
        Restaurant.menu = menu;
    }

    @FXML
    private Button manageMenuButton;

    @FXML
    private Button displayMenuButton;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Button previousOrdersButton;

    @FXML
    private Button exitButton;


    // Handle "Manage Menu" button click
    @FXML
    private void handleManageMenu(ActionEvent event) {
            menu.setPrevious(this);
            switchScene(event,"/com/example/lodgify/RestaurantManagement/menu.fxml","Menu",menu);
    }



    // Handle "Display Menu" button click
    @FXML
    private void handleDisplayMenu(ActionEvent event) {
        menu.handleViewMenu(event);
    }



    // Handle "Place Order" button click
    @FXML
    private void handlePlaceOrder(ActionEvent event) {
        if (menu.menuItems.isEmpty()) {
            showAlert("ERROR", "MENU IS CURRENTLY EMPTY");
        } else {
            order = new Order();
            order.setPrevious(this);
            switchScene(event,"/com/example/lodgify/RestaurantManagement/Order.fxml","Order",order);
        }

    }

    // Handle "Previous Orders" button click
    @FXML
    private void handlePreviousOrders() {
        if (orders.isEmpty()) {
            showAlert("ERROR", "NO PREVIOUS ORDER FOUND.");
        } else {
            StringBuilder previousOrdersContent = new StringBuilder("Previous Orders:\n");
            for (Order order : orders) {
                previousOrdersContent.append(order.toString()).append("\n");
            }
            showAlert("PREVIOUS ORDERS", previousOrdersContent.toString());
        }



    }

    // Handle "Exit" button click
    @FXML
    private void handleExit(ActionEvent event) {
        switchScene(event,"/com/example/lodgify/dashboard.fxml","Hotel Management System",previous);

    }
}