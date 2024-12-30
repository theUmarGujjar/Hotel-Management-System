package com.example.Lodgify.Administration;

import com.example.Lodgify.Generic.Individual;

import java.util.Scanner;

public class Employee extends Individual{
    int salary;
    

    Scanner input = new Scanner(System.in);


    // Authorizaion aut;
    Employee(String name , String cnic , String phoneNumber , String gmail , String dob,int salary,String userName,String passWord){
      super(name, cnic, phoneNumber, gmail, dob,userName, passWord);
      setSalary(salary);
    }

    Employee (String cnic , String phoneNumber , String gmail ,  String userName){
        super(cnic, phoneNumber, gmail);
        aut.setUserName(userName);
    }

    Employee(){};

    
    public int getSalary() {


        return salary;
    }
     void setSalary(int salary) {
        this.salary = salary;
    }

    public String giveCredentials() {
            return "CNIC: " + getCnic() + "\n" +
                    aut.toString();
    }



    public void loadEmp(){

            
        String cnic;
        String gmail;
        String phoneNumber;
        String dob;
        int salary;

        //taking info about employee
            System.out.println("enter the details of employee");
            System.out.println("Name : ");
            name=input.nextLine();
            setName(name);
            System.out.println("Cnic : ");
            cnic=input.nextLine();
             setCnic(cnic);
                
            
           
            System.out.println("email : ");
            gmail=input.nextLine();
            setGmail(gmail);
            System.out.println("Phone number : ");
            phoneNumber=input.nextLine();
            setPhoneNumber(phoneNumber);
            System.out.println("date of birth as YYYY-MM-DD : ");
            dob=input.nextLine();
            setDob(dob);
            System.out.println("salary : ");
            while(true){
            if(!(input.hasNextInt())){
                System.out.println();
                System.out.println("WRONG INPUT ::: ENTER INTEGER ONLY  ");
                System.out.println();
                input.nextLine();
            
            }else{
                break;
            }
        }
            salary=input.nextInt();
            input.nextLine();
            setSalary(salary);


            System.out.println("Enter the Username of that employee :  ");
            String username = input.nextLine();
            System.out.println("Enter the password of that employee :  ");
            String password = input.nextLine();

            aut.setAuthorization(username , password);


    }
    public String getUserName() {
        return aut.getUserName();
    }

    public String getPassWord() {
        return aut.getPassWord();
    }

    
}
