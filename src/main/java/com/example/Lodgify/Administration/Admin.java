package com.example.Lodgify.Administration;
import com.example.Lodgify.Dashboard;
import com.example.Lodgify.Tools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Admin implements Tools {

    //    Ui Tools
    private Dashboard previous;
    public void setPrevious(Dashboard previous) {
        this.previous = previous;
    }

    EmployeeManager employee=new EmployeeManager();
    CarManager car=new CarManager();
    RoomManager room=new RoomManager();

    public EmployeeManager getEmployee() {
        return employee;
    }

    public CarManager getCar() {
        return car;
    }

    public RoomManager getRoom() {
        return room;
    }

    public void setRoom(RoomManager room) {
        this.room = room;
    }

    public void setCar(CarManager car) {
        this.car = car;
    }

    public void setEmployee(EmployeeManager employee) {
        this.employee = employee;
    }



    @FXML
    private Button carsButton;

    @FXML
    private Button employeesButton;

    @FXML
    private Button exit;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Button roomsButton;

    @FXML
    void employee(ActionEvent event) {
            employee.setPrevious(this);
            switchScene(event,"/com/example/Lodgify/AdministrationManager.fxml","Employee",employee);

    }

    @FXML
    void cars(ActionEvent event) {
        car.setPrevious(this);
        switchScene(event, "/com/example/Lodgify/CarRoom.fxml","Car",car);

    }


    @FXML
    void rooms(ActionEvent event) {
        room.setPrevious(this);
        switchScene(event, "/com/example/Lodgify/CarRoom.fxml","Rooms",room);


    }


    @FXML
    private void exitToDashboard(ActionEvent event) {
        switchScene(event,"/com/example/lodgify/dashboard.fxml","Hotel Management System",previous);
    }


}
