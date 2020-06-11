package com.example.niket.grocerystore.CartActivity;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderHistoryDetails implements Parcelable {
    private String itemName;
    private String itemImage;
    private String itemPrice;
    private String itemCount;
    private String totalAmount;

    private OrderHistoryDetails(Parcel in) {
        itemName = in.readString();
        itemImage = in.readString();
        itemPrice = in.readString();
        itemCount = in.readString();
        totalAmount = in.readString();
    }

    public static final Creator<OrderHistoryDetails> CREATOR = new Creator<OrderHistoryDetails>() {
        @Override
        public OrderHistoryDetails createFromParcel(Parcel in) {
            return new OrderHistoryDetails(in);
        }

        @Override
        public OrderHistoryDetails[] newArray(int size) {
            return new OrderHistoryDetails[size];
        }
    };

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCount() {
        return itemCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.itemName);
        parcel.writeString(this.itemCount);
        parcel.writeString(this.itemImage);
        parcel.writeString(this.itemPrice);
        parcel.writeString(this.totalAmount);
    }
}
