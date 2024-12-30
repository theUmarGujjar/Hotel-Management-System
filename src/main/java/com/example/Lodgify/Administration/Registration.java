package com.example.Lodgify.Administration;

import com.example.Lodgify.Generic.Individual;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import com.example.Lodgify.Dashboard;

import java.util.concurrent.atomic.AtomicReference;

public class Registration extends Individual implements Regex  {



    private String privateQuestion;

    // FunFact of Programming.
    // Constructor is Private.
    private Registration(String name, String cnic, String phoneNumber, String gmail, String dob,String username, String password,String privateQuestion) {
        super(name, cnic, phoneNumber, gmail, dob,username, password);
        this.privateQuestion = privateQuestion;
    }

    public void setPrivateQuestion(String privateQuestion) {
        this.privateQuestion = privateQuestion;
    }

    public String getPrivateQuestion(){
        return privateQuestion;
    }

    public static Registration loadRegisteration(String name, String cnic, String phoneNumber, String gmail, String dob,String username, String password,String privateQuestion){
        Registration admin = new Registration(name, cnic, phoneNumber, gmail, dob, username, password,privateQuestion);
        return admin;
    }

    public static void RegisterAdmin(){
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Licence Registration");
        dialog.setHeaderText("Enter details carefully for registration:");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

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
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        usernameField.setPromptText("Username");
        TextField questionField = new TextField();
        questionField.setPromptText("Childhood Friend ? ");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("CNIC:"), 0, 1);
        grid.add(cnicField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Phone Number:"), 0, 3);
        grid.add(phoneNumberField, 1, 3);
        grid.add(new Label("Date of Birth:"), 0, 4);
        grid.add(dobField, 1, 4);
        grid.add(new Label("Username:"), 0, 5);
        grid.add(usernameField, 1, 5);
        grid.add(new Label("Password:"), 0, 6);
        grid.add(passwordField, 1, 6);
        grid.add(new Label("Enter your Childhood Frined's name ?"), 0, 7);
        grid.add(questionField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        final Button addButton = (Button) dialog.getDialogPane().lookupButton(addButtonType);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
                String name = nameField.getText();
                String cnic = cnicField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneNumberField.getText();
                String dob = dobField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                String question  = questionField.getText().trim();
                // Validate required fields
                if (name.isEmpty() || cnic.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || dob.isEmpty() || username.isEmpty() || password.isEmpty() || question.isEmpty()) {
                    showInfo("All fields must be filled.");
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

                //Self composition of the object of a class in the class itself
                 Registration admin = new Registration(name, cnic, phoneNumber, email, dob, username, password, question);

                // Just for making sure that it's time of license registration.
                if(Dashboard.signupList.isEmpty()){
                    Dashboard.signupList.add(admin);
                }
        });
        dialog.showAndWait();


    }


        public static void showError(String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(message);
            alert.showAndWait();
        }

        public static void showInfo(String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText(message);
            alert.showAndWait();
        }
}
