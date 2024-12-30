package com.example.Lodgify.RestaurantManagement;
// UNUSED IN THE FRONTEND NO WAY TO USE THESE
public enum RestrauntStatus {
    EXIT, MANAGE_MENU, DISPLAY_MENU, PLACE_ORDER, PREVIOUS_ORDERS,INVALID;

    final public static RestrauntStatus chooseStatus(int choice) {
        RestrauntStatus value;
        switch (choice) {
            case 0:
                return EXIT;
            case 1:
                return MANAGE_MENU;
            case 2:
                return DISPLAY_MENU;
            case 3:
                return PLACE_ORDER;
            case 4:
                return PREVIOUS_ORDERS;
            default:
                return INVALID;
        }

    }
}