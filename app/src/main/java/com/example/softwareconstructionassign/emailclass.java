package com.example.softwareconstructionassign;

public class emailclass {
    String id;
    String email;
    String password;


    public emailclass(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public emailclass(Object value) {

    }

    @Override
    public String toString() {
        return "emailclass{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
