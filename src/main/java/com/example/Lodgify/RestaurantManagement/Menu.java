package com.example.Lodgify.RestaurantManagement;

import com.example.Lodgify.Tools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Menu implements Tools {


//  Ui controllers
    private Restaurant previous;
    public void setPrevious(Restaurant previous) {
        this.previous = previous;
    }
//  Main Logic


    FoodItem item;

    @FXML
    private Button addMenuButton;
    @FXML
    private Button updateMenuButton;
    @FXML
    private Button deleteMenuButton;
    @FXML
    private Button viewMenuButton;
    @FXML
    private Button exitButton;
    @FXML
    private ListView<String> itemList;
    @FXML
    private Button displayBack;
    @FXML
    private TextField nameInput;

    @FXML
    private Text itemDetailText;


    public static ArrayList<FoodItem> menuItems = new ArrayList<>();


    public void nextProccessing() {
        // Check if the item name is set

        if (item.getName() == null || item.getName().trim().isEmpty() || item.getName().equals("no name")){
            showAlert("ERROR", "ITEM NAME IS INVALID OR EMPTY");
            return;
        }
        // Check for duplication before adding the item
        if (duplicateChecker(item)) {
            showAlert("ERROR", "DUPLICATE ITEM FOUND");
        } else {
            showAlert("RESULT", "Item added successfully");
            menuItems.add(item);
        }
    }



    // Triggered by clicking the Add button
    @FXML
    private void handleAddMenu(ActionEvent event) {
        item=new FoodItem();
        item.setPrevious(this);
        switchScene(event, "/com/example/lodgify/RestaurantManagement/FoodItem.fxml", "Food Item", item);
    }

    // Triggered by clicking the Update button
    @FXML
    private void handleUpdateMenu(ActionEvent event) {
        if (menuItems.isEmpty()){
            showAlert("ERROR","NO MENU ITEM TO UPDATE");
        }
        else {
            switchScene(event, "/com/example/lodgify/RestaurantManagement/UpdateMenu.fxml", "Menu", this);
        }
    }

    @FXML
    private void updateMenu(ActionEvent event) {
        String name = nameInput.getText().trim();
        if (name.isEmpty()) {
            showAlert("Error", "Please enter the name of the item to delete.");
            return;
        }

        int index = findItemIndexByName(name);
        if (index == -1) {
            showAlert("Error", "Item not found.");
            return;
        }
        menuItems.remove(index);
        handleAddMenu(event);
    }

    @FXML
    public void exitToMenu(ActionEvent event){
        switchScene(event,"/com/example/lodgify/RestaurantManagement/Menu.fxml","Menu",this);
    }

    // Triggered by clicking the Delete button
    @FXML
    private void handleDeleteMenu(ActionEvent event) {
        if (menuItems.isEmpty()){
            showAlert("ERROR","NO MENU ITEM TO UPDATE");
        }
        else {
            switchScene(event, "/com/example/lodgify/RestaurantManagement/DeleteMenu.fxml", "Menu", this);
        }
    }

    @FXML
    public void deleteMenu(ActionEvent event) {
        String name = nameInput.getText().trim();
        if (name.isEmpty()) {
            showAlert("Error", "Please enter the name of the item to update.");
            return;
        }

        int index = findItemIndexByName(name);
        if (index == -1) {
            showAlert("Error", "Item not found.");
            return;
        }

        menuItems.remove(index);
        showAlert("Success", "Item deleted successfully.");
        //controller is same class , just javafx file scene shift.
        switchScene(event,"/com/example/lodgify/RestaurantManagement/Menu.fxml","Menu",this);
    }


    // TO VIEW THE MENU
    @FXML
    public void handleViewMenu(ActionEvent event) {
        if (menuItems.isEmpty()) {
            showAlert("ERROR", "NO MENU ITEM FOUND");
        }
        else {
            switchScene(event,"/com/example/lodgify/RestaurantManagement/DisplayMenu.fxml","Displaying data",this);
            itemList.getItems().clear();
            for (FoodItem item : menuItems) {

                itemList.getItems().add(item.getName());
            }
            itemDetailText.setText("Click on the item to see its detail");
        }
    }
//  IN MENU DISPALY IT WORK SO USER CLICK ON ITEM TO SEE IT'S DETAIL

    @FXML
    public void handleItemClick(){

        String selectedItem = itemList.getSelectionModel().getSelectedItem();

        // If an item is selected, update the detail text
        for (FoodItem item : menuItems) {
            if (item.getName().equals(selectedItem)) {
                itemDetailText.setText(item.toString());
            }
        }

    }
//    TO EXIT TO THE RESTRAUNT
    @FXML
    private void handleExit(ActionEvent event) {
                switchScene(event, "/com/example/lodgify/RestaurantManagement/Restraunt.fxml", "Restaurant Management System",previous);
    }

    // Utility Methods
//  TO CHECK IS AN ITEM IS DUPLICATED OR NOT
    private boolean duplicateChecker(FoodItem item) {

        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getName().equals(item.getName())) {
                return true;
            }
        }
        return false;

    }

//  TO CHECK THE INDEX OF ORDER ITEM
    private int findItemIndexByName(String name) {
        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;

    }
}
