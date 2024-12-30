module com.example.Lodgify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.compiler;
    opens com.example.Lodgify.RestaurantManagement to javafx.fxml;
    opens com.example.Lodgify.HotelReceptionist to javafx.fxml;
    opens com.example.Lodgify.Administration to javafx.fxml;
    opens com.example.Lodgify.Generic;
    opens com.example.Lodgify.Staff;

    exports com.example.Lodgify.HotelReceptionist;
    exports com.example.Lodgify.Administration;
    exports com.example.Lodgify.Generic;
    exports com.example.Lodgify.Staff;
    exports com.example.Lodgify.RestaurantManagement to javafx.fxml;




    opens com.example.Lodgify to javafx.fxml;
    exports com.example.Lodgify;
}