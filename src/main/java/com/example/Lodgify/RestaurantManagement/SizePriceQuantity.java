package com.example.Lodgify.RestaurantManagement;
import com.example.Lodgify.Tools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SizePriceQuantity implements Tools {

//  UI COMPONENT
    private FoodItem previous;
    public void setPrevious(FoodItem previous) {
        this.previous = previous;
    }

//  CONTROLLER CODE
    @FXML
    private TextField sizeField;
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
    private Label globalErrorLabel;



    // Handle submit button action
    @FXML
    private void handleSubmitButton(ActionEvent event) {
        boolean valid = true;
        globalErrorLabel.setText("");  // Clear global errors

        // Validate Size
        try {
            String size = sizeField.getText().trim();
            this.setSize(size);
            sizeErrorLabel.setText("");  // Clear previous error
        } catch (IllegalArgumentException e) {
            sizeErrorLabel.setText(e.getMessage());
            valid = false;
        }

        // Validate Price
        try {
            double price = Double.parseDouble(priceField.getText().trim());
            this.setPrice(price);
            priceErrorLabel.setText("");  // Clear previous error
        } catch (NumberFormatException e) {
            priceErrorLabel.setText("Price must be a valid number");
            valid = false;
        } catch (IllegalArgumentException e) {
            priceErrorLabel.setText(e.getMessage());
            valid = false;
        }

        // Validate Quantity
        try {
            int quantity = Integer.parseInt(quantityField.getText().trim());
            this.setQuantity(quantity);
            quantityErrorLabel.setText("");  // Clear previous error
        } catch (NumberFormatException e) {
            quantityErrorLabel.setText("Quantity must be a valid number");
            valid = false;
        } catch (IllegalArgumentException e) {
            quantityErrorLabel.setText(e.getMessage());
            valid = false;
        }

        // If everything is valid, show the summary, else show global error
        if (valid) {
            switchScene(event,"/com/example/lodgify/RestaurantManagement/FoodItem.fxml","FoodItems",previous);
            previous.nextProccessing();

        } else {
            globalErrorLabel.setText("     Please Correct Errors   ");
        }
    }

//    MAIN LOGIC

    private String size;
    private double price;
    private int quantity;

    public SizePriceQuantity() {
        this("none",0.0,0);
    }

    public SizePriceQuantity(String size, double price, int quantity) {
        setSize(size);
        setPrice(price);
        setQuantity(quantity);
    }

//  copy constructor

    public SizePriceQuantity(SizePriceQuantity s){
        this(s.getSize(),s.getPrice(),s.getQuantity());
    }


//   getters and setter

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        size = size.trim().toLowerCase();
        if (size.equals("xl") || size.equals("l") || size.equals("n") || size.equals("s") || size.equals("xs") || size.equals("none")) {
            this.size = size;

        } else {
            throw new IllegalArgumentException("SIZE MUST BE xl, l, n, s, xs");

        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price<0){
            throw new IllegalArgumentException("PRICE CANNOT BE NEGATIVE");
        }
        else{
            this.price = price;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity<0){
            throw new IllegalArgumentException("QUANTITY CANNOT BE NEGATIVE");
        }
        else{
            this.quantity = quantity;
        }

    }

//  DISPLAY METHODS

    public void display(){
        System.out.println("Size: " + getSize());
        System.out.println("Price: " + getPrice());
        System.out.println("Quantity: " + getQuantity());
    }

    @Override
    public String toString() {
        return "\nAvailabe data [size='" + getSize() + "', price=" + getPrice() + ", quantity=" + getQuantity() + "]";
    }

}
