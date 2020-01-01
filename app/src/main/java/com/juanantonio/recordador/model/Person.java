package com.juanantonio.recordador.model;

public class Person {
    private Integer id = null;
    private String name = null;
    private String surname = null;
    private String image = null;

    public Person(Integer id, String user, String email, String image) {
        this.id = id;
        this.name = user;
        this.surname = email;
        this.image = image;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
