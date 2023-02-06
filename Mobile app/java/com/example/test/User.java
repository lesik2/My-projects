package com.example.test;
public class User {
    private String name,login,password,surname;
    private int id,role;
    public User() {
    }
    public User(int role,String login, String password, String name, String surname) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.surname = surname;
        this.role=role;
    }
    public User(int id,int role, String login, String password,String name, String surname) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.surname = surname;
        this.id = id;
        this.role=role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
