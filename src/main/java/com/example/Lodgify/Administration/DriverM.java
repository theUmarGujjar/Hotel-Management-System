package com.example.Lodgify.Administration;

public class DriverM extends Employee{

    public DriverM(){
        super();
    }
    public DriverM(String name , String cnic , String phoneNumber , String gmail , String dob,int salary,String userName,String passWord){
        super(name, cnic, phoneNumber, gmail, dob,salary,userName,passWord);
        
      }
    @Override
    public String giveCredentials() {
        StringBuilder builder = new StringBuilder();
        builder.append("Driver: ").append("\n").append(super.giveCredentials());
        return builder.toString();
    }
}