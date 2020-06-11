package com.example.niket.grocerystore.Adress;

public class AddressPojo {
    private String name;
    private String address;
    private String mobile;
    private String email;
    private String landmark;
    private String pin;
    private String state;
    private String city;
    private String deletekey;

    String getDeletekey() {
        return deletekey;
    }

    void setDeletekey(String deletekey) {
        this.deletekey = deletekey;
    }

    String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String getLandmark() {
        return landmark;
    }

    void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    String getPin() {
        return pin;
    }

    void setPin(String pin) {
        this.pin = pin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
