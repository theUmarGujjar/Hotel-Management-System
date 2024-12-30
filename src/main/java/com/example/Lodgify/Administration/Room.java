package com.example.Lodgify.Administration;

import java.util.Scanner;

public class Room {
    private static final Scanner scanner = new Scanner(System.in);

    // Encapsulated instance variables..
    public int rNumber;
    private int rPrice;
    private String rType;
    private int rBeds;
    public String rStatus;
    private String rFloor;
    private String rAvailability;

    public Room(){

    }

    public Room(int rNumber, String rStatus) {
        this.rNumber=rNumber;
        this.rStatus=rStatus;
    }


    //constructor..
    public Room(int rNumber , int rPrice , String rType , int rBeds , String rStatus , String rFloor , String rAvailability){
        setrNumber(rNumber);
        setrPrice(rPrice);
        setrType(rType);
        setrBeds(rBeds);
        setrStatus(rStatus);
        setrFloor(rFloor);
        setrAvailability(rAvailability);
    }

    // setter functions..

    void setrInfo(int rNumber , int rPrice , String rType , int rBeds , String rStatus , String rFloor,String rAvailability ){
        setrNumber(rNumber);
        setrPrice(rPrice);
        setrType(rType);
        setrBeds(rBeds);
        setrStatus(rStatus);
        setrFloor(rFloor);
        setrAvailability(rAvailability);

    }

    public void setrNumber(int rNumber){
        this.rNumber = rNumber;
    }
    public void setrPrice(int rPrice){
        this.rPrice = rPrice;
    }
    public void setrType(String rType) {
            this.rType = rType;
    }
    void setrBeds(int rBeds){
        this.rBeds = rBeds;
    }
    public void setrStatus(String rStatus) {
        this.rStatus = rStatus;
    }
    void setrFloor(String rFloor) {
            this.rFloor = rFloor;
    }

    public void setrAvailability(String rAvailability) {
        this.rAvailability = rAvailability;
    }



    //getters functions..
    public int getRNumber(){
        return rNumber;
    }
    public int getRPrice(){
        return rPrice;
    }
    public String getRType(){
        return rType;
    }
    public int getRBeds(){
        return rBeds;
    }
    public String getRStatus(){
        return rStatus;
    }
    public String getRFloor(){
        return rFloor;
    }
    public String getRAvailability(){
        return rAvailability;
    }

    public void show(){
        System.out.println("Room number is : " + rNumber);
        System.out.println("Room price is : " + rPrice);
        System.out.println("Room type is : " + rType);
        System.out.println("Room beds is : " + rBeds);
        System.out.println("Room status is : " + rStatus);
        System.out.println("Room floor is : " + rFloor);
        System.out.println("Room avaialbility is : " + rAvailability);

        System.out.println();
    }

}