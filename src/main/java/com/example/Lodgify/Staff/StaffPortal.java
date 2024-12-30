package com.example.Lodgify.Staff;
import com.example.Lodgify.Dashboard;
import com.example.Lodgify.Tools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import static com.example.Lodgify.Administration.EmployeeManager.*;
import static com.example.Lodgify.Dashboard.showLoginDialog;

public class StaffPortal implements Tools {

    //   UI COMPONOENTS
    private Dashboard previous;
    public void setPrevious(Dashboard previous){
        this.previous=previous;
    }
    public static Driver driver=new Driver();
    Sweeper sweeper=new Sweeper();

    @FXML
    private Button carsButton;

    @FXML
    private Button employeesButton;

    @FXML
    private ImageView logoImageView;

    @FXML
    void shiftToDriver(ActionEvent event) {
        driver.setPrevious(this);
        if(!driverList.isEmpty()){
            String[] credentials = showLoginDialog();
            Boolean login=false;
            if (credentials != null) {
                String username = credentials[0];
                String password = credentials[1];
                for (int i=0;i<driverList.size();i++){
                    login=driverList.get(i).aut.checkAuthorization(username,password);
                    if(login==true){
                        break;
                    }
                }
            }
            if(login==true) {
                switchScene(event,"/com/example/Lodgify/DrivePortal.fxml","Driver",driver);
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
            alert.setContentText("There is no Driver in the Database");
            alert.showAndWait();
        }

    }

    @FXML
    void  shiftToSweeper(ActionEvent event) {
        sweeper.setPrevious(this);
        if(!sweeperList.isEmpty()){
            String[] credentials = showLoginDialog();
            Boolean login=false;
            if (credentials != null) {
                String username = credentials[0];
                String password = credentials[1];
                for (int i=0;i<sweeperList.size();i++){
                    login=sweeperList.get(i).aut.checkAuthorization(username,password);
                    if(login==true){
                        break;
                    }
                }
            }
            if(login==true) {
                switchScene(event,"/com/example/Lodgify/SweeperPortal.fxml","Sweeper",sweeper);
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
            alert.setContentText("There is no Sweeper in the Database");
            alert.showAndWait();
        }



    }
    @FXML
    public void exitToDashboard(ActionEvent event){
        switchScene(event,"/com/example/Lodgify/dashboard.fxml","Hotel Management System",previous);
    }


}
