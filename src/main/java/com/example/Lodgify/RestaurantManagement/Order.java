package com.example.Lodgify.RestaurantManagement;
import com.example.Lodgify.Tools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Scanner;

import static com.example.Lodgify.RestaurantManagement.Restaurant.menu;

public class Order implements Tools {
//  UI COMPONENTS
    private Restaurant previous;
    public void setPrevious(Restaurant previous) {
        this.previous = previous;
    }

// CONTROLLERS
    @FXML
    private Button add, update, delete, view, place, bill, exitButton;

    @FXML
    private TextArea nameArea;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private Label sizeErrorLabel;

    @FXML
    private Label priceErrorLabel;

    @FXML
    private Label quantityErrorLabel;


    @FXML
    public void exitToOrder(ActionEvent event){
        switchScene(event,"/com/example/lodgify/RestaurantManagement/Order.fxml","Order",this);
    }

    @FXML
    public void addItem(ActionEvent event) {
        item=new OrderItem();
        item.setPrevious(this);
        switchScene(event,"/com/example/lodgify/RestaurantManagement/addOrderItem.fxml","Order item",this);
    }

    @FXML
    public void handleSubmitButton(ActionEvent event){
        boolean isValid = true;

        FoodItem foodItem;
        double foodItemPrice=0;
        boolean sizefound=false;
        double foodItemQuantity=0;
        String allsizes="";

        // Validate name
        String name = nameArea.getText().trim().toLowerCase();

        int menuIndex=findItemIndexByName(name,previous.menu.menuItems);


        if(menuIndex != -1) {
            nameErrorLabel.setText("");
            foodItem = previous.menu.menuItems.get(menuIndex);


            // Validate size
            String selectedSize = sizeComboBox.getValue();

            for (SizePriceQuantity s:foodItem.getSizePriceQuantities()){
                    if(s.getSize().equals(selectedSize)){
                        sizefound=true;
                        foodItemPrice=s.getPrice();
                        foodItemQuantity=s.getQuantity();
                    }
                    allsizes+=s.getSize()+" ";
            }


            if (selectedSize == null) {
                sizeErrorLabel.setText("Please select a size.");
                isValid = false;
            } else if (!sizefound) {
                sizeErrorLabel.setText("Available sizes are "+ allsizes);
            } else {
                sizeErrorLabel.setText("");
            }

            // Validate quantity
            String quantityText = quantityField.getText().trim();
            int quantity=0;
            try {
                quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    quantityErrorLabel.setText("Quantity must be greater than 0.");
                    isValid = false;
                } else if (quantity>foodItemQuantity) {
                    quantityErrorLabel.setText("Quantity must be less than or equal to "+foodItemQuantity);
                    isValid = false;
                } else {
                    quantityErrorLabel.setText("");
                }
            } catch (NumberFormatException e) {
                quantityErrorLabel.setText("Invalid quantity. Please enter a whole number.");
                isValid = false;
            }

            // If all inputs are valid, process the data
            if (isValid) {
                OrderItem item = new OrderItem(name, selectedSize, quantity,(foodItemPrice*quantity));

                for (OrderItem i:myorder){
                    if (i.getItemName().equals(name) && i.getSize().equals(selectedSize)){
                        showAlert("Error","ITEM ALREADY EXIST, KINDLY UPDATE IT");
                        switchScene(event,"/com/example/lodgify/RestaurantManagement/Order.fxml","Order",this);
                        return;
                    }
                }
                myorder.add(item);
                showAlert("RESULT","Order Item added successfully");
                switchScene(event,"/com/example/lodgify/RestaurantManagement/Order.fxml","Order",this);
            }


        }
        else {
            nameErrorLabel.setText("Item with this name is not found");
        }

    }


    @FXML
    public void updateItem(ActionEvent event) {
        if (myorder.isEmpty()){
            showAlert("ERROR","NO ORDER ITEM AVAILABLE TO UPDATE");
        }
        else {
            switchScene(event, "/com/example/lodgify/RestaurantManagement/updateOrderItem.fxml", "Update Item", this);
        }
    }
    @FXML
    public void handleUpdateButton(ActionEvent event){
        String name="";
        String size="";
        boolean isValid=true;

        if(nameArea.getText().trim().isEmpty()){
            nameErrorLabel.setText("Please enter a name.");
            isValid=false;
        }else {
            name=nameArea.getText().trim();
        }

        size=sizeComboBox.getValue();
        if(size == null){
            sizeErrorLabel.setText("Please choose a size");
            isValid=false;
        }

        int index=findItemIndexFromOrder(name,size);

        if(isValid){
            if (index == -1){
                showAlert("Error","Item not found");
            }
            else {
                myorder.remove(index);
                switchScene(event,"/com/example/lodgify/RestaurantManagement/addOrderItem.fxml","Update",this);
            }
        }

    }

    @FXML
    public void deleteItem(ActionEvent event){
        if(myorder.isEmpty()){
            showAlert("ERROR","NO ORDER ITEM AVAILABLE TO DELETE");
        }
        else {
            switchScene(event, "/com/example/lodgify/RestaurantManagement/deleteOrderItem.fxml", "Delete Order Item", this);
        }

    }
    @FXML
    public void handleDeleteButton(ActionEvent event){
        String name="";
        String size="";
        boolean isValid=true;

        if(nameArea.getText().trim().isEmpty()){
            nameErrorLabel.setText("Please enter a name.");
            isValid=false;
        }else {
            name=nameArea.getText().trim();
        }

        size=sizeComboBox.getValue();
        if(size == null){
            sizeErrorLabel.setText("Please choose a size");
            isValid=false;
        }

        int index=findItemIndexFromOrder(name,size);

        if(isValid){
            if (index == -1){
                showAlert("ERRO","ITEM NOT FOUDN IN MENU");
            }
            else {
               myorder.remove(index);
               switchScene(event,"/com/example/lodgify/RestaurantManagement/Order.fxml","Order",this);
               showAlert("DISPLAY","ITEM REMOVED SUCCESSFULLY");
            }
        }



    }


    @FXML
    public void display(ActionEvent event){

        System.out.println(myorder.size());
        if(myorder.isEmpty()){
            showAlert("ERROR","NO ORDER ITEM TO DISPLAY");
        }
        else {
            StringBuilder str = new StringBuilder();
            int num = 0;

            str.append("**********************************\n")
                    .append("          ORDER SUMMARY          \n")
                    .append("**********************************\n\n")
                    .append("Order ID: ").append(orderID).append("\n\n")
                    .append("Items:\n")
                    .append("----------------------------------------------------\n");

            for (OrderItem orderItem : myorder) {
                str.append(String.format("%2d. %-20s | Size: %-5s | Qty: %2d\n",
                        ++num,
                        orderItem.getItemName(),
                        orderItem.getSize(),
                        orderItem.getQuantity()));
            }

            str.append("----------------------------------------------------\n")
                    .append("Thank you for your order! 😊");
            showAlert("DISPLAY ORDER",str.toString());

        }

    }
//
    @FXML
    public void viewBill(){
        // Display the bill using alert
        showAlert("DISPLAY BILL",toString());
    }
//
    @FXML
    public void placeOrder(ActionEvent event){
       if(myorder.isEmpty()){
           showAlert("ERROR","NO ORDER ITEM FOUND");
       }else {
           viewBill();
           updateMenu();
           setOrdered(true);
           previous.orders.add(this);
           switchScene(event,"/com/example/lodgify/RestaurantManagement/Restraunt.fxml","Restrant",this.previous);
       }
    }
    @FXML
    public void exit(ActionEvent event) {
        id--;
        switchScene(event,"/com/example/lodgify/RestaurantManagement/Restraunt.fxml","Restraunt",previous);
        showAlert("ERROR","OOPS! ORDER NOT PLACED");
    }


    private static int id;
    private final int orderID = ++id;
    private boolean ordered;
    private ArrayList<OrderItem> myorder;
    OrderItem item;

    Scanner input=new Scanner(System.in);


//    constructor

    public Order(){
        this(new ArrayList<>(), false);
    }

    public Order(ArrayList<OrderItem> myorder,boolean ordered) {
        setMyorder(myorder);
        setOrdered(ordered);
    }

    //    getter and setters
    public int getid(){
        return id;
    }
    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public ArrayList<OrderItem> getMyorder() {
        return myorder;
    }

    public void setMyorder(ArrayList<OrderItem> myorder) {
        this.myorder = myorder;
    }

    public void print() {
        System.out.println("Order ID: " + orderID);
    }

    //
    public void updateMenu(){
        int buyQuantity;
        int menuQuantity;

        if(!isOrdered()){
            for(int i=0;i< myorder.size();i++){
                for(int j = 0; j< menu.menuItems.size(); j++){
                    if(myorder.get(i).getItemName().equals(menu.menuItems.get(j).getName())){
                        for(int k = 0; k< menu.menuItems.get(j).getSizePriceQuantities().size(); k++){
                            if(menu.menuItems.get(j).getSizePriceQuantities().get(k).getSize().equalsIgnoreCase(myorder.get(i).getSize())){
                                menuQuantity= menu.menuItems.get(j).getSizePriceQuantities().get(k).getQuantity();
                                buyQuantity=myorder.get(i).getQuantity();
                                menu.menuItems.get(j).getSizePriceQuantities().get(k).setQuantity(menuQuantity-buyQuantity);
                            }

                        }
                    }
                }
            }
        }
        else{
            System.out.println("Order is already placed");
        }


    }
//
    private int findItemIndexByName(String name,ArrayList<FoodItem> menuItems) {
        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private int findItemIndexFromOrder(String name,String size) {
        ArrayList<OrderItem> items = null;
        for (int i = 0; i < myorder.size(); i++) {
            if (myorder.get(i).getItemName().equalsIgnoreCase(name) && myorder.get(i).getSize().equalsIgnoreCase(size)) {
                return i;
            }
        }
        return -1;

    }

    @Override
    public String toString() {
        double total = 0;
        StringBuilder bill = new StringBuilder();

        for (OrderItem orderItem : myorder) {
            total += orderItem.getPrice();
        }

        if (myorder.size() == 0) {
            bill.append("There is no item available in the order");
        } else {
            int num = 0;
            bill.append("Order ID: ").append(orderID).append("\n\n");
            for (OrderItem orderItem : myorder) {
                bill.append(++num).append(". ")
                        .append(orderItem.getItemName()).append("     ====>     ")
                        .append("Size: ").append(orderItem.getSize()).append("     ")
                        .append("Quantity: ").append(orderItem.getQuantity()).append("     ")
                        .append("Price: ").append(orderItem.getPrice()).append("\n");
            }
            bill.append("\n");
            bill.append("                                              -------------------------------\n");
            bill.append("                                               Total: ").append(total).append("\n");
        }
        return bill.toString();
    }
}
