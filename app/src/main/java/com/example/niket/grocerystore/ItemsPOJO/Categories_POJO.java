package com.example.niket.grocerystore.ItemsPOJO;

import java.util.ArrayList;

/**
 * Created by Niket on 3/22/2018.
 */

public class Categories_POJO {

  private String categoryName;
  private ArrayList<Category_Items_POJO> allItemInSectionArralist;


    public ArrayList<Category_Items_POJO> getAllItemInSectionArralist() {
        return allItemInSectionArralist;
    }

    public void setAllItemInSectionArralist(ArrayList<Category_Items_POJO> allItemInSectionArralist) {
        this.allItemInSectionArralist = allItemInSectionArralist;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
