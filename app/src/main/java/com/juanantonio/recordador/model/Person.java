package com.juanantonio.recordador.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
    private Integer id = null;
    private String name = null;
    private String email = null;
    private String image = null;
    private String location = null;
    private String phone = null;
    private String date = null;

    public String getLocation() {
        return location;
    }

    public boolean setLocation(String location) {
        if(location.length()>3){
            this.location = location;
            return true;
        }else{
            return false;
        }

    }

    public String getPhone() {
        return phone;
    }

    public boolean setPhone(String phone) {
        Pattern pattern = Pattern
                .compile("^(\\+34|0034|34)?[6|7|8|9][0-9]{8}$");
        Matcher mather = pattern.matcher(phone);
        if (mather.find() == true) {
            this.phone = phone;
            return true;
        } else {
            return false;
        }

    }

    public String getDate() {
        return date;
    }

    public boolean setDate(String date) {
        Pattern pattern = Pattern
                .compile("(([1-2][0-9])|([1-9])|(3[0-1]))/((1[0-2])|([1-9]))/[0-9]{4}");
        Matcher mather = pattern.matcher(date);
        if (mather.find() == true) {
            this.date = date;
            return true;
        } else {
            return false;
        }

    }

    public Person() {
    }

    public Person(Integer id, String name, String email, String image, String location, String phone, String date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
        this.location = location;
        this.phone = phone;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Nombre: " + this.name +
                "ID: " + this.id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {

        if (name.length() >= 3) {
            System.out.println("Longitud nombre" + name.length());
            this.name = name;
            return true;
        } else {
            return false;
        }

    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            this.email = email;
            return true;
        } else {
            return false;
        }

    }

    public String getImage() {
        return image;
    }

    public boolean setImage(String image) {
        this.image=image;
        return true;

    }


}