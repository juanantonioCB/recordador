package com.juanantonio.recordador.model;

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

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
    public String toString(){
        return "Nombre: "+this.name+
                "ID: "+this.id;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
