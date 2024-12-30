package com.example.Lodgify.Administration;
import java.util.Scanner;

public class ReceptionistHotel extends Employee{
    private static Scanner sc = new Scanner(System.in);


    Scanner input = new Scanner(System.in);
    public ReceptionistHotel(){
        super();
    };

    public ReceptionistHotel(String name , String cnic , String phoneNumber , String gmail , String dob,int salary,String userName,String passWord){
        super(name, cnic, phoneNumber, gmail, dob,salary,userName,passWord);

    }

    @Override
    public String giveCredentials() {
        StringBuilder builder = new StringBuilder();
        builder.append("Receptionist Hotel: ").append("\n").append(super.giveCredentials());
        return builder.toString();
    }
}