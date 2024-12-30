package com.example.Lodgify.Generic;

public class Authorization {
    private String userName;
    private String passWord;

    public  void setAuthorization(String userName , String passWord){
        this.userName = userName;
        this.passWord = passWord;
    }
    
    

    public Authorization() {
    }



    public String getUserName() {
        return userName;
    }



    public String getPassWord() {
        return passWord;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }



    public Authorization(String userName , String passWord){
        setAuthorization(userName,passWord);
    }

    //check authorization..
    public Boolean checkAuthorization(String userName , String passWord ){
        if(userName.equals(this.userName)){
            if(passWord.equals(this.passWord)){
                System.out.println("Authorized Successfully...");
                return true;
            }
            else {
                System.out.println("Wrong Password..");
                return false;
            }
        }
        else{
            System.out.println("Wrong credentials..");
            return false;
        }
    }

    @Override
    public String toString() {
        return "UserName is: " + userName + "\nPassword is: " + passWord;
    }


}