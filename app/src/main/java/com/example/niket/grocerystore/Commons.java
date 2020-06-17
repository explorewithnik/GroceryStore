package com.example.niket.grocerystore;

import com.example.niket.grocerystore.ItemsPOJO.Category_Items_POJO;

import java.util.ArrayList;

public class Commons {

    public ArrayList<Category_Items_POJO> getDryFruitsDetails() {
        ArrayList<Category_Items_POJO> itemDetailList = new ArrayList<>();

        Category_Items_POJO category_items_pojo1 = new Category_Items_POJO();
        category_items_pojo1.setButtonItemCount(0);
        category_items_pojo1.setCategoryName("Dry Fruits & Nuts");
        category_items_pojo1.setItemCatName("Almonds");
        category_items_pojo1.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FDry%20Fruits%20%26%20Nuts%2FGlonutsAlmonds.jpg?alt=media&token=59342d27-af4e-4efe-ae5d-6e2c9f7373b1");
        category_items_pojo1.setItemMRPStrikePrice("260");
        category_items_pojo1.setItemName("Glonuts Almonds");
        category_items_pojo1.setItemPrice(220);
        category_items_pojo1.setItemWeight("200 gm");
        category_items_pojo1.setPlusMinusButton("-");

        itemDetailList.add(category_items_pojo1);

        Category_Items_POJO category_items_pojo2 = new Category_Items_POJO();
        category_items_pojo2.setButtonItemCount(0);
        category_items_pojo2.setCategoryName("Dry Fruits & Nuts");
        category_items_pojo2.setItemCatName("Cashews");
        category_items_pojo2.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FDry%20Fruits%20%26%20Nuts%2Fglonutcashew.jpg?alt=media&token=7d83ea98-88c5-4c73-89a9-28efd16104bf");
        category_items_pojo2.setItemMRPStrikePrice("350");
        category_items_pojo2.setItemName("Glonuts Cashews");
        category_items_pojo2.setItemPrice(210);
        category_items_pojo2.setItemWeight("200 gm");

        itemDetailList.add(category_items_pojo2);


        Category_Items_POJO category_items_pojo3 = new Category_Items_POJO();
        category_items_pojo3.setButtonItemCount(0);
        category_items_pojo3.setCategoryName("Dry Fruits & Nuts");
        category_items_pojo3.setItemCatName("Raisins");
        category_items_pojo3.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FDry%20Fruits%20%26%20Nuts%2FglonutRaisins.jpg?alt=media&token=65ba83db-f079-4371-b907-3ac4eeeb0c1a");
        category_items_pojo3.setItemMRPStrikePrice("245");
        category_items_pojo3.setItemName("Glonuts Raisins");
        category_items_pojo3.setItemPrice(99);
        category_items_pojo3.setItemWeight("500 gm");

        itemDetailList.add(category_items_pojo3);


        Category_Items_POJO category_items_pojo4 = new Category_Items_POJO();
        category_items_pojo4.setButtonItemCount(0);
        category_items_pojo4.setCategoryName("Dry Fruits & Nuts");
        category_items_pojo4.setItemCatName("Walnuts");
        category_items_pojo4.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FDry%20Fruits%20%26%20Nuts%2Fglonut%20walnut.jpg?alt=media&token=a5791546-2072-4e45-b182-4536960685e9");
        category_items_pojo4.setItemMRPStrikePrice("169");
        category_items_pojo4.setItemName("Glonuts walnuts");
        category_items_pojo4.setItemPrice(87);
        category_items_pojo4.setItemWeight("200 gm");

        itemDetailList.add(category_items_pojo4);


        return itemDetailList;
    }

    public ArrayList<Category_Items_POJO> getAtaAndOtherFlours() {
        ArrayList<Category_Items_POJO> itemDetailList = new ArrayList<>();

        Category_Items_POJO category_items_pojo1 = new Category_Items_POJO();
        category_items_pojo1.setButtonItemCount(0);
        category_items_pojo1.setCategoryName("Atta and Other Flours");
        category_items_pojo1.setItemCatName("Atta");
        category_items_pojo1.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Item%20Images%2FAashirwaad%20Atta%2FAashirwaad%20Atta13187?alt=media&token=1011cd35-2878-49a3-b734-a486bb19f007");
        category_items_pojo1.setItemMRPStrikePrice("320");
        category_items_pojo1.setItemName("Aashirwaad Atta");
        category_items_pojo1.setItemPrice(270);
        category_items_pojo1.setItemWeight("10 kg");
        category_items_pojo1.setPlusMinusButton("-");

        itemDetailList.add(category_items_pojo1);

        Category_Items_POJO category_items_pojo2 = new Category_Items_POJO();
        category_items_pojo2.setButtonItemCount(0);
        category_items_pojo2.setCategoryName("Atta and Other Flours");
        category_items_pojo2.setItemCatName("Besan");
        category_items_pojo2.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/FortuneBesan.jpg?alt=media&token=bfb97c8e-e0f5-4d25-a31d-84c0bc5b9c96");
        category_items_pojo2.setItemMRPStrikePrice("55");
        category_items_pojo2.setItemName("Fortune Matar Besan");
        category_items_pojo2.setItemPrice(50);
        category_items_pojo2.setItemWeight("500 gm");

        itemDetailList.add(category_items_pojo2);


        Category_Items_POJO category_items_pojo3 = new Category_Items_POJO();
        category_items_pojo3.setButtonItemCount(0);
        category_items_pojo3.setCategoryName("Atta and Other Flours");
        category_items_pojo3.setItemCatName("Corn Flour");
        category_items_pojo3.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FAtta%20and%20Other%20Flours%2Fcorn-flour-250x250.png?alt=media&token=94ed60bd-42b6-4634-bc4c-9704ee26147d");
        category_items_pojo3.setItemMRPStrikePrice("45");
        category_items_pojo3.setItemName("Kwality Corn Flour");
        category_items_pojo3.setItemPrice(30);
        category_items_pojo3.setItemWeight("500 gm");

        itemDetailList.add(category_items_pojo3);

        Category_Items_POJO category_items_pojo4 = new Category_Items_POJO();
        category_items_pojo4.setButtonItemCount(0);
        category_items_pojo4.setCategoryName("Atta and Other Flours");
        category_items_pojo4.setItemCatName("Sooji");
        category_items_pojo4.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/shaktibhogsooji.jpg?alt=media&token=34ccae34-718d-4561-89fd-45985d6d0e1b");
        category_items_pojo4.setItemMRPStrikePrice("30");
        category_items_pojo4.setItemName("Shakti Bhog Sooji");
        category_items_pojo4.setItemPrice(16);
        category_items_pojo4.setItemWeight("500 gm");

        itemDetailList.add(category_items_pojo4);

        return itemDetailList;
    }

    public ArrayList<Category_Items_POJO> getPulses() {
        ArrayList<Category_Items_POJO> itemDetailList = new ArrayList<>();

        Category_Items_POJO category_items_pojo1 = new Category_Items_POJO();
        category_items_pojo1.setButtonItemCount(0);
        category_items_pojo1.setCategoryName("Pulses");
        category_items_pojo1.setItemCatName("Arhar");
        category_items_pojo1.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FPulses%2Farharloose.jpg?alt=media&token=55bf44c7-8235-4725-9ab1-af3103f41e66");
        category_items_pojo1.setItemMRPStrikePrice("90");
        category_items_pojo1.setItemName("loose Arhar");
        category_items_pojo1.setItemPrice(60);
        category_items_pojo1.setItemWeight("1 kg");

        itemDetailList.add(category_items_pojo1);

        Category_Items_POJO category_items_pojo2 = new Category_Items_POJO();
        category_items_pojo2.setButtonItemCount(0);
        category_items_pojo2.setCategoryName("Pulses");
        category_items_pojo2.setItemCatName("Urad");
        category_items_pojo2.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FPulses%2FuradLoose.jpg?alt=media&token=5129e6cc-4b66-4e5f-920b-ae65030d7e83");
        category_items_pojo2.setItemMRPStrikePrice("80");
        category_items_pojo2.setItemName("loose Urad");
        category_items_pojo2.setItemPrice(39);
        category_items_pojo2.setItemWeight("500 gm");

        itemDetailList.add(category_items_pojo2);


        Category_Items_POJO category_items_pojo3 = new Category_Items_POJO();
        category_items_pojo3.setButtonItemCount(0);
        category_items_pojo3.setCategoryName("Pulses");
        category_items_pojo3.setItemCatName("kabulli chana");
        category_items_pojo3.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FPulses%2FkabulichanaLoose.jpg?alt=media&token=0250f28b-144f-428a-bdbb-6303771f406b");
        category_items_pojo3.setItemMRPStrikePrice("115");
        category_items_pojo3.setItemName("loose kabulli chana");
        category_items_pojo3.setItemPrice(58);
        category_items_pojo3.setItemWeight("500 gm");

        itemDetailList.add(category_items_pojo3);

        return itemDetailList;
    }

    public ArrayList<Category_Items_POJO> getRiceAndGrains() {
        ArrayList<Category_Items_POJO> itemDetailList = new ArrayList<>();

        Category_Items_POJO category_items_pojo1 = new Category_Items_POJO();
        category_items_pojo1.setButtonItemCount(0);
        category_items_pojo1.setCategoryName("Rice & Other Grains");
        category_items_pojo1.setItemCatName("Poha");
        category_items_pojo1.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Item%20Images%2FKirana%20King%20Poha%2FKirana%20King%20Poha248557?alt=media&token=3dd753bc-ccda-42cc-b4c9-cb3729c56c27");
        category_items_pojo1.setItemMRPStrikePrice("35");
        category_items_pojo1.setItemName("Kirana King Poha");
        category_items_pojo1.setItemPrice(25);
        category_items_pojo1.setItemWeight("500 gm");

        itemDetailList.add(category_items_pojo1);

        Category_Items_POJO category_items_pojo2 = new Category_Items_POJO();
        category_items_pojo2.setButtonItemCount(0);
        category_items_pojo2.setCategoryName("Rice & Other Grains");
        category_items_pojo2.setItemCatName("Daliya");
        category_items_pojo2.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FPulses%2Floosedalliya.jpg?alt=media&token=e1c361dc-45eb-42e5-ba2e-c664f2c90837");
        category_items_pojo2.setItemMRPStrikePrice("35");
        category_items_pojo2.setItemName("loose Daaliya");
        category_items_pojo2.setItemPrice(19);
        category_items_pojo2.setItemWeight("500 gm");

        itemDetailList.add(category_items_pojo2);

        return itemDetailList;
    }

    public ArrayList<Category_Items_POJO> getSpices() {
        ArrayList<Category_Items_POJO> itemDetailList = new ArrayList<>();

        Category_Items_POJO category_items_pojo1 = new Category_Items_POJO();
        category_items_pojo1.setButtonItemCount(0);
        category_items_pojo1.setCategoryName("Spices");
        category_items_pojo1.setItemCatName("Black Pepper");
        category_items_pojo1.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Item%20Images%2FCatch%20Black%20Pepper%20Powder%2FCatch%20Black%20Pepper%20Powder18113?alt=media&token=f479908a-c6c3-4f8a-976f-f0fb6209e746");
        category_items_pojo1.setItemMRPStrikePrice("180");
        category_items_pojo1.setItemName("Catch Black Pepper Powder");
        category_items_pojo1.setItemPrice(126);
        category_items_pojo1.setItemWeight("100 gm");

        itemDetailList.add(category_items_pojo1);

        Category_Items_POJO category_items_pojo2 = new Category_Items_POJO();
        category_items_pojo2.setButtonItemCount(0);
        category_items_pojo2.setCategoryName("Spices");
        category_items_pojo2.setItemCatName("Garam Masala");
        category_items_pojo2.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Item%20Images%2FCatch%20Garam%20Masala%20%2FCatch%20Garam%20Masala%2018113?alt=media&token=8ebfb587-50e2-45cb-a758-7a284d5938c3");
        category_items_pojo2.setItemMRPStrikePrice("90");
        category_items_pojo2.setItemName("Catch Garam Masala");
        category_items_pojo2.setItemPrice(70);
        category_items_pojo2.setItemWeight("100 gm");

        itemDetailList.add(category_items_pojo2);


        Category_Items_POJO category_items_pojo3 = new Category_Items_POJO();
        category_items_pojo3.setButtonItemCount(0);
        category_items_pojo3.setCategoryName("Spices");
        category_items_pojo3.setItemCatName("Jeera");
        category_items_pojo3.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Item%20Images%2FCatch%20Jeera%20Powder%2FCatch%20Jeera%20Powder18113?alt=media&token=2e490d4d-b3f4-49b0-bb37-8af556a4d254");
        category_items_pojo3.setItemMRPStrikePrice("150");
        category_items_pojo3.setItemName("Catch Jeera Powder");
        category_items_pojo3.setItemPrice(120);
        category_items_pojo3.setItemWeight("100 gm");

        itemDetailList.add(category_items_pojo3);


        Category_Items_POJO category_items_pojo4 = new Category_Items_POJO();
        category_items_pojo4.setButtonItemCount(0);
        category_items_pojo4.setCategoryName("Spices");
        category_items_pojo4.setItemCatName("white Pepper");
        category_items_pojo4.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Item%20Images%2FShyam%20white%20paper%20Powder%2FShyam%20white%20paper%20Powder18113?alt=media&token=9180ed9a-a92e-4dc9-9843-88907321756e");
        category_items_pojo4.setItemMRPStrikePrice("200");
        category_items_pojo4.setItemName("Shyam white Pepper Powder");
        category_items_pojo4.setItemPrice(150);
        category_items_pojo4.setItemWeight("50 gm");

        itemDetailList.add(category_items_pojo4);


        return itemDetailList;
    }

    public ArrayList<Category_Items_POJO> getSearchData() {
        ArrayList<Category_Items_POJO> itemDetailList = new ArrayList<>();

        //pulses
        Category_Items_POJO category_items_pojo1 = new Category_Items_POJO();
        category_items_pojo1.setButtonItemCount(0);
        category_items_pojo1.setCategoryName("Pulses");
        category_items_pojo1.setItemCatName("Arhar");
        category_items_pojo1.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FPulses%2Farharloose.jpg?alt=media&token=55bf44c7-8235-4725-9ab1-af3103f41e66");
        category_items_pojo1.setItemMRPStrikePrice("90");
        category_items_pojo1.setItemName("loose Arhar");
        category_items_pojo1.setItemPrice(60);
        category_items_pojo1.setItemWeight("1 kg");
        category_items_pojo1.setSearchItemName("arhar");
        itemDetailList.add(category_items_pojo1);

        Category_Items_POJO category_items_pojo2 = new Category_Items_POJO();
        category_items_pojo2.setButtonItemCount(0);
        category_items_pojo2.setCategoryName("Pulses");
        category_items_pojo2.setItemCatName("Urad");
        category_items_pojo2.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FPulses%2FuradLoose.jpg?alt=media&token=5129e6cc-4b66-4e5f-920b-ae65030d7e83");
        category_items_pojo2.setItemMRPStrikePrice("80");
        category_items_pojo2.setItemName("loose Urad");
        category_items_pojo2.setItemPrice(39);
        category_items_pojo2.setItemWeight("500 gm");
        category_items_pojo2.setSearchItemName("urad");
        itemDetailList.add(category_items_pojo2);

        Category_Items_POJO category_items_pojo3 = new Category_Items_POJO();
        category_items_pojo3.setButtonItemCount(0);
        category_items_pojo3.setCategoryName("Pulses");
        category_items_pojo3.setItemCatName("kabulli chana");
        category_items_pojo3.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FPulses%2FkabulichanaLoose.jpg?alt=media&token=0250f28b-144f-428a-bdbb-6303771f406b");
        category_items_pojo3.setItemMRPStrikePrice("115");
        category_items_pojo3.setItemName("loose kabulli chana");
        category_items_pojo3.setItemPrice(58);
        category_items_pojo3.setItemWeight("500 gm");
        category_items_pojo3.setSearchItemName("chana");
        itemDetailList.add(category_items_pojo3);

        //dry fruits
        Category_Items_POJO category_items_pojo4 = new Category_Items_POJO();
        category_items_pojo4.setButtonItemCount(0);
        category_items_pojo4.setCategoryName("Dry Fruits & Nuts");
        category_items_pojo4.setItemCatName("Almonds");
        category_items_pojo4.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FDry%20Fruits%20%26%20Nuts%2FGlonutsAlmonds.jpg?alt=media&token=59342d27-af4e-4efe-ae5d-6e2c9f7373b1");
        category_items_pojo4.setItemMRPStrikePrice("260");
        category_items_pojo4.setItemName("Glonuts Almonds");
        category_items_pojo4.setItemPrice(220);
        category_items_pojo4.setItemWeight("200 gm");
        category_items_pojo4.setSearchItemName("almond");
        itemDetailList.add(category_items_pojo4);

        Category_Items_POJO category_items_pojo5 = new Category_Items_POJO();
        category_items_pojo5.setButtonItemCount(0);
        category_items_pojo5.setCategoryName("Dry Fruits & Nuts");
        category_items_pojo5.setItemCatName("Cashews");
        category_items_pojo5.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FDry%20Fruits%20%26%20Nuts%2Fglonutcashew.jpg?alt=media&token=7d83ea98-88c5-4c73-89a9-28efd16104bf");
        category_items_pojo5.setItemMRPStrikePrice("350");
        category_items_pojo5.setItemName("Glonuts Cashews");
        category_items_pojo5.setItemPrice(210);
        category_items_pojo5.setItemWeight("200 gm");
        category_items_pojo5.setSearchItemName("cashew");
        itemDetailList.add(category_items_pojo5);

        Category_Items_POJO category_items_pojo6 = new Category_Items_POJO();
        category_items_pojo6.setButtonItemCount(0);
        category_items_pojo6.setCategoryName("Dry Fruits & Nuts");
        category_items_pojo6.setItemCatName("Raisins");
        category_items_pojo6.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FDry%20Fruits%20%26%20Nuts%2FglonutRaisins.jpg?alt=media&token=65ba83db-f079-4371-b907-3ac4eeeb0c1a");
        category_items_pojo6.setItemMRPStrikePrice("245");
        category_items_pojo6.setItemName("Glonuts Raisins");
        category_items_pojo6.setItemPrice(99);
        category_items_pojo6.setItemWeight("500 gm");
        category_items_pojo6.setSearchItemName("raisins");
        itemDetailList.add(category_items_pojo6);

        Category_Items_POJO category_items_pojo7 = new Category_Items_POJO();
        category_items_pojo7.setButtonItemCount(0);
        category_items_pojo7.setCategoryName("Dry Fruits & Nuts");
        category_items_pojo7.setItemCatName("Walnuts");
        category_items_pojo7.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FDry%20Fruits%20%26%20Nuts%2Fglonut%20walnut.jpg?alt=media&token=a5791546-2072-4e45-b182-4536960685e9");
        category_items_pojo7.setItemMRPStrikePrice("169");
        category_items_pojo7.setItemName("Glonuts walnuts");
        category_items_pojo7.setItemPrice(87);
        category_items_pojo7.setItemWeight("200 gm");
        category_items_pojo7.setSearchItemName("walnut");
        itemDetailList.add(category_items_pojo7);

        //atta
        Category_Items_POJO category_items_pojo8 = new Category_Items_POJO();
        category_items_pojo8.setButtonItemCount(0);
        category_items_pojo8.setCategoryName("Atta and Other Flours");
        category_items_pojo8.setItemCatName("Atta");
        category_items_pojo8.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Item%20Images%2FAashirwaad%20Atta%2FAashirwaad%20Atta13187?alt=media&token=1011cd35-2878-49a3-b734-a486bb19f007");
        category_items_pojo8.setItemMRPStrikePrice("320");
        category_items_pojo8.setItemName("Aashirwaad Atta");
        category_items_pojo8.setItemPrice(270);
        category_items_pojo8.setItemWeight("10 kg");
        category_items_pojo8.setSearchItemName("atta");
        itemDetailList.add(category_items_pojo8);

        Category_Items_POJO category_items_pojo9 = new Category_Items_POJO();
        category_items_pojo9.setButtonItemCount(0);
        category_items_pojo9.setCategoryName("Atta and Other Flours");
        category_items_pojo9.setItemCatName("Besan");
        category_items_pojo9.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/FortuneBesan.jpg?alt=media&token=bfb97c8e-e0f5-4d25-a31d-84c0bc5b9c96");
        category_items_pojo9.setItemMRPStrikePrice("55");
        category_items_pojo9.setItemName("Fortune Matar Besan");
        category_items_pojo9.setItemPrice(50);
        category_items_pojo9.setItemWeight("500 gm");
        category_items_pojo9.setSearchItemName("besan");
        itemDetailList.add(category_items_pojo9);

        Category_Items_POJO category_items_pojo10 = new Category_Items_POJO();
        category_items_pojo10.setButtonItemCount(0);
        category_items_pojo10.setCategoryName("Atta and Other Flours");
        category_items_pojo10.setItemCatName("Corn Flour");
        category_items_pojo10.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FAtta%20and%20Other%20Flours%2Fcorn-flour-250x250.png?alt=media&token=94ed60bd-42b6-4634-bc4c-9704ee26147d");
        category_items_pojo10.setItemMRPStrikePrice("45");
        category_items_pojo10.setItemName("Kwality Corn Flour");
        category_items_pojo10.setItemPrice(30);
        category_items_pojo10.setItemWeight("500 gm");
        category_items_pojo10.setSearchItemName("corn flour");
        itemDetailList.add(category_items_pojo10);

        Category_Items_POJO category_items_pojo11 = new Category_Items_POJO();
        category_items_pojo11.setButtonItemCount(0);
        category_items_pojo11.setCategoryName("Atta and Other Flours");
        category_items_pojo11.setItemCatName("Sooji");
        category_items_pojo11.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/shaktibhogsooji.jpg?alt=media&token=34ccae34-718d-4561-89fd-45985d6d0e1b");
        category_items_pojo11.setItemMRPStrikePrice("30");
        category_items_pojo11.setItemName("Shakti Bhog Sooji");
        category_items_pojo11.setItemPrice(16);
        category_items_pojo11.setItemWeight("500 gm");
        category_items_pojo11.setSearchItemName("sooji");
        itemDetailList.add(category_items_pojo11);

        //spices
        Category_Items_POJO category_items_pojo12 = new Category_Items_POJO();
        category_items_pojo12.setButtonItemCount(0);
        category_items_pojo12.setCategoryName("Spices");
        category_items_pojo12.setItemCatName("Black Pepper");
        category_items_pojo12.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Item%20Images%2FCatch%20Black%20Pepper%20Powder%2FCatch%20Black%20Pepper%20Powder18113?alt=media&token=f479908a-c6c3-4f8a-976f-f0fb6209e746");
        category_items_pojo12.setItemMRPStrikePrice("180");
        category_items_pojo12.setItemName("Catch Black Pepper Powder");
        category_items_pojo12.setItemPrice(126);
        category_items_pojo12.setItemWeight("100 gm");
        category_items_pojo12.setSearchItemName("black pepper");
        itemDetailList.add(category_items_pojo12);

        Category_Items_POJO category_items_pojo13 = new Category_Items_POJO();
        category_items_pojo13.setButtonItemCount(0);
        category_items_pojo13.setCategoryName("Spices");
        category_items_pojo13.setItemCatName("Garam Masala");
        category_items_pojo13.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Item%20Images%2FCatch%20Garam%20Masala%20%2FCatch%20Garam%20Masala%2018113?alt=media&token=8ebfb587-50e2-45cb-a758-7a284d5938c3");
        category_items_pojo13.setItemMRPStrikePrice("90");
        category_items_pojo13.setItemName("Catch Garam Masala");
        category_items_pojo13.setItemPrice(70);
        category_items_pojo13.setItemWeight("100 gm");
        category_items_pojo13.setSearchItemName("garam masala");
        itemDetailList.add(category_items_pojo13);

        Category_Items_POJO category_items_pojo14 = new Category_Items_POJO();
        category_items_pojo14.setButtonItemCount(0);
        category_items_pojo14.setCategoryName("Spices");
        category_items_pojo14.setItemCatName("Jeera");
        category_items_pojo14.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Item%20Images%2FCatch%20Jeera%20Powder%2FCatch%20Jeera%20Powder18113?alt=media&token=2e490d4d-b3f4-49b0-bb37-8af556a4d254");
        category_items_pojo14.setItemMRPStrikePrice("150");
        category_items_pojo14.setItemName("Catch Jeera Powder");
        category_items_pojo14.setItemPrice(120);
        category_items_pojo14.setItemWeight("100 gm");
        category_items_pojo14.setSearchItemName("jeera powder");
        itemDetailList.add(category_items_pojo14);

        Category_Items_POJO category_items_pojo15 = new Category_Items_POJO();
        category_items_pojo15.setButtonItemCount(0);
        category_items_pojo15.setCategoryName("Spices");
        category_items_pojo15.setItemCatName("white Pepper");
        category_items_pojo15.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Item%20Images%2FShyam%20white%20paper%20Powder%2FShyam%20white%20paper%20Powder18113?alt=media&token=9180ed9a-a92e-4dc9-9843-88907321756e");
        category_items_pojo15.setItemMRPStrikePrice("200");
        category_items_pojo15.setItemName("Shyam white Pepper Powder");
        category_items_pojo15.setItemPrice(150);
        category_items_pojo15.setItemWeight("50 gm");
        category_items_pojo15.setSearchItemName("pepper powder");

        itemDetailList.add(category_items_pojo15);

        //rice
        Category_Items_POJO category_items_pojo16 = new Category_Items_POJO();
        category_items_pojo16.setButtonItemCount(0);
        category_items_pojo16.setCategoryName("Rice & Other Grains");
        category_items_pojo16.setItemCatName("Poha");
        category_items_pojo16.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Item%20Images%2FKirana%20King%20Poha%2FKirana%20King%20Poha248557?alt=media&token=3dd753bc-ccda-42cc-b4c9-cb3729c56c27");
        category_items_pojo16.setItemMRPStrikePrice("35");
        category_items_pojo16.setItemName("Kirana King Poha");
        category_items_pojo16.setItemPrice(25);
        category_items_pojo16.setSearchItemName("poha");
        category_items_pojo16.setItemWeight("500 gm");

        itemDetailList.add(category_items_pojo16);

        Category_Items_POJO category_items_pojo17 = new Category_Items_POJO();
        category_items_pojo17.setButtonItemCount(0);
        category_items_pojo17.setCategoryName("Rice & Other Grains");
        category_items_pojo17.setItemCatName("Daliya");
        category_items_pojo17.setItemImage("https://firebasestorage.googleapis.com/v0/b/grocery-store-457ab.appspot.com/o/Category%20Images%2FPulses%2Floosedalliya.jpg?alt=media&token=e1c361dc-45eb-42e5-ba2e-c664f2c90837");
        category_items_pojo17.setItemMRPStrikePrice("35");
        category_items_pojo17.setItemName("loose Daliya");
        category_items_pojo17.setItemPrice(19);
        category_items_pojo17.setSearchItemName("daliya");
        category_items_pojo17.setItemWeight("500 gm");

        itemDetailList.add(category_items_pojo17);

        return itemDetailList;
    }


}


