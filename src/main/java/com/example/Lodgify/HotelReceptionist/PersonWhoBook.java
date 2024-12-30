package com.example.Lodgify.HotelReceptionist;

//import com.example.lodgify.Generic.Individual;

import com.example.Lodgify.Administration.Regex;
import com.example.Lodgify.Generic.Individual;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class PersonWhoBook extends Individual implements Regex {
    private int rNumber;


    public PersonWhoBook(int rNumber, String name, String cnic, String phoneNumber, String gmail, String dob) {
        super(name, cnic, phoneNumber, gmail, dob);
        this.rNumber = rNumber;

    }

    //constructor overloading.
    public PersonWhoBook(int num) {
        setCnic(null);
        loadIndividual(num);
    }


    public Integer getRNumber() {
        return rNumber;
    }

    public void setrNumber(int rNumber) {
        this.rNumber = rNumber;
    }


    public void loadIndividual(int rNumber) {

        // Create a dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Enter Customer Details");

        // Create labels and text fields for input
        Label nameLabel = new Label("Customer Name:");
        TextField nameField = new TextField();

        Label cnicLabel = new Label("CNIC:");
        TextField cnicField = new TextField();

        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();

        Label gmailLabel = new Label("Gmail:");
        TextField gmailField = new TextField();

        Label dobLabel = new Label("Date of Birth (YYYY-MM-DD):");
        TextField dobField = new TextField();

        // Arrange labels and fields in a grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);

        grid.add(cnicLabel, 0, 1);
        grid.add(cnicField, 1, 1);

        grid.add(phoneLabel, 0, 2);
        grid.add(phoneField, 1, 2);

        grid.add(gmailLabel, 0, 3);
        grid.add(gmailField, 1, 3);

        grid.add(dobLabel, 0, 4);
        grid.add(dobField, 1, 4);

        // Set the content of the dialog
        dialog.getDialogPane().setContent(grid);

        // Add buttons to the dialog
        ButtonType loadButtonType = new ButtonType("Book", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loadButtonType, ButtonType.CANCEL);

        // Show dialog and capture user input
        Node bookButton = dialog.getDialogPane().lookupButton(loadButtonType);
        bookButton.addEventFilter(ActionEvent.ACTION, event -> {
            String name = nameField.getText();
            String cnic = cnicField.getText();
            String pNum = phoneField.getText();
            String gmail = gmailField.getText();
            String dob = dobField.getText();

            if (name.isEmpty() || cnic.isEmpty() || gmail.isEmpty() || pNum.isEmpty() || dob.isEmpty() ) {
                    showError("All fields must be filled.");
                    event.consume(); // Prevent dialog from closing
                    return ;

                }

                if (!name.matches(NAME)) {
                    showError("Name must be alphabetic");
                    event.consume();
                    return ; // Prevent closing
                }
                if (!cnic.matches(CNIC)) {
                    showError("CNIC must be like XXXXX-XXXXXXX-X");
                    event.consume();
                    return ; // Prevent closing
                }
                if (!pNum.matches(PHONE)) {
                    showError("Phone number must be like XXXX-XXXXXXX");
                    event.consume();
                    return ; // Prevent closing
                }
                if (!gmail.matches(GMAIL)) {
                    showError("Email must be like username@gmail.com");
                    event.consume();
                    return ; // Prevent closing
                }
                if (!dob.matches(DOB)) {
                    showError("Date of Birth must be in YYYY-MM-DD format");
                    event.consume();
                    return ; // Prevent closing
                }
            for(PersonWhoBook pbk:Booking.pBook){
                if(cnic.equals(pbk.getCnic())){
                    showError("Customer with this CNIC already exixts.");
                    event.consume();
                    return;
                }
                if(pNum.equals(pbk.getPhoneNumber())){
                    showError("This Phone Number already exists.");
                    event.consume();
                    return;
                }
                if(gmail.equals(pbk.getGmail())){
                    showError("This Gmail already exists.");
                    event.consume();
                    return;
                }

            }

                // Set instance variables if all validations pass
                setName(name);
                setCnic(cnic);
                setPhoneNumber(pNum);
                setGmail(gmail);
                setDob(dob);
                this.rNumber = rNumber;

        });

        dialog.showAndWait();
    }


    // Helper method to display error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}







































