package com.example.Lodgify.Administration;

public interface Regex {
    public final String NAME = "^[a-zA-Z ]+(?: [a-zA-Z]+)*$";
    public  final String CNIC= "^\\d{5}-\\d{7}-\\d{1}$";
    public final String PHONE = "^\\+?\\d{1,4}[-\\s]?\\(?\\d{3}\\)?[-\\s]?\\d{7}$";
    public final String GMAIL= "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public  final String DOB= "^(?!0000)\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
    public final String USERNAME="^[a-zA-Z0-9]+$";
    public final String RoomType="[a-zA-Z ]+";
    public final  String RoomFloor="[a-zA-Z0-9 ]+";
    public final String CarModel="[A-Za-z ]+";
    public final String LPlateNumber="[a-zA-Z]{3} \\d{1,4}";
}
