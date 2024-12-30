package com.example.Lodgify.RestaurantManagement;
// UNUSED IN THE FRONTEND NO WAY TO USE THESE

public enum ItemStatus {
    EXIT,ADD,UPDATE,REMOVE,VIEW,VIEWBILL,PLACEORDER,INVALID;

     final  public static  ItemStatus chooseStatus(int choice) {
         switch (choice) {
             case 0:
                 return EXIT;
                 case 1:
                     return ADD;
                     case 2:
                         return UPDATE;
                         case 3:
                             return REMOVE;
                             case 4:
                                 return VIEW;
                                 case 5:
                                     return VIEWBILL;
                                     case 6:
                                         return PLACEORDER;
                                         default:
                                             return INVALID;
         }


    }
}
