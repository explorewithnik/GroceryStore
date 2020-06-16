package com.example.niket.grocerystore.Adress;

/**
 * Created by Niket on 3/30/2018.
 */

public class AddressClass {
    private String name;
    private String userLandmark;
    private String userMobile;
    private String userEmail;
    private String userPinCode;
    private String userState;
    private String userCity;
    private String userFlatNumber;
    private String addressDelteId;

    public String getUserFlatNumber() {
        return userFlatNumber;
    }

    public void setUserFlatNumber(String userFlatNumber) {
        this.userFlatNumber = userFlatNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserLandmark() {
        return userLandmark;
    }

    public void setUserLandmark(String userLandmark) {
        this.userLandmark = userLandmark;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPinCode() {
        return userPinCode;
    }

    public void setUserPinCode(String userPinCode) {
        this.userPinCode = userPinCode;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getAddressDelteId() {
        return addressDelteId;
    }

    public void setAddressDelteId(String addressDelteId) {
        this.addressDelteId = addressDelteId;
    }

    public AddressClass() {
    }

    public AddressClass(String name, String userLandmark, String userMobile, String userEmail, String userPinCode, String userState, String userCity, String userFlatNumber, String addressDelteId) {
        this.name = name;
        this.userLandmark = userLandmark;
        this.userMobile = userMobile;
        this.userEmail = userEmail;
        this.userPinCode = userPinCode;
        this.userState = userState;
        this.userCity = userCity;
        this.userFlatNumber = userFlatNumber;
        this.addressDelteId = addressDelteId;
    }
}
