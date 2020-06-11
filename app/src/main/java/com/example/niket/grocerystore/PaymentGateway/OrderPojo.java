package com.example.niket.grocerystore.PaymentGateway;

public class OrderPojo {
    private String name;
    private String mobile;
    private String email;
    private String pin;
    private String state;
    private String city;
    private String finalAmount;
    private String flatNo;
    private String landMark;
    private String transactionReference;
    private String orderId;
    private String currentDate;
    private String currentTime;

    String getCurrentTime() {
        return currentTime;
    }

    void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    private String mode;

    public String getMode() {
        return mode;
    }

    void setMode(String mode) {
        this.mode = mode;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    void setCity(String city) {
        this.city = city;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getFlatNo() {
        return flatNo;
    }

    void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getLandMark() {
        return landMark;
    }

    void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getOrderId() {
        return orderId;
    }

    void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
