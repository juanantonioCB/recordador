package com.juanantonio.recordador.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private Boolean state = null;
    private String province = null;

    public String getLocation() {
        return location;
    }

    public Boolean getState() {
        return state;
    }

    public String getProvince() {
        return province;
    }

    public boolean setProvince(String province) {
        if (province.length() > 3) {
            this.province = province;
            return true;
        } else {
            return false;
        }
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public boolean setLocation(String location) {
        if (location.length() > 3) {
            this.location = location;
            return true;
        } else {
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
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false);
            formatoFecha.parse(date);
        } catch (ParseException e) {
            return false;
        }
        this.date = date;
        return true;

    }

    public Person() {
    }

    public Person(Integer id, String name, String email, String image, String location, String phone, String date, Boolean state, String province) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
        this.location = location;
        this.phone = phone;
        this.date = date;
        this.state = state;
        this.province=province;
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

    public void setImage(String image) {
        this.image = image;
    }


}