package com.example.Lodgify.Generic;

import java.util.Scanner;

public abstract class Individual {
    public String name;
    private String cnic;
    private String gmail;
    private String phoneNumber;
    private String dob;
    Scanner input=new Scanner(System.in);
    public Authorization aut = new Authorization();



    public Individual() {}

    // constructor overloading.
    public Individual(String cnic, String phoneNumber, String gmail ) {
        setInfo( cnic, phoneNumber, gmail);
    }

    public Individual(String name, String cnic, String phoneNumber, String gmail, String dob) {
        setInfo(name, cnic, phoneNumber, gmail, dob);
    }
    public Individual(String name, String cnic, String phoneNumber, String gmail, String dob,String userName, String passWord) {
        setInfo(name, cnic, phoneNumber, gmail, dob);
        aut.setAuthorization(userName, passWord);
    }

    public void setName(String name) {

            this.name = name;


    }


    public void setCnic(String cnic) {
            this.cnic = cnic;

    }

    public void setGmail(String gmail) {

            this.gmail = gmail;

    }
    public void setPhoneNumber(String phoneNumber) {

            this.phoneNumber = phoneNumber;

    }
    public void setDob(String dob) {

            this.dob = dob;

    }
    public void setInfo(String name, String cnic, String phoneNumber, String gmail, String dob) {
        setName(name);
        setCnic(cnic);
        setPhoneNumber(phoneNumber);
        setGmail(gmail);
        setDob(dob);
    }

    // Static PolyMorphisim.
    public void setInfo( String cnic, String phoneNumber, String gmail) {
        setCnic(cnic);
        setPhoneNumber(phoneNumber);
        setGmail(gmail);
    }

    public String getName() {
        return name;
    }

    public String getCnic() {
        return cnic;
    }

    public String getGmail() {
        return gmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDob() {
        return dob;
    }
}