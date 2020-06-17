package com.example.niket.grocerystore.ItemsPOJO;

/**
 * Created by Niket on 3/30/2018.
 */

public class Category_Items_POJO {
    private String itemImage;
    private Integer itemPrice;
    private String itemName;
    private String itemWeight;
    private String itemMRPStrikePrice;
    private Integer buttonItemCount;
    private String categoryName;
    private Integer totalItemAmount;
    private String itemCatName;
    private String searchItemName ;

    public String getSearchItemName() {
        return searchItemName;
    }

    public void setSearchItemName(String searchItemName) {
        this.searchItemName = searchItemName;
    }



    public String getItemCatName() {
        return itemCatName;
    }

    public void setItemCatName(String itemCatName) {
        this.itemCatName = itemCatName;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getTotalItemAmount() {
        return totalItemAmount;
    }

    public void setTotalItemAmount(Integer totalItemAmount) {
        this.totalItemAmount = totalItemAmount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getButtonItemCount() {
        return buttonItemCount;
    }

    public void setButtonItemCount(Integer buttonItemCount) {
        this.buttonItemCount = buttonItemCount;
    }

    private String plusMinusButton;

    public String getPlusMinusButton() {
        return plusMinusButton;
    }

    public void setPlusMinusButton(String plusMinusButton) {
        this.plusMinusButton = plusMinusButton;
    }

    private boolean addedTocart = false;


    public boolean isAddedTocart() {
        return addedTocart;
    }

    public void setAddedTocart(boolean addedTocart) {
        this.addedTocart = addedTocart;
    }

    public Category_Items_POJO(String itemName) {
        this.itemName = itemName;
    }


    public Category_Items_POJO() {
    }

    public Category_Items_POJO(String itemImage, Integer itemPrice, String itemName, String itemWeight, String itemMRPStrikePrice, boolean addedTocart) {

        this.itemImage = itemImage;
        this.itemPrice = itemPrice;
        this.itemWeight = itemWeight;
        this.itemName = itemName;
        this.itemMRPStrikePrice = itemMRPStrikePrice;
        this.addedTocart = addedTocart;

    }

    public Category_Items_POJO(String itemImage, Integer itemPrice, String itemName, String itemWeight, String itemMRPStrikePrice, Integer buttonItemCount, String categoryName, Integer totalItemAmount, String itemCatName, String plusMinusButton, boolean addedTocart) {
        this.itemImage = itemImage;
        this.itemPrice = itemPrice;
        this.itemName = itemName;
        this.itemWeight = itemWeight;
        this.itemMRPStrikePrice = itemMRPStrikePrice;
        this.buttonItemCount = buttonItemCount;
        this.categoryName = categoryName;
        this.totalItemAmount = totalItemAmount;
        this.itemCatName = itemCatName;
        this.plusMinusButton = plusMinusButton;
        this.addedTocart = addedTocart;
    }

    public String getItemMRPStrikePrice() {
        return itemMRPStrikePrice;
    }

    public void setItemMRPStrikePrice(String itemMRPStrikePrice) {
        this.itemMRPStrikePrice = itemMRPStrikePrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(String itemWeight) {
        this.itemWeight = itemWeight;
    }
}
