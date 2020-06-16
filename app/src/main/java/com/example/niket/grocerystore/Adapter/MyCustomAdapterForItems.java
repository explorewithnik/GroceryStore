package com.example.niket.grocerystore.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.niket.grocerystore.Activities.HomePage;
import com.example.niket.grocerystore.CartActivity.AddorRemoveCallbacks;
import com.example.niket.grocerystore.ItemsPOJO.Category_Items_POJO;
import com.example.niket.grocerystore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCustomAdapterForItems extends RecyclerView.Adapter<MyCustomAdapterForItems.ContactViewHolder> {

    private ArrayList<Category_Items_POJO> categoriesItemsPOJOArrayList;
    private Context mContext;
    private String userMobile;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceForSearch;
    private DatabaseReference databaseReference2;
    private String CategoryName;
    private Category_Items_POJO category_items_pojo1;
    SharedPreferences sharedPreferences;

    MyCustomAdapterForItems(Context context, ArrayList<Category_Items_POJO> categoriesItemsPOJOArrayList, String CategoryName) {
        this.mContext = context;
        this.categoriesItemsPOJOArrayList = categoriesItemsPOJOArrayList;
        this.CategoryName = CategoryName;
    }


    @NonNull
    @Override
    public MyCustomAdapterForItems.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items_design, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyCustomAdapterForItems.ContactViewHolder holder, int position) {
        sharedPreferences = mContext.getSharedPreferences("save", 0);
        userMobile = sharedPreferences.getString("userMobile", "");
        Log.e("userMobile", "userMobile " + userMobile);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


        databaseReference = firebaseDatabase.getReference("data").child("Cart").child("products");
        databaseReference2 = firebaseDatabase.getReference("data").child("products");
        databaseReferenceForSearch = firebaseDatabase.getReference("data").child("search").child("products");

        final Category_Items_POJO category_items_pojo = categoriesItemsPOJOArrayList.get(position);

        holder.itemName.setText(category_items_pojo.getItemName());
        String itemPrice = "₹" + category_items_pojo.getItemPrice();
        holder.itemPrice.setText(itemPrice);

        String itemMRPStrikePrice = "₹" + category_items_pojo.getItemMRPStrikePrice();
        holder.itemMRPStrikePrice.setText(itemMRPStrikePrice);

        holder.itemWeight.setText(category_items_pojo.getItemWeight());

        if (category_items_pojo.getButtonItemCount() != null && category_items_pojo.getButtonItemCount() == 0) {
            holder.addMinusButton.setVisibility(View.INVISIBLE);
            holder.addButton.setText(R.string.add_btn_text);
            holder.addPlusButton.setText("+");
        } else {
            holder.addButton.setTextColor(mContext.getResources().getColor(R.color.black_light));
            holder.addButton.setBackgroundColor(Color.WHITE);
            String addButtonText = "0";
            if (category_items_pojo.getButtonItemCount() != null)
                addButtonText = category_items_pojo.getButtonItemCount().toString();
            holder.addButton.setText(addButtonText);
            holder.addMinusButton.setVisibility(View.VISIBLE);
        }

        Glide.with(mContext).load(category_items_pojo.getItemImage()).into(holder.itemImage);

        // strike-through text
        holder.itemMRPStrikePrice.setPaintFlags(holder.itemMRPStrikePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.addPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                category_items_pojo1 = categoriesItemsPOJOArrayList.get(holder.getAdapterPosition());

                categoriesItemsPOJOArrayList.get(holder.getAdapterPosition()).setAddedTocart(true);
                final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("items").child(CategoryName).child(category_items_pojo1.getItemName()).child("buttonItemCount");

                rootRef.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {

                        Object v = mutableData.getValue();
                        if (v != null && v.equals(0)) {
                        } else {
                            Integer count = mutableData.getValue(Integer.class);
                            if (count != null) {
                                mutableData.setValue(count + 1);
                                Category_Items_POJO category_items_pojo2 = new Category_Items_POJO();

                                category_items_pojo2.setButtonItemCount(count + 1);
                                category_items_pojo2.setCategoryName(CategoryName);
                                category_items_pojo2.setItemWeight(category_items_pojo1.getItemWeight());
                                category_items_pojo2.setItemMRPStrikePrice(category_items_pojo1.getItemMRPStrikePrice());
                                category_items_pojo2.setItemPrice(category_items_pojo1.getItemPrice());
                                category_items_pojo2.setItemName(category_items_pojo1.getItemName());
                                category_items_pojo2.setItemImage(category_items_pojo1.getItemImage());
                                category_items_pojo2.setTotalItemAmount((count + 1) * (category_items_pojo1.getItemPrice()));
                                category_items_pojo2.setItemCatName(category_items_pojo1.getItemCatName());


                                databaseReference.child(category_items_pojo1.getItemName()).setValue(category_items_pojo2);
                                databaseReference2.child(CategoryName).child(category_items_pojo1.getItemCatName()).child(category_items_pojo1.getItemName()).child("buttonItemCount").setValue(count + 1);
                                databaseReferenceForSearch.child(category_items_pojo1.getItemName()).child("buttonItemCount").setValue(count + 1);
                            }
                        }
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                        if (databaseError != null) {
                            System.out.println("Firebase counter increment failed.");
                        } else {
                            System.out.println("Firebase counter increment succeeded.");

                        }
                    }
                });
                if (mContext instanceof HomePage) {
                    ((AddorRemoveCallbacks) mContext).onAddProduct();
                }
            }
        });
        holder.addMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_items_pojo1 = categoriesItemsPOJOArrayList.get(holder.getAdapterPosition());
                categoriesItemsPOJOArrayList.get(holder.getAdapterPosition()).setAddedTocart(false);

                final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("data").child("items").child(CategoryName).child(category_items_pojo1.getItemName()).child("buttonItemCount");

                rootRef.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        Integer count = mutableData.getValue(Integer.class);
                        if (count != null) {
                            if (count <= 0) {
                                mutableData.setValue(0);
                            } else {
                                mutableData.setValue(count - 1);
                                Category_Items_POJO category_items_pojo2 = new Category_Items_POJO();

                                category_items_pojo2.setButtonItemCount(count - 1);
                                category_items_pojo2.setCategoryName(CategoryName);
                                category_items_pojo2.setItemWeight(category_items_pojo1.getItemWeight());
                                category_items_pojo2.setItemMRPStrikePrice(category_items_pojo1.getItemMRPStrikePrice());
                                category_items_pojo2.setItemPrice(category_items_pojo1.getItemPrice());
                                category_items_pojo2.setItemName(category_items_pojo1.getItemName());
                                category_items_pojo2.setItemImage(category_items_pojo1.getItemImage());
                                category_items_pojo2.setTotalItemAmount((count - 1) * (category_items_pojo1.getItemPrice()));
                                category_items_pojo2.setItemCatName(category_items_pojo1.getItemCatName());
                                databaseReference.child(category_items_pojo1.getItemName()).setValue(category_items_pojo2);
                                databaseReference2.child(CategoryName).child(category_items_pojo1.getItemCatName()).child(category_items_pojo1.getItemName()).child("buttonItemCount").setValue(count - 1);
                                databaseReferenceForSearch.child(category_items_pojo1.getItemName()).child("buttonItemCount").setValue(count - 1);
                            }
                        }
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                        if (databaseError != null) {
                            System.out.println("Firebase counter increment failed.");
                        } else {
                            System.out.println("Firebase counter increment succeeded.");
                        }
                    }
                });
                if (mContext instanceof HomePage) {
                    ((AddorRemoveCallbacks) mContext).onRemoveProduct();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesItemsPOJOArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView itemPrice, itemName, itemWeight, itemMRPStrikePrice;
        ImageView itemImage;
        Button addButton, addPlusButton, addMinusButton;

        ContactViewHolder(final View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemWeight = itemView.findViewById(R.id.itemWeight);
            itemMRPStrikePrice = itemView.findViewById(R.id.itemMRPStrikePrice);
            addButton = itemView.findViewById(R.id.addItem);
            addPlusButton = itemView.findViewById(R.id.addItemPlus);
            addMinusButton = itemView.findViewById(R.id.addItemMinus);
            itemImage = itemView.findViewById(R.id.itemImage);
            addButton.setBackgroundColor(Color.TRANSPARENT);
        }
    }

}
