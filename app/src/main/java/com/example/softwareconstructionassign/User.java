/******
 * Project: Software Construction
 * Author: DILEEP VEMULA and ABHISHEK CHETRI
 */
package com.example.softwareconstructionassign;

public class User {
    String id;
    String email;
    String password;
    String stocks;
    String volume;


    public User(String id, String email, String password, String stocks, String volume) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.stocks = stocks;
        this.volume = volume;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public User(Object value) {

    }

    @Override
    public String toString() {
        return "User{" +
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

    public String getstocks() {
        return stocks;
    }

    public void setstocks(String stocks) {
        this.stocks = stocks;
    }
}
