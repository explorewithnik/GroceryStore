package com.example.niket.grocerystore.POJO_Class;

public class MorePojo {
    private String itemImage;
    private Integer itemPrice;
    private String itemName;
    private String itemWeight;
    private String itemMRPStrikePrice;
    private String itemCount;
    private Integer buttonItemCount;
    private String categoryName;
    private Integer totalItemAmount;
    private boolean addedTocart = false;
    private String itemCatName;

    public String getItemCatName() {
        return itemCatName;
    }

    public void setItemCatName(String itemCatName) {
        this.itemCatName = itemCatName;
    }

    public boolean isAddedTocart() {
        return addedTocart;
    }

    public void setAddedTocart(boolean addedTocart) {
        this.addedTocart = addedTocart;
    }

    public Integer getButtonItemCount() {
        return buttonItemCount;
    }

    public void setButtonItemCount(Integer buttonItemCount) {
        this.buttonItemCount = buttonItemCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getTotalItemAmount() {
        return totalItemAmount;
    }

    public void setTotalItemAmount(Integer totalItemAmount) {
        this.totalItemAmount = totalItemAmount;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
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

    public String getItemMRPStrikePrice() {
        return itemMRPStrikePrice;
    }

    public void setItemMRPStrikePrice(String itemMRPStrikePrice) {
        this.itemMRPStrikePrice = itemMRPStrikePrice;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }
}
